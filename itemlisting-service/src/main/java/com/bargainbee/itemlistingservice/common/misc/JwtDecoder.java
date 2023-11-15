package com.bargainbee.itemlistingservice.common.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final ObjectMapper objectMapper;

    public String decodeJwtAndGetUsername(String jwt) throws JsonProcessingException {
        jwt = removeBearerFromJwt(jwt);

        String[] chunks = jwt.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        JsonNode payloadJsonNode = objectMapper.readTree(payload);

        return payloadJsonNode.get("preferred_username").asText();
    }

    public String removeBearerFromJwt(String jwt) {
        return jwt.substring(7);
    }

}
