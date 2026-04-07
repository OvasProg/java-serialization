package com.example.demo;

import com.example.demo.deserialization.JsonHandler;
import com.example.demo.deserialization.ProtobufHandler;
import com.example.demo.deserialization.XmlHandler;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.proto.StudentMessages;
import com.example.demo.proto.UserMessages;

import java.io.File;

public final class DemoApplication {

    public static void main(String[] args) throws Exception {
        JsonHandler json = new JsonHandler();
        XmlHandler xml = new XmlHandler();
        ProtobufHandler proto = new ProtobufHandler();

        // Task 1 & 2: User Round-Trip
        User user = json.fromClasspathResource("/samples/user.json", User.class);
        System.out.println("Loaded User from JSON: " + user);

        File outputDir = new File("target/output");
        outputDir.mkdirs();

        json.writeToFile(user, new File(outputDir, "user.json"));
        xml.writeToFile(user, new File(outputDir, "user.xml"));
        // Protobuf binary for User (from POJO manually for demo, or via proto parsing)
        UserMessages.User userProto = UserMessages.User.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();
        proto.writeToFile(userProto, new File(outputDir, "user.pb"));

        // Task 1 & 3: Student Round-Trip
        Student student = json.fromClasspathResource("/samples/student.json", Student.class);
        System.out.println("Loaded Student from JSON: " + student);

        json.writeToFile(student, new File(outputDir, "student.json"));
        xml.writeToFile(student, new File(outputDir, "student.xml"));

        // Build Student Protobuf from POJO for round-trip demonstration
        StudentMessages.Address protoAddr = StudentMessages.Address.newBuilder()
                .setCountry(student.getAddress().getCountry())
                .setCity(student.getAddress().getCity())
                .setStreet(student.getAddress().getStreet())
                .build();
        
        StudentMessages.Student.Builder studentProtoBuilder = StudentMessages.Student.newBuilder()
                .setId(student.getId())
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setEmail(student.getEmail())
                .setGroup(student.getGroup())
                .setYearOfStudy(student.getYearOfStudy())
                .setActive(student.isActive())
                .setAddress(protoAddr);

        student.getCourses().forEach(c -> studentProtoBuilder.addCourses(
                StudentMessages.Course.newBuilder().setName(c.getName()).setCode(c.getCode()).build()
        ));
        
        proto.writeToFile(studentProtoBuilder.build(), new File(outputDir, "student.pb"));

        System.out.println("Round-trip complete. Output files are in " + outputDir.getAbsolutePath());
    }
}
