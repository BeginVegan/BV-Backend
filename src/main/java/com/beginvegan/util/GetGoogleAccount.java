package com.beginvegan.util;

import com.beginvegan.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Generated
@Slf4j
public class GetGoogleAccount {
    private static final String GOOGLE_API = "https://www.googleapis.com/oauth2/v1/userinfo";

    public static MemberDTO getMemberInfo(String accessToken) throws IOException {
//        String credential = base64Decoder(googleCredential);
//        Map<String, String> googleAccountInfo = responseParser(credential);
//
//        String email = String.valueOf(googleAccountInfo.get("email"));
//        String name = String.valueOf(googleAccountInfo.get("name"));
//
//        MemberDTO memberInfo = new MemberDTO();
//        memberInfo.setMemberName(name);
//        memberInfo.setMemberEmail(email);
//
//        return memberInfo;

        ResponseEntity<String> response = googleAPI(accessToken);
        Map<String, Object> googleAccountInfo = responseParser(response);

        String email = String.valueOf(googleAccountInfo.get("email"));
        String name = String.valueOf(googleAccountInfo.get("name"));

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberName(name);
        memberInfo.setMemberEmail(email);

        return memberInfo;

    }

    public static  ResponseEntity<String> googleAPI(String accessToken) throws IOException {
        WebClient client = WebClient.builder().baseUrl(GOOGLE_API).build();

        ResponseEntity<String> response = client.get()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(String.class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new IOException("Google API 호출 실패");
        }
        log.info(response.toString());
        return response;
    }

    public static Map<String, Object> responseParser(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> googleAccountInfo = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        return googleAccountInfo;
    }



//    public static String base64Decoder(String googleCredential) {
//        String[] jwtParts = googleCredential.split("[.]");
//        String decodedPayload = new String(Base64.getUrlDecoder().decode(jwtParts[1]));
//        return decodedPayload;
//    }
//
//    public static Map<String, String> responseParser(String decodedPayload) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> googleAccountInfo = mapper.readValue(decodedPayload, Map.class);
//        return googleAccountInfo;
//    }

}
