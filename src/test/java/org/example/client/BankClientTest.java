package org.example.client;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.entity.BalanceCheckRequest;
import org.example.entity.BankServiceGrpc;
import org.example.entity.WithDrawRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

  private BankServiceGrpc.BankServiceBlockingStub blockingStub;
  private BankServiceGrpc.BankServiceStub bankServiceStub;

  @BeforeAll
  public void setUp() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 1998)
        .usePlaintext()
        .build();
    this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    this.bankServiceStub = BankServiceGrpc.newStub(channel);
  }

  @Test
  public void balanceTest() {
    BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
        .setAccountNumber(6)
        .build();
    Assertions.assertEquals(50 , this.blockingStub.getBalance(balanceCheckRequest).getAmount());
  }

  //use blocking therefore i must be wait
  @Test
  public void withdrawTest() {
    WithDrawRequest withDrawRequest = WithDrawRequest.newBuilder()
        .setAccountNumber(7)
        .setAmount(40)
        .build();

    this.blockingStub.withDraw(withDrawRequest)
        .forEachRemaining(money -> System.out.println("Received money: " + money.getValue()));

    BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
        .setAccountNumber(7)
        .build();
    Assertions.assertEquals(30, this.blockingStub.getBalance(balanceCheckRequest).getAmount());
  }

  //don't need to wait response
  @Test
  public void withdrawAsyncTest() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    WithDrawRequest withDrawRequest = WithDrawRequest.newBuilder()
        .setAccountNumber(10)
        .setAmount(50)
        .build();

    this.bankServiceStub.withDraw(withDrawRequest, new MoneyStreamingResponse(latch));
    latch.await();
//    Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
  }
}
