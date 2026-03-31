package com.example.demo.deserialization;

import com.example.demo.model.User;
import com.example.demo.testsupport.SampleUserFixture;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonUserDeserializerTest {

    private final JsonUserDeserializer deserializer = new JsonUserDeserializer();

    @Test
    void fromClasspathResource_readsSampleJson() throws IOException {
        User user = deserializer.fromClasspathResource("/samples/user.json");
        assertThat(user).isEqualTo(SampleUserFixture.expectedPojo());
    }

    @Test
    void fromClasspathResource_missingResource_throws() {
        assertThatThrownBy(() -> deserializer.fromClasspathResource("/samples/does-not-exist.json"))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Resource not found");
    }
}
