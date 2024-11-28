package entity.card;

import entity.player.PlayerShip;

public class HpCard extends Card {
  public HpCard() {
    super("HP", "res/image/card/hp.jpg");
  }

  @Override
  public void applyCardStatus(PlayerShip playerShip) {
    int prevMaxHp = playerShip.getPlayerMaxHP();
    playerShip.getPlayerCardStatus().hpLevelUp();
    int newMaxHp = playerShip.getPlayerMaxHP();
    playerShip.receiveHeal(newMaxHp - prevMaxHp);
    logCardSelectMessage();
  }
}
