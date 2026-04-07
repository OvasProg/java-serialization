package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

    @Test
    void equalsAndHashCode_workCorrectly() {
        Address addr1 = new Address("USA", "NY", "Main");
        Address addr2 = new Address("USA", "NY", "Main");
        Course c1 = new Course("Java", "J1");
        Course c2 = new Course("Java", "J1");

        Student s1 = new Student(1, "John", "Doe", "j@d.com", "G1", 1, true, addr1, List.of(c1));
        Student s2 = new Student(1, "John", "Doe", "j@d.com", "G1", 1, true, addr2, List.of(c2));
        Student s3 = new Student(2, "Jane", "Doe", "j@d.com", "G1", 1, true, addr1, List.of(c1));

        assertThat(s1).isEqualTo(s2).hasSameHashCodeAs(s2);
        assertThat(s1).isNotEqualTo(s3);
    }

    @Test
    void toString_includesFields() {
        Student s = new Student(1, "John", "Doe", "j@d.com", "G1", 1, true, new Address("A", "B", "C"), List.of());
        assertThat(s.toString()).contains("John", "Doe", "j@d.com", "G1", "A", "B", "C");
    }
}
