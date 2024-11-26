package entity.card;

import entity.player.PlayerShip;

public class BulletsSpeedCard extends Card {
    public BulletsSpeedCard() {
        super("BulletsSpeed");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().bulletsSpeedLevelUp();
        logCardSelectMessage();
    }
}