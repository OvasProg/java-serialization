package com.example.demo.deserialization;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Handles serialization and deserialization for Protobuf messages.
 */
public final class ProtobufHandler {

    public <T extends Message> T fromBinaryBytes(byte[] data, Parser<T> parser) throws InvalidProtocolBufferException {
        return parser.parseFrom(data);
    }

    public <T extends Message> T fromBinaryClasspathResource(String resourcePath, Parser<T> parser) throws IOException {
        try (InputStream in = ProtobufHandler.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return parser.parseFrom(in.readAllBytes());
        }
    }

    public <T extends Message> T fromFile(File file, Parser<T> parser) throws IOException {
        try (InputStream in = new java.io.FileInputStream(file)) {
            return parser.parseFrom(in.readAllBytes());
        }
    }

    public <T extends Message> T fromTextFormatClasspathResource(String resourcePath, T.Builder builder) throws IOException {
        try (InputStream in = ProtobufHandler.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            TextFormat.merge(text, builder);
            @SuppressWarnings("unchecked")
            T result = (T) builder.build();
            return result;
        }
    }

    public void writeToFile(Message msg, File file) throws IOException {
        file.getParentFile().mkdirs();
        try (FileOutputStream out = new FileOutputStream(file)) {
            msg.writeTo(out);
        }
    }

    @FunctionalInterface
    public interface Parser<T extends Message> {
        T parseFrom(byte[] data) throws InvalidProtocolBufferException;
    }
}
