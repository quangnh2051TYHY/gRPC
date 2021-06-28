package org.example.client;

import io.grpc.stub.StreamObserver;
import org.example.entity.Balance;

import java.util.concurrent.CountDownLatch;

public class BalanceStreamObserve implements StreamObserver<Balance> {

  private CountDownLatch countDownLatch;

  public BalanceStreamObserve(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void onNext(Balance balance) {
    System.out.println("final balance: " + balance.getAmount());
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println(throwable.toString());
  }

  @Override
  public void onCompleted() {
    System.out.println("Server is done");
  }
}
