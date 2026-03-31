package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void equalsAndHashCode_useIdNameEmail() {
        User a = new User(1, "n", "e");
        User b = new User(1, "n", "e");
        User c = new User(2, "n", "e");

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a).isNotEqualTo(c);
    }

    @Test
    void toString_includesFields() {
        User u = new User(7, "x", "y@z");
        assertThat(u.toString()).contains("7", "x", "y@z");
    }
}
