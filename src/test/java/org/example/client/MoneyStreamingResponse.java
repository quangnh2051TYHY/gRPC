package org.example.client;

import io.grpc.stub.StreamObserver;
import org.example.entity.Money;

import java.util.concurrent.CountDownLatch;

public class MoneyStreamingResponse implements StreamObserver<Money> {

  private CountDownLatch countDownLatch;

  public MoneyStreamingResponse(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void onNext(Money money) {
    System.out.println("Received async: " + money.getValue());
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println(throwable.toString());
  }

  @Override
  public void onCompleted() {
    System.out.println("completed!");
  }
}
