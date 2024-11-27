package entity.card;

import entity.player.PlayerShip;

public class AttackDamageCard extends Card {
    public AttackDamageCard() {
        super("Attack Damage");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().attackDamageLevelUp();
        logCardSelectMessage();
    }
}
