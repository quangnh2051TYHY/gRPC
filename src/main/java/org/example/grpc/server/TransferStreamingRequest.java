package org.example.grpc.server;

import io.grpc.stub.StreamObserver;
import org.example.entity.Account;
import org.example.entity.TransferRequest;
import org.example.entity.TransferResponse;
import org.example.entity.TransferStatus;
import org.example.grpc.server.db.AccountFakeDb;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

  private StreamObserver<TransferResponse> transferResponseStreamObserver;

  public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
    this.transferResponseStreamObserver = transferResponseStreamObserver;
  }

  @Override
  public void onNext(TransferRequest transferRequest) {
    int fromAccount = transferRequest.getFromAccount();
    int toAccount = transferRequest.getToAccount();
    int amount = transferRequest.getAmount();
    int balance = AccountFakeDb.getBalance(fromAccount);
    TransferStatus status = TransferStatus.FAILED;

    if(balance >= amount && fromAccount != toAccount) {
      AccountFakeDb.withdraw(fromAccount, amount);
      AccountFakeDb.deposit(toAccount, amount);
      status = TransferStatus.SUCCESS;
    }

    Account fromAccountInfo = Account.newBuilder()
        .setAccountNumber(fromAccount)
        .setAmount(AccountFakeDb.getBalance(fromAccount))
        .build();
    Account toAccountInfo = Account.newBuilder()
        .setAccountNumber(toAccount)
        .setAmount(AccountFakeDb.getBalance(toAccount))
        .build();

    TransferResponse transferResponse = TransferResponse.newBuilder()
        .setStatus(status)
        .addAccounts(fromAccountInfo)
        .addAccounts(toAccountInfo)
        .build();

    this.transferResponseStreamObserver.onNext(transferResponse);
  }

  @Override
  public void onError(Throwable throwable) {

  }

  @Override
  public void onCompleted() {
    AccountFakeDb.printAccountDetails();
    this.transferResponseStreamObserver.onCompleted();
  }
}
