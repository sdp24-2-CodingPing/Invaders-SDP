package entity.card;

import engine.Core;
import entity.player.PlayerShip;

import java.util.logging.Logger;

public abstract class Card {
    private final String cardName;
    private static Logger logger = Core.getLogger();

    public Card(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void logCardSelectMessage() {
        logger.info("You Selected " + cardName + " Card!");
    }

    public abstract void applyCardStatus(PlayerShip playerShip);
}
