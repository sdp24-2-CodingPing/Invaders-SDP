package entity.card;

import entity.player.PlayerShip;

public class BulletsSpeedCard extends Card {
    public BulletsSpeedCard() {
        super("Bullet Speed", "res/image/card/bullets_speed.jpg");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().bulletsSpeedLevelUp();
        logCardSelectMessage();
    }
}