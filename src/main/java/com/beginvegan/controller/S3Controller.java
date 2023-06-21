package com.beginvegan.controller;

import com.beginvegan.service.S3Service;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("s3")
@Generated
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @GetMapping("{dirName}")
    public ResponseEntity<?> imageList(@PathVariable String dirName) {
        List<String> imageList = s3Service.getS3(dirName);
        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }

    @GetMapping("/single")
    public ResponseEntity<?> imageOne(@RequestParam String dirName) {
        List<String> imageList = s3Service.getS3(dirName);
        return new ResponseEntity<>(imageList.get(0), HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> imageDownload(@RequestParam String dirName) throws IOException {
        String decodedDirName = java.net.URLDecoder.decode(dirName, "UTF-8");
        ByteArrayOutputStream byteArrayOutputStream = s3Service.downloadImage(decodedDirName);

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        HttpHeaders headers = buildHeaders(byteArray);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private HttpHeaders buildHeaders(byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set the content type as octet-stream
        headers.setContentLength(data.length); // Set the content length
        return headers;
    }
}