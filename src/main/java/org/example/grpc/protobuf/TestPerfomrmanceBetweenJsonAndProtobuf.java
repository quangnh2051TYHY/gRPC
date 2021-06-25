package org.example.grpc.protobuf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;
import org.example.entity.Person;
import org.example.grpc.json.PersonJson;

import java.io.IOException;

public class TestPerfomrmanceBetweenJsonAndProtobuf {
  public static void main(String[] args) throws IOException {
    //json
    PersonJson jPerson = new PersonJson();
    jPerson.setAge(1);
    jPerson.setName("J");

    ObjectMapper mapper = new ObjectMapper();

    Runnable runnableJson = () -> {
      try {
        byte[] bytes = mapper.writeValueAsBytes(jPerson);
        PersonJson newPersonJson = mapper.readValue(bytes, PersonJson.class);
      } catch (Exception e) {
        e.printStackTrace();
      }


    };
    //protobuf
    Person person = Person.newBuilder()
        .setName("p")
        .setAge(Int32Value.newBuilder().setValue(32).build())
        .build();

    Runnable runnableProtobuf = () -> {
      try {
        byte[] bytes = person.toByteArray();
        Person per = Person.parseFrom(bytes);
      } catch (Exception e) {
        e.printStackTrace();
      }
    };


    runPerformanceTest(runnableJson, "Runnable Json ");
    runPerformanceTest(runnableProtobuf, "Runnable Protobuf ");
  }

  private static void runPerformanceTest(Runnable runnable, String method) {
    long now = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      runnable.run();
    }
    long updated_now = System.currentTimeMillis();
    System.out.println(method + "Duration:" + (updated_now - now));
  }
}
