package com.pixvs.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class RocketChatController {

    public static final String CANAL_GITHUB = "tyWNPkkASSJM4wCb8";

    @Autowired
    private Environment environment;

    public ResponseEntity<String> sendMessage(String canal, String mensaje) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", environment.getProperty("environments.rocket-chat.token"));
        headers.set("X-User-Id", environment.getProperty("environments.rocket-chat.user"));

        String requestJson = "{\n" +
                "   \"message\": {\n" +
                "      \"rid\": \""+canal+"\",\n" +
                "      \"msg\": \""+mensaje+"\"\n" +
                "  }\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<String>(requestJson,headers);

        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(environment.getProperty("environments.rocket-chat.url") + "/api/v1/chat.sendMessage", request, String.class);

        return responseEntityStr;
    }
}