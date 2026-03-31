package com.example.demo.deserialization;

import com.example.demo.proto.UserMessages;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Deserializes {@link UserMessages.User} from binary protobuf bytes or from TextFormat (human-readable).
 */
public final class ProtobufUserDeserializer {

    public UserMessages.User fromBinaryBytes(byte[] data) throws InvalidProtocolBufferException {
        return UserMessages.User.parseFrom(data);
    }

    public UserMessages.User fromBinaryClasspathResource(String resourcePath) throws IOException {
        try (InputStream in = ProtobufUserDeserializer.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return UserMessages.User.parseFrom(in.readAllBytes());
        }
    }

    public UserMessages.User fromTextFormatClasspathResource(String resourcePath) throws IOException {
        try (InputStream in = ProtobufUserDeserializer.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            UserMessages.User.Builder builder = UserMessages.User.newBuilder();
            TextFormat.merge(text, builder);
            return builder.build();
        }
    }
}
