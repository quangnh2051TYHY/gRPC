package org.example.grpc.protobuf;

import org.example.entity.Person;

public class DefaultValueDemo {
  public static void main(String[] args) {
    Person person = Person.newBuilder().build();
//    System.out.println(person.hasAddress());
    System.out.println(person.hashCode());
//    System.out.println(person.getGirlFriendList().get(0));
  }
}
