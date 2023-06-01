package com.beginvegan.aspect;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonControllerAdvice {

    /**
     * 예외 처리 Advice
     * controller의 Exception을 최종적으로 받아서 처리
     * 다른 메소드에 의해 특별히 처리되지 않는 다른 예외의 경우 except() 메소드가 호출됨
     * 프론트 서버에 응답으로 오류 메시지와 서버 오류 코드(HTTP 상태 코드 500)를 전달
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> except(Exception e) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        resHeaders.add("Access-Control-Allow-Origin", "*");
        resHeaders.add("Access-Control-Allow-Credentials", "true");
        return new ResponseEntity<>(e.getMessage(), resHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
