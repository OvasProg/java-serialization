package com.example.demo.deserialization;

import com.example.demo.proto.UserMessages;
import com.example.demo.testsupport.SampleUserFixture;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtobufUserDeserializerTest {

    private final ProtobufUserDeserializer deserializer = new ProtobufUserDeserializer();

    @Test
    void fromBinaryBytes_roundTripsWithToByteArray() throws InvalidProtocolBufferException {
        UserMessages.User original = SampleUserFixture.expectedProto();
        UserMessages.User parsed = deserializer.fromBinaryBytes(original.toByteArray());
        assertThat(parsed).isEqualTo(original);
    }

    @Test
    void fromBinaryClasspathResource_readsSamplePb() throws IOException {
        UserMessages.User user = deserializer.fromBinaryClasspathResource("/samples/user.pb");
        assertThat(user).isEqualTo(SampleUserFixture.expectedProto());
    }

    @Test
    void fromTextFormatClasspathResource_readsSampleTextproto() throws IOException {
        UserMessages.User user = deserializer.fromTextFormatClasspathResource("/samples/user.textproto");
        assertThat(user).isEqualTo(SampleUserFixture.expectedProto());
    }

    @Test
    void fromBinaryClasspathResource_missingResource_throws() {
        assertThatThrownBy(() -> deserializer.fromBinaryClasspathResource("/samples/missing.pb"))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Resource not found");
    }

    @Test
    void fromTextFormatClasspathResource_missingResource_throws() {
        assertThatThrownBy(() -> deserializer.fromTextFormatClasspathResource("/samples/missing.textproto"))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Resource not found");
    }

    @Test
    void fromBinaryBytes_invalidPayload_throws() {
        assertThatThrownBy(() -> deserializer.fromBinaryBytes(new byte[] {0x00, 0x01}))
                .isInstanceOf(InvalidProtocolBufferException.class);
    }
}
