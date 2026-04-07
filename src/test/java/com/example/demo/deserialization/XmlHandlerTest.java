package com.example.demo.deserialization;

import com.example.demo.model.Address;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class XmlHandlerTest {

    private final XmlHandler handler = new XmlHandler();

    @Test
    void user_deserialization() throws IOException {
        User deserialized = handler.fromClasspathResource("/samples/user.xml", User.class);
        assertThat(deserialized.getId()).isEqualTo(42);
        assertThat(deserialized.getName()).isEqualTo("Jane Developer");
    }

    @Test
    void student_deserialization() throws IOException {
        Student student = handler.fromClasspathResource("/samples/student.xml", Student.class);
        
        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getFirstName()).isEqualTo("John");
        assertThat(student.getAddress().getCity()).isEqualTo("New York");
        assertThat(student.getCourses()).hasSize(2);
        assertThat(student.getCourses().get(0).getName()).isEqualTo("Java Programming");
    }

    @Test
    void student_roundTrip(@TempDir Path tempDir) throws IOException {
        Student original = new Student(10, "Test", "Student", "test@test.com", "G1", 1, true,
                new Address("UK", "London", "Baker St"),
                List.of(new Course("C1", "CODE1")));
        
        File file = tempDir.resolve("student_rt.xml").toFile();
        handler.writeToFile(original, file);
        
        Student deserialized = handler.fromFile(file, Student.class);
        assertThat(deserialized).isEqualTo(original);
    }
}
