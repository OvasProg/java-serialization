package com.example.demo;

import com.example.demo.deserialization.JsonUserDeserializer;
import com.example.demo.deserialization.ProtobufUserDeserializer;
import com.example.demo.deserialization.XmlUserDeserializer;
import com.example.demo.model.User;
import com.example.demo.proto.UserMessages;

public final class DemoApplication {

    public static void main(String[] args) throws Exception {
        JsonUserDeserializer json = new JsonUserDeserializer();
        XmlUserDeserializer xml = new XmlUserDeserializer();
        ProtobufUserDeserializer proto = new ProtobufUserDeserializer();

        User fromJson = json.fromClasspathResource("/samples/user.json");
        User fromXml = xml.fromClasspathResource("/samples/user.xml");
        UserMessages.User fromProtoText = proto.fromTextFormatClasspathResource("/samples/user.textproto");
        UserMessages.User fromProtoBinary = proto.fromBinaryClasspathResource("/samples/user.pb");

        System.out.println("JSON -> " + fromJson);
        System.out.println("XML  -> " + fromXml);
        System.out.println("Protobuf (TextFormat) -> id=" + fromProtoText.getId()
                + ", name=" + fromProtoText.getName()
                + ", email=" + fromProtoText.getEmail());
        System.out.println("Protobuf (binary)     -> id=" + fromProtoBinary.getId()
                + ", name=" + fromProtoBinary.getName()
                + ", email=" + fromProtoBinary.getEmail());

        if (!fromJson.equals(new User(fromProtoBinary.getId(), fromProtoBinary.getName(), fromProtoBinary.getEmail()))) {
            throw new IllegalStateException("JSON and Protobuf binary payloads describe different users.");
        }
        if (!fromXml.equals(fromJson)) {
            throw new IllegalStateException("XML and JSON payloads describe different users.");
        }
    }
}
