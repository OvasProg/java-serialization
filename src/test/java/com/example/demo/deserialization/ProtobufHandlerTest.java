package com.example.demo.deserialization;

import com.example.demo.proto.StudentMessages;
import com.example.demo.proto.UserMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ProtobufHandlerTest {

    private final ProtobufHandler handler = new ProtobufHandler();

    @Test
    void user_deserialization() throws IOException {
        UserMessages.User user = handler.fromBinaryClasspathResource("/samples/user.pb", UserMessages.User::parseFrom);
        assertThat(user.getName()).isEqualTo("Jane Developer");
    }

    @Test
    void student_deserialization() throws IOException {
        StudentMessages.Student student = handler.fromBinaryClasspathResource("/samples/student.pb", StudentMessages.Student::parseFrom);
        assertThat(student.getFirstName()).isEqualTo("John");
        assertThat(student.getAddress().getCity()).isEqualTo("New York");
        assertThat(student.getCoursesCount()).isEqualTo(2);
    }

    @Test
    void student_textFormat_deserialization() throws IOException {
        StudentMessages.Student student = handler.fromTextFormatClasspathResource("/samples/student.textproto", StudentMessages.Student.newBuilder());
        assertThat(student.getFirstName()).isEqualTo("John");
        assertThat(student.getCourses(0).getName()).isEqualTo("Java Programming");
    }

    @Test
    void student_roundTrip(@TempDir Path tempDir) throws IOException {
        StudentMessages.Student original = StudentMessages.Student.newBuilder()
                .setId(100)
                .setFirstName("Proto")
                .setLastName("Test")
                .build();
        
        File file = tempDir.resolve("student_rt.pb").toFile();
        handler.writeToFile(original, file);
        
        StudentMessages.Student deserialized = handler.fromFile(file, StudentMessages.Student::parseFrom);
        assertThat(deserialized).isEqualTo(original);
    }
}
