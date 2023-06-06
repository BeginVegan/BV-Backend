package com.beginvegan.util;

import com.beginvegan.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;
@Generated
public class GetKakaoAccount {
    private static final String KAKAO_API = "https://kapi.kakao.com/v2/user/me";

    public static MemberDTO getMemberInfo(String accessToken) throws IOException {
        ResponseEntity<String> response = kakaoAPI(accessToken);
        Map<String, Object> kakaoAccountInfo = responseParser(response);

        String id = String.valueOf(kakaoAccountInfo.get("id"));
        String email = id.concat("@kakao.com");
        String name = (String)((Map<String, Object>) kakaoAccountInfo.get("properties")).get("nickname");

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberName(name);
        memberInfo.setMemberEmail(email);

        return memberInfo;
    }

    public static  ResponseEntity<String> kakaoAPI(String accessToken) throws IOException {
        WebClient client = WebClient.builder().baseUrl(KAKAO_API).build();

        ResponseEntity<String> response = client.get()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(String.class)
                .block();

        if (response == null || response.getBody() == null) {
            throw new IOException("Kakao API 호출 실패");
        }

        return response;
    }

    public static Map<String, Object> responseParser(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> kakaoAccountInfo = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        return kakaoAccountInfo;
    }
}