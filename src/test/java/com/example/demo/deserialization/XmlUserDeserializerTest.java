package com.example.demo.deserialization;

import com.example.demo.model.User;
import com.example.demo.testsupport.SampleUserFixture;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class XmlUserDeserializerTest {

    private final XmlUserDeserializer deserializer = new XmlUserDeserializer();

    @Test
    void fromClasspathResource_readsSampleXml() throws IOException {
        User user = deserializer.fromClasspathResource("/samples/user.xml");
        assertThat(user).isEqualTo(SampleUserFixture.expectedPojo());
    }

    @Test
    void fromClasspathResource_missingResource_throws() {
        assertThatThrownBy(() -> deserializer.fromClasspathResource("/samples/does-not-exist.xml"))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Resource not found");
    }
}
