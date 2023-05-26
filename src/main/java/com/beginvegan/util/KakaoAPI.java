package com.beginvegan.util;

import com.beginvegan.dto.MemberDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class KakaoAPI {
    public static final String USER_EMAIL_URL = "https://kapi.kakao.com/v2/user/me";

    public static MemberDTO getMemberInfo(String accessToken) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        //요청 헤더를 세팅한다.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);

        //위에서 세팅한 파라미터와 헤더를 바탕으로 요청 entity를 만들고, GET 요청을 보낸다.
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(USER_EMAIL_URL, HttpMethod.GET, entity,String.class);

        //응답의 JSON 문자열을 Parsing한다. List의 첫번째 요소가 email값으로 온다.
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        Map<String, Object> accountInfo = (Map<String, Object>) map.get("kakao_account");

        //Map에서 name, email을 가져온다.
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail((String) accountInfo.get("email"));
        memberInfo.setMemberName((String) accountInfo.get("nickname"));

        return memberInfo;
    }

}
