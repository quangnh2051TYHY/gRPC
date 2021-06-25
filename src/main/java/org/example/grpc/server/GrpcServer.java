package org.example.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Start server at port 1998");
    //start server
    Server server = ServerBuilder.forPort(1998)
        .addService(new BankService())
        .build();
    server.start();
    server.awaitTermination();
  }
}
