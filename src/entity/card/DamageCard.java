package entity.card;

import entity.player.PlayerCardStatus;
import entity.player.PlayerShip;

public class DamageCard extends Card {
    public DamageCard() {
        super("Damage");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().damageLevelUp();
        logCardSelectMessage();
    }
}
