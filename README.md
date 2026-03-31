# Deserialization demo (Java)

Small Maven project that loads the same logical **User** record from **JSON**, **XML**, and **Protocol Buffers** (binary wire format and optional TextFormat).

## Requirements

- **JDK 17** or newer  
- **Apache Maven 3.6+** (first build downloads `protoc` and dependencies into your local Maven repository)

## Run the demo

From the project root:

```bash
mvn clean compile exec:java
```

`com.example.demo.DemoApplication` reads the sample files under `src/main/resources/samples/`, prints each deserialized result, and checks that JSON, XML, and Protobuf describe the same user.

## Testing

| Command | What runs |
|---------|-----------|
| `mvn test` | **Unit tests** (JUnit 5 via Surefire): deserializers, `User` model |
| `mvn verify` | Unit tests **and** **integration tests** (Failsafe): `DemoApplicationIT` exercises `DemoApplication.main` against the real classpath samples |

Integration tests use the `*IT` naming convention so they are picked up by the Failsafe plugin, not Surefire.

## Project layout

| Path | Purpose |
|------|---------|
| `src/main/proto/user.proto` | Protobuf schema; Java sources are generated at build time |
| `src/main/java/.../model/User.java` | POJO used for JSON and XML |
| `src/main/java/.../deserialization/` | `JsonUserDeserializer`, `XmlUserDeserializer`, `ProtobufUserDeserializer` |
| `src/main/java/.../DemoApplication.java` | Entry point |
| `src/main/resources/samples/` | `user.json`, `user.xml`, `user.textproto`, `user.pb` |
| `src/test/java/...` | JUnit 5 unit tests; `DemoApplicationIT` for end-to-end checks |

## Stack

- **JSON / XML:** [Jackson](https://github.com/FasterXML/jackson) (`jackson-databind`, `jackson-dataformat-xml`)
- **Protobuf:** [protobuf-java](https://github.com/protocolbuffers/protobuf) and `protobuf-java-util` (TextFormat), with `protobuf-maven-plugin` to compile `.proto` files

## Regenerating `user.pb`

If you change `user.proto` or the sample field values, rebuild the binary sample so it stays aligned with the text formats:

```bash
mvn compile
CP=$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout -DincludeScope=compile)
java -cp "target/classes:$CP" com.example.demo.tool.WriteSampleUserPb
```

This overwrites `src/main/resources/samples/user.pb`.

## Notes

- On some JDK versions you may see **warnings** about `sun.misc.Unsafe` originating from the Protobuf runtime; they are library-side and do not affect this demo’s behavior.
- Protobuf **TextFormat** (`user.textproto`) is human-readable and useful for debugging; production systems typically use **binary** (`user.pb`) or other transports.
