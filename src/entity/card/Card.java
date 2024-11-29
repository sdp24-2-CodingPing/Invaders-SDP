package entity.card;

import engine.Core;
import entity.player.PlayerShip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public abstract class Card {
  private final String cardName;
  private static final Logger logger = Core.getLogger();
  private BufferedImage cardImage;

  public Card(String cardName, String cardImagePath) {
    this.cardName = cardName;
    try {
      cardImage = ImageIO.read(new File(cardImagePath));
    } catch (IOException e) {
      logger.info("Card image loading failed");
    }
  }

  public BufferedImage getCardImage() {
    return cardImage;
  }

  public String getCardName() {
    return this.cardName;
  }

  public void logCardSelectMessage() {
    logger.info("You Selected " + cardName + " Card!");
  }

  public abstract void applyCardStatus(PlayerShip playerShip);
}
