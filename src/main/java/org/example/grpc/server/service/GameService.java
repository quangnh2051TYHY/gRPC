package org.example.grpc.server.service;

import io.grpc.stub.StreamObserver;
import org.example.entity.*;
import org.example.grpc.server.DieSteamingRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

  @Override
  public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
    Player client = Player.newBuilder()
        .setName("Player 1")
        .setPosition(0)
        .setState(State.PLAYING)
        .build();

    Player server = Player.newBuilder()
        .setName("Boss")
        .setPosition(0)
        .setState(State.PLAYING)
        .build();
    return new DieSteamingRequest(client, server, responseObserver);
  }

  @Override
  public void play(PlayerAmountReq request, StreamObserver<GameState> responseObserver) {
    List<Player> listPlayer = new ArrayList<>();
    int numberPlayer = request.getNumberPlayer();
    for(int i = 0; i < numberPlayer; i++) {
      Player player = Player.newBuilder()
          .setName("Player " + i)
          .setPosition(0)
          .setState(State.PLAYING)
          .build();
      listPlayer.add(player);
      player = this.getPlayerWithNewPos(player, ThreadLocalRandom.current().nextInt(1, 7));
      listPlayer.set(i, player);
      responseObserver.onNext(getGameState(listPlayer));
    }

    responseObserver.onCompleted();
  }

  private GameState getGameState(List<Player> listPlayer) {
    return GameState.newBuilder()
        .addAllPlayer(listPlayer)
        .build();
  }

  private Player getPlayerWithNewPos(Player player, int dieValue) {
    int newPosition = player.getPosition() + dieValue;
    if (newPosition > 100) {
      newPosition = 100 - (newPosition - 100);
    }
    //update position for Player
    if(newPosition == 100) {
      player = player.toBuilder()
          .setState(State.WIN)
          .build();
    }
    player = player.toBuilder()
        .setPosition(newPosition)
        .build();

    return player;
  }
}
