package com.example.demo.deserialization;

import com.example.demo.model.User;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;

public final class XmlUserDeserializer {

    private final XmlMapper mapper = new XmlMapper();

    public User fromClasspathResource(String resourcePath) throws IOException {
        try (InputStream in = XmlUserDeserializer.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(in, User.class);
        }
    }
}
