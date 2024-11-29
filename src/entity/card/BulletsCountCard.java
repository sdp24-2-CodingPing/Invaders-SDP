package entity.card;

import entity.player.PlayerShip;

public class BulletsCountCard extends Card {
  public BulletsCountCard() {
    super("Bullet Count", "res/image/card/bullets_count.png");
  }

  @Override
  public void applyCardStatus(PlayerShip playerShip) {
    playerShip.getPlayerCardStatus().bulletsCountLevelUp();
    logCardSelectMessage();
  }
}
