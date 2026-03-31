package com.example.demo.deserialization;

import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUserDeserializer {

    private final ObjectMapper mapper = new ObjectMapper();

    public User fromClasspathResource(String resourcePath) throws IOException {
        try (InputStream in = JsonUserDeserializer.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(in, User.class);
        }
    }
}
