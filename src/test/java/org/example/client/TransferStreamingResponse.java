package org.example.client;

import io.grpc.stub.StreamObserver;
import org.example.entity.TransferResponse;

import java.util.concurrent.CountDownLatch;

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {
  private CountDownLatch countDownLatch;

  public TransferStreamingResponse(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void onNext(TransferResponse transferResponse) {
    System.out.println("Status: )" + transferResponse.getStatus());
    transferResponse.getAccountsList()
        .stream()
        .map(account -> account.getAccountNumber() + " : " + account.getAmount())
        .forEach(System.out::println);
    System.out.println("-------------------------------------------------------------");
  }

  @Override
  public void onError(Throwable throwable) {
    this.countDownLatch.countDown();
  }

  @Override
  public void onCompleted() {
    System.out.println("All transfer done");
    this.countDownLatch.countDown();
  }
}
