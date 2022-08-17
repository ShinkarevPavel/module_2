package com.epam.esm.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonResponseSender {
    private static final String RESPONSE_CHAR_ENCODING = "UTF-8";
    private static final String RESPONSE_CONTENT_TYPE = "application/json";

    public void send(HttpServletResponse httpServletResponse, Object responseObject) throws IOException {
        ResponseEntity response = (ResponseEntity) responseObject; //TODO
        Map<String, Object> responseMap = (Map<String, Object>) response.getBody();
        httpServletResponse.setCharacterEncoding(RESPONSE_CHAR_ENCODING);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(RESPONSE_CONTENT_TYPE);
        String json = new ObjectMapper().writeValueAsString(responseMap);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.flushBuffer();
    }
}
