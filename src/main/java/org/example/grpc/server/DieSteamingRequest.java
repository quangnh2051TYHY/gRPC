package org.example.grpc.server;

import io.grpc.stub.StreamObserver;
import org.example.entity.Die;
import org.example.entity.GameState;
import org.example.entity.Player;
import org.example.entity.State;

import java.util.concurrent.ThreadLocalRandom;

public class DieSteamingRequest implements StreamObserver<Die> {

  private Player client;
  private Player server;
  private StreamObserver<GameState> gameStateStreamObserver;

  public DieSteamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
    this.client = client;
    this.server = server;
    this.gameStateStreamObserver = gameStateStreamObserver;
  }

  @Override
  public void onNext(Die die) {
    this.client = this.getPlayerWithNewPos(this.client, die.getValue());
    if (this.client.getPosition() != 100) {
      this.server = this.getPlayerWithNewPos(this.server,
          ThreadLocalRandom.current().nextInt(1, 7));
    }
//    if(this.client.getState().equals(State.WIN) || this.server.getState().equals(State.WIN)) {
//
//    }
    this.gameStateStreamObserver.onNext(this.getGameState());
  }

  @Override
  public void onError(Throwable throwable) {

  }

  @Override
  public void onCompleted() {

    this.getGameState().getPlayerList().stream().forEach(player -> {
      if (player.getState().equals(State.WIN)) {
        System.out.println(player.getName() + "Win");
      }
    });
    this.gameStateStreamObserver.onCompleted();
  }

  private GameState getGameState() {
    return GameState.newBuilder()
        .addPlayer(this.client)
        .addPlayer(this.server)
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
