package com.example.demo.deserialization;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class XmlHandler {

    private final XmlMapper mapper = new XmlMapper();

    public XmlHandler() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public <T> T fromClasspathResource(String resourcePath, Class<T> clazz) throws IOException {
        try (InputStream in = XmlHandler.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(in, clazz);
        }
    }

    public <T> T fromFile(File file, Class<T> clazz) throws IOException {
        return mapper.readValue(file, clazz);
    }

    public void writeToFile(Object obj, File file) throws IOException {
        file.getParentFile().mkdirs();
        mapper.writeValue(file, obj);
    }
}
