package com.example.demo.tool;

import com.example.demo.proto.UserMessages;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes {@code src/main/resources/samples/user.pb} from the same values as the JSON/XML/TextFormat samples.
 * Run: {@code mvn -q compile exec:java -Dexec.mainClass=com.example.demo.tool.WriteSampleUserPb}
 */
public final class WriteSampleUserPb {

    public static void main(String[] args) throws Exception {
        UserMessages.User user = UserMessages.User.newBuilder()
                .setId(42)
                .setName("Jane Developer")
                .setEmail("jane@example.com")
                .build();
        Path out = Path.of("src/main/resources/samples/user.pb");
        Files.write(out, user.toByteArray());
        System.out.println("Wrote " + out.toAbsolutePath() + " (" + user.toByteArray().length + " bytes)");
    }
}
