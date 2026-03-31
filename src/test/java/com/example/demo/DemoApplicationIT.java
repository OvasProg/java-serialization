package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * End-to-end check: same classpath samples as production entry point, cross-format consistency rules.
 */
class DemoApplicationIT {

    @Test
    void main_loadsJsonXmlAndProtobufSamplesWithoutInconsistency() {
        assertThatCode(() -> DemoApplication.main(new String[0])).doesNotThrowAnyException();
    }
}
