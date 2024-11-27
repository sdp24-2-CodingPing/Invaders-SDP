package entity.card;

import entity.player.PlayerShip;

public class AttackDamageCard extends Card {
    public AttackDamageCard() {
        super("Attack Damage", "res/image/card/attack_damage.png");
    }

    @Override
    public void applyCardStatus(PlayerShip playerShip) {
        playerShip.getPlayerCardStatus().attackDamageLevelUp();
        logCardSelectMessage();
    }
}
