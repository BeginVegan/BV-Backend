package com.beginvegan.util;

import com.beginvegan.dto.MemberDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

public class KakaoAPI {
    private static final String KAKAO_API = "https://kapi.kakao.com/v2/user/me";

    public static MemberDTO getMemberInfo(String accessToken) throws IOException {
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

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> kakaoResponse = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        String id = String.valueOf(kakaoResponse.get("id"));
        String name = (String)((Map<String, Object>) kakaoResponse.get("properties")).get("nickname");
        String email = id.concat("@kakao.com");

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberName(name);
        memberInfo.setMemberEmail(email);

        return memberInfo;
    }
}