package engine.drawmanager;

import engine.DrawManager;
import java.awt.*;
import screen.Screen;

/** Manages drawing for the Title Screen. */
public class TitleDrawManager extends DrawManager {

  /**
   * Draws game title.
   *
   * @param screen Screen to draw on.
   */
  public static void drawTitle(final Screen screen) {
    String titleString = "Invaders";
    String instructionsString = "select with w+s / arrows, confirm with space";

    backBufferGraphics.setColor(Color.GRAY);
    drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5 * 2);

    backBufferGraphics.setColor(Color.GREEN);
    drawCenteredBigString(screen, titleString, screen.getHeight() / 5);
  }

  /**
   * Draws main menu.
   *
   * @param screen Screen to draw on.
   * @param option Option selected.
   */
  public static void drawMenu(final Screen screen, final int option, final int coin) {
    String playString = "Play";
    String shopString = "SHOP";
    String coinString = "YOUR COIN: " + coin;
    String achievementString = "ACHIEVEMENT";
    String settingString = "SETTING";
    String exitString = "EXIT";

    if (option == 6) /*option2 => Game Settings */ backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(screen, playString, screen.getHeight() / 7 * 4);

    if (option == 3) /*option3 => Shop */ backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, shopString, screen.getHeight() / 7 * 4 + fontRegularMetrics.getHeight() * 2);

    backBufferGraphics.setColor(Color.ORANGE);
    drawCenteredSmallString(
        screen, coinString, screen.getHeight() / 7 * 4 + fontRegularMetrics.getHeight() * 3);

    if (option == 4) /*option4 => Achievement */ backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, achievementString, screen.getHeight() / 7 * 4 + fontRegularMetrics.getHeight() * 5);

    if (option == 5) /*option5 => Setting */ backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, settingString, screen.getHeight() / 7 * 4 + fontRegularMetrics.getHeight() * 7);

    if (option == 0) /*option0 => exit */ backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, exitString, screen.getHeight() / 7 * 4 + fontRegularMetrics.getHeight() * 9);
  }
}
