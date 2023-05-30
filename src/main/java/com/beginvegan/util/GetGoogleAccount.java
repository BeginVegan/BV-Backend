package com.beginvegan.util;

import com.beginvegan.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class GetGoogleAccount {
    public static MemberDTO getMemberInfo(String googleCredential) throws IOException {
        String credential = base64Decoder(googleCredential);
        Map<String, String> googleAccountInfo = responseParser(credential);

        String email = String.valueOf(googleAccountInfo.get("email"));
        String name = String.valueOf(googleAccountInfo.get("name"));

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberName(name);
        memberInfo.setMemberEmail(email);

        return memberInfo;
    }

    public static String base64Decoder(String googleCredential) {
        String[] jwtParts = googleCredential.split("[.]");
        String decodedPayload = new String(Base64.getUrlDecoder().decode(jwtParts[1]));
        return decodedPayload;
    }

    public static Map<String, String> responseParser(String decodedPayload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> googleAccountInfo = mapper.readValue(decodedPayload, Map.class);
        return googleAccountInfo;
    }

}
