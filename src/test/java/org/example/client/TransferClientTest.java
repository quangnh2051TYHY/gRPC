package org.example.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.entity.BankServiceGrpc;
import org.example.entity.TransferRequest;
import org.example.entity.TransferServiceGrpc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

  private TransferServiceGrpc.TransferServiceBlockingStub transferServiceBlockingStub;
  private TransferServiceGrpc.TransferServiceStub transferServiceStub;

  @BeforeAll
  public void setUp() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 1998)
        .usePlaintext()
        .build();
    this.transferServiceBlockingStub = TransferServiceGrpc.newBlockingStub(channel);
    this.transferServiceStub = TransferServiceGrpc.newStub(channel);
  }

  @Test
  public void transferTest() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    TransferStreamingResponse response = new TransferStreamingResponse(countDownLatch);
    StreamObserver<TransferRequest> requestStreamObserver = this.transferServiceStub.transfer(response);
    Random random = new Random();
    for(int i = 0; i < 100; i++) {
      TransferRequest request = TransferRequest.newBuilder()
          .setFromAccount(random.nextInt(11))
          .setToAccount(random.nextInt(11))
          .setAmount(random.nextInt(20))
          .build();

      requestStreamObserver.onNext(request);
    }
    requestStreamObserver.onCompleted();
    countDownLatch.await();
  }
}
