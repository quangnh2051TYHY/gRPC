package org.example.grpc.protobuf;


import com.google.protobuf.Int32Value;
import org.example.entity.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {
  public static void main(String[] args) throws IOException {
    Person per = Person.newBuilder()
        .setName("Lệ")
        .setAge(Int32Value.newBuilder().setValue(32).build())
        .build();

    //create file if not existed and write per to that file
    Path path = Paths.get("quang.txt");
//    Files.write(path, per.toByteArray());
    //read per again from file
    byte [] bytes = Files.readAllBytes(path);
    System.out.println(bytes.length);
    Person personInFile = Person.parseFrom(bytes);

    System.out.println(personInFile);
  }
}
