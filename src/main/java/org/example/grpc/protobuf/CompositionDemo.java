package org.example.grpc.protobuf;



import com.google.protobuf.Int32Value;
import org.example.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompositionDemo {
  public static void main(String[] args) {
    Address address = Address.newBuilder()
        .setPostbox(100)
        .setCity("Ha Noi")
        .setStreet("Tran Phu- Ha Dong")
        .build();

    Car car = Car.newBuilder()
        .setMake("America")
        .setModel("Chervolet")
        .setYear(1998)
        .build();


//    Person person = Person.newBuilder()
//        .setAge(23)
//        .setName("Quangnh1")
//        .setAddress(address)
//        .setCar(car)
//        .addGirlFriend(girlFriend1)
//        .addGirlFriend(girlFriend2)
//        .build();

    //add Collection example
    GirlFriend girlFriend1 = GirlFriend.newBuilder()
        .setName("Test Girl")
        .setAge(22)
        .build();

    GirlFriend girlFriend2 = GirlFriend.newBuilder()
        .setName("Test Girl 2")
        .setAge(22)
        .build();

    ArrayList<GirlFriend> girlFriends = new ArrayList<>();
    girlFriends.add(girlFriend1);
    girlFriends.add(girlFriend2);

    Person person = Person.newBuilder()
        .setAge(Int32Value.newBuilder().setValue(32).build())
        .setName("Quangnh1")
        .setAddress(address)
        .setCar(car)
        .addAllGirlFriend(girlFriends)
        .build();

    //add map example
    Map<Integer, GirlFriend> mapSleep = new HashMap<>();
    mapSleep.put(2, girlFriend1);
    mapSleep.put(3, girlFriend2);

    //add enum example
    BodyStyle bodyStyle = BodyStyle.RONALDO;

    Person personMap = Person.newBuilder()
        .setAge(Int32Value.newBuilder().setValue(32).build())
        .setName("Quangnh1")
        .setAddress(address)
        .setCar(car)
        .addAllGirlFriend(girlFriends)
        .putAllSleepCalendar(mapSleep)
        .setBodyStyle(BodyStyle.RONALDO)
        .build();

//    System.out.println(personMap.getSleepCalendarOrThrow(2));
    //print
    System.out.println(personMap.getBodyStyle());
  }
}

