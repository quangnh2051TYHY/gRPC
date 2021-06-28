package org.example.grpc.server;

import io.grpc.stub.StreamObserver;
import org.example.entity.Balance;
import org.example.entity.DepositRequest;
import org.example.grpc.server.db.AccountFakeDb;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

  private StreamObserver<Balance> balanceStreamObserver;
  private int accountBalance;

  public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
    this.balanceStreamObserver = balanceStreamObserver;
  }
  //request sent and CashStreamObserver will listen DepositRequest
  //and update DB
  @Override
  public void onNext(DepositRequest depositRequest) {
    int accountNumber = depositRequest.getAccountNumber();
    int amount = depositRequest.getAmount();
    this.accountBalance = AccountFakeDb.deposit(accountNumber, amount);
  }

  @Override
  public void onError(Throwable throwable) {

  }

  @Override
  public void onCompleted() {
    Balance balance = Balance.newBuilder()
        .setAmount(this.accountBalance)
        .build();
    this.balanceStreamObserver.onNext(balance);
    this.balanceStreamObserver.onCompleted();
    System.out.println("Times");
  }
}
