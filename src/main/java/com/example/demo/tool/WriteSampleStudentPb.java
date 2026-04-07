package com.example.demo.tool;

import com.example.demo.proto.StudentMessages;

import java.nio.file.Files;
import java.nio.file.Path;

public final class WriteSampleStudentPb {

    public static void main(String[] args) throws Exception {
        StudentMessages.Address address = StudentMessages.Address.newBuilder()
                .setCountry("USA")
                .setCity("New York")
                .setStreet("123 Main St")
                .build();

        StudentMessages.Course javaCourse = StudentMessages.Course.newBuilder()
                .setName("Java Programming")
                .setCode("JAVA101")
                .build();

        StudentMessages.Course dsCourse = StudentMessages.Course.newBuilder()
                .setName("Data Structures")
                .setCode("DS201")
                .build();

        StudentMessages.Student student = StudentMessages.Student.newBuilder()
                .setId(1)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setGroup("CS-101")
                .setYearOfStudy(2)
                .setActive(true)
                .setAddress(address)
                .addCourses(javaCourse)
                .addCourses(dsCourse)
                .build();

        Path out = Path.of("src/main/resources/samples/student.pb");
        Files.write(out, student.toByteArray());
        System.out.println("Wrote " + out.toAbsolutePath() + " (" + student.toByteArray().length + " bytes)");
    }
}
