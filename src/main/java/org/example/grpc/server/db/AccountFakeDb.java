package org.example.grpc.server.db;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountFakeDb {
  /*
  this is DB
  * */
  private static final Map<Integer, Integer> balanceMap = IntStream
      .rangeClosed(1, 10)
      .boxed()
      .collect(Collectors.toMap(
          Function.identity(),
          v -> v * 100));

  public static int getBalance(int accountId) {
    return balanceMap.get(accountId);
  }

  public static int deposit(int accountId, int amount) {
    return balanceMap.computeIfPresent(accountId, (k, v) -> v + amount);
  }

  public static int withdraw(int accountId, int amount) {
    return balanceMap.computeIfPresent(accountId, (k, v) -> v - amount);
  }

  public static void printAccountDetails() {
    System.out.println(balanceMap);
  }
}
