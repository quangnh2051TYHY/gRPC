package org.example.grpc.server.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.entity.*;
import org.example.grpc.server.db.AccountFakeDb;
import org.example.grpc.server.CashStreamingRequest;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

  @Override
  public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
    int accountNumber = request.getAccountNumber();
    Balance balance = Balance.newBuilder()
        .setAmount(AccountFakeDb.getBalance(accountNumber))
        .build();
    responseObserver.onNext(balance);
    responseObserver.onCompleted();
  }

  @Override
  public void withDraw(WithDrawRequest request, StreamObserver<Money> responseObserver)  {
    int accountNumber = request.getAccountNumber();
    int amount = request.getAmount();

    int balance = AccountFakeDb.getBalance(accountNumber);


    if (amount > balance) {
      Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only {} " + balance);
      responseObserver.onError(status.asRuntimeException());
      return;
    }

    for (int i = 0; i < (amount / 10); i++) {
      Money money = Money.newBuilder()
          .setValue(10).build();
      AccountFakeDb.withdraw(accountNumber, 10);

      responseObserver.onNext(money);
//      try {
//        Thread.sleep(1000);
//      } catch(InterruptedException ex) {
//        ex.printStackTrace();
//      }
    }
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
    return new CashStreamingRequest(responseObserver);
  }
}
