package com.example.demo.testsupport;

import com.example.demo.model.User;
import com.example.demo.proto.UserMessages;

public final class SampleUserFixture {

    public static final int ID = 42;
    public static final String NAME = "Jane Developer";
    public static final String EMAIL = "jane@example.com";

    private SampleUserFixture() {
    }

    public static User expectedPojo() {
        return new User(ID, NAME, EMAIL);
    }

    public static UserMessages.User expectedProto() {
        return UserMessages.User.newBuilder()
                .setId(ID)
                .setName(NAME)
                .setEmail(EMAIL)
                .build();
    }
}
