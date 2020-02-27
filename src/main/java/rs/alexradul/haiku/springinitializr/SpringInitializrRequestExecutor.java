/*
 * Copyright (c) 2020.
 */

package rs.alexradul.haiku.springinitializr;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringInitializrRequestExecutor {

    public static final String SPRING_STARTER_URL = "https://start.spring.io/starter.zip?";

    private final SpringInitializrRequest request;
    private final ObjectMapper objectMapper;

    public SpringInitializrRequestExecutor(SpringInitializrRequest request, ObjectMapper objectMapper) {
        this.request = request;
        this.objectMapper = objectMapper;
    }

    public void execute(File destinationDir) throws IOException {
        String springStaterURI = springStaterURI();
        try (InputStream stream = new URL(springStaterURI).openConnection().getInputStream()) {
            Zip.extractAll(stream, destinationDir);
        }
    }

    @SuppressWarnings("unchecked")
    private String springStaterURI() {
        Map<String, String> requestParams = objectMapper.convertValue(request, Map.class);
        return requestParams.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                        + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&", SPRING_STARTER_URL, ""));
    }
}
