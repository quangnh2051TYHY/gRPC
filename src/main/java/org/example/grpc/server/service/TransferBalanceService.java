package org.example.grpc.server.service;

import io.grpc.stub.StreamObserver;
import org.example.entity.TransferRequest;
import org.example.entity.TransferResponse;
import org.example.entity.TransferServiceGrpc;
import org.example.grpc.server.TransferStreamingRequest;

public class TransferBalanceService extends TransferServiceGrpc.TransferServiceImplBase {
  @Override
  public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
    return new TransferStreamingRequest(responseObserver);
  }
}
