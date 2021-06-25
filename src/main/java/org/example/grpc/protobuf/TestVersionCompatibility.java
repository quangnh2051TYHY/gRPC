package org.example.grpc.protobuf;

import org.example.entity.Television;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//after change entity proto of field from yearPublish -> Model. When read from file still
//return old result but change YearPublish to Model
public class TestVersionCompatibility {
  public static void main(String[] args) throws IOException {
   /*V1*/
//    Television televisionLG = Television.newBuilder()
//        .setBrand("LG")
//        .setYearPublish(1975)
//        .build();
//
    Path pathV1 = Paths.get("tv-v1");
////    Files.write(pathV1 , televisionLG.toByteArray());
//
//    //
    byte[] bytes = Files.readAllBytes(pathV1);
    System.out.println("TV V1: " +Television.parseFrom(bytes));
  }
}
