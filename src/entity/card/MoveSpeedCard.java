package entity.card;

import entity.player.PlayerShip;

public class MoveSpeedCard extends Card {
    public MoveSpeedCard() {
        super("Move Speed");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().moveSpeedLevelUp();
        logCardSelectMessage();
    }
}