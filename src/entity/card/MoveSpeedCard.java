package entity.card;

import entity.player.PlayerShip;

public class MoveSpeedCard extends Card {
  public MoveSpeedCard() {
    super("Move Speed", "res/image/card/move_speed.png");
  }

  @Override
  public void applyCardStatus(PlayerShip playerShip) {
    playerShip.getPlayerCardStatus().moveSpeedLevelUp();
    logCardSelectMessage();
  }
}
