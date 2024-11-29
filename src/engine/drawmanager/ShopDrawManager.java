package engine.drawmanager;

import engine.Cooldown;
import engine.DrawManager;
import entity.Wallet;
import java.awt.*;
import java.awt.image.BufferedImage;
import screen.Screen;

/** Manages drawing for the Shop Screen. */
public class ShopDrawManager extends DrawManager {

  /**
   * draw shop
   *
   * @param screen Screen to draw on.
   * @param option selected shop item
   * @param wallet player's wallet
   * @param money_alertcooldown cooldown for insufficient coin alert
   * @param max_alertcooldown cooldown for max level alert
   */
  public static void drawShop(
      final Screen screen,
      final int option,
      final Wallet wallet,
      final Cooldown money_alertcooldown,
      final Cooldown max_alertcooldown) {

    String shopString = "Shop";
    int shopStringY = Math.round(screen.getHeight() * 0.15f);

    String coinString = ":  " + wallet.getCoin();
    String exitString = "PRESS \"ESC\" TO RETURN TO MAIN MENU";
    String[] costs = new String[] {"2000", "4000", "8000", "MAX LEVEL"};

    String[] itemString =
        new String[] {"BULLET SPEED", "SHOT INTERVAL", "ADDITIONAL LIFE", "COIN GAIN"};
    int[] walletLevel =
        new int[] {
          wallet.getBullet_lv(), wallet.getShot_lv(), wallet.getLives_lv(), wallet.getCoin_lv()
        };

    BufferedImage[] itemImages =
        new BufferedImage[] {img_bulletspeed, img_shotinterval, img_additionallife, img_coingain};

    int imgstartx = screen.getWidth() / 80 * 23;
    int imgstarty = screen.getHeight() / 80 * 27;
    int imgdis = screen.getHeight() / 80 * 12;
    int coinstartx = screen.getWidth() / 80 * 55;
    int coinstarty = screen.getHeight() / 160 * 66;
    int coindis = screen.getHeight() / 80 * 12;
    int coinSize = 20;
    int cointextstartx = screen.getWidth() / 80 * 60;
    int cointextstarty = screen.getHeight() / 160 * 71;
    int cointextdis = screen.getHeight() / 80 * 12;

    backBufferGraphics.setColor(Color.GREEN);
    drawCenteredBigString(screen, shopString, shopStringY);
    backBufferGraphics.drawImage(
        img_coin,
        screen.getWidth() / 80 * 39 - (coinString.length() - 3) * screen.getWidth() / 80,
        screen.getHeight() / 80 * 18,
        coinSize,
        coinSize,
        null);
    backBufferGraphics.setColor(Color.WHITE);
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.drawString(
        coinString,
        screen.getWidth() / 80 * 44 - (coinString.length() - 3) * screen.getWidth() / 80,
        screen.getHeight() / 80 * 20);

    for (int i = 0; i < 4; i++) {
      backBufferGraphics.setColor(Color.WHITE);
      drawCenteredRegularString(screen, itemString[i], screen.getHeight() / 80 * (28 + 12 * i));
      for (int j = 0; j < 3; j++) {
        if (j + 2 <= walletLevel[i]) {
          backBufferGraphics.setColor(Color.GREEN);
          backBufferGraphics.fillRect(
              screen.getWidth() / 40 * (33 / 2) + j * (screen.getWidth() / 10),
              screen.getHeight() / 80 * (30 + 12 * i),
              20,
              20);
        } else {
          backBufferGraphics.setColor(Color.WHITE);
          backBufferGraphics.fillRect(
              screen.getWidth() / 40 * (33 / 2) + j * (screen.getWidth() / 10),
              screen.getHeight() / 80 * (30 + 12 * i),
              20,
              20);
        }
      }
    }

    backBufferGraphics.setColor(Color.WHITE);
    backBufferGraphics.drawImage(
        itemImages[option - 1], imgstartx, imgstarty + (imgdis * (option - 1)), 50, 40, null);
    backBufferGraphics.drawImage(
        img_coin, coinstartx, coinstarty + (coindis * (option - 1)), coinSize, coinSize, null);
    backBufferGraphics.drawString(
        "X " + costs[walletLevel[option - 1] - 1],
        cointextstartx,
        cointextstarty + (cointextdis * (option - 1)));

    backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(screen, exitString, screen.getHeight() / 80 * 80);

    if (!money_alertcooldown.checkFinished()) {
      backBufferGraphics.setColor(Color.red);
      backBufferGraphics.fillRect(
          (screen.getWidth() - 300) / 2, (screen.getHeight() - 100) / 2, 300, 80);
      backBufferGraphics.setColor(Color.black);
      drawCenteredBigString(screen, "Insufficient coin", screen.getHeight() / 2);
    }
    if (!max_alertcooldown.checkFinished()) {
      backBufferGraphics.setColor(Color.red);
      backBufferGraphics.fillRect(
          (screen.getWidth() - 300) / 2, (screen.getHeight() - 100) / 2, 300, 80);
      backBufferGraphics.setColor(Color.black);
      drawCenteredBigString(screen, "Already max level", screen.getHeight() / 2);
    }
  }
}
