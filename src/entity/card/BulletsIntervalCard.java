package entity.card;

import entity.player.PlayerShip;

public class BulletsIntervalCard extends Card {
    public BulletsIntervalCard() {
        super("Interval");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().intervalLevelUp();
        logCardSelectMessage();
    }
}