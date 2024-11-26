package entity.card;

import entity.player.PlayerShip;

public class BulletsCountCard extends Card {
    public BulletsCountCard() {
        super("BulletsCount");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().bulletsCountLevelUp();
        logCardSelectMessage();
    }
}