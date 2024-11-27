package entity.card;

import entity.player.PlayerShip;

public class ShotIntervalCard extends Card {
    public ShotIntervalCard() {
        super("Shot Interval", "res/image/card/shot_interval.jpg");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().intervalLevelUp();
        logCardSelectMessage();
    }
}