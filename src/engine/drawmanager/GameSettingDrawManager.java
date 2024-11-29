package engine.drawmanager;

import engine.DrawManager;
import java.awt.*;
import screen.Screen;

public class GameSettingDrawManager extends DrawManager {

  /**
   * Draws the game setting screen.
   *
   * @param screen Screen to draw on.
   */
  public static void drawGameSetting(final Screen screen) {
    String titleString = "Game Setting";

    backBufferGraphics.setColor(Color.GREEN);
    drawCenteredBigString(screen, titleString, screen.getHeight() / 100 * 25);
  }

  /**
   * Draws the game setting row.
   *
   * @param screen Screen to draw on.
   * @param selectedRow Selected row.
   * @author <a href="mailto:dayeon.dev@gmail.com">Dayeon Oh</a>
   */
  public static void drawGameSettingRow(final Screen screen, final int selectedRow) {
    int y = 0;
    int height = 0;
    int screenHeight = screen.getHeight();

    if (selectedRow == 0) {
      y = screenHeight / 100 * 35;
      height = screen.getHeight() / 100 * 28;
    } else if (selectedRow == 1) {
      y = screenHeight / 100 * 63;
      height = screen.getHeight() / 100 * 18;
    } else if (selectedRow == 2) {
      y = screenHeight / 100 * 92;
      height = screen.getHeight() / 100 * 10;
    }

    backBufferGraphics.setColor(Color.DARK_GRAY);
    backBufferGraphics.fillRect(0, y, screen.getWidth(), height);
  }

  /**
   * Draws the game setting elements.
   *
   * @param screen Screen to draw on.
   * @param selectedRow Selected row.
   * @param name Player name.
   * @param difficultyLevel Difficulty level.
   * @author <a href="mailto:dayeon.dev@gmail.com">Dayeon Oh</a>
   */
  public static void drawGameSettingElements(
      final Screen screen, final int selectedRow, final String name, final int difficultyLevel) {
    String spaceString = " ";
    String playerString = "Player";
    String levelEasyString = "Easy";
    String levelNormalString = "Normal";
    String levelHardString = "Hard";
    String startString = "Start";

    if (selectedRow == 0) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);

    drawCenteredRegularString(screen, playerString, screen.getHeight() / 100 * 43);
    drawCenteredRegularString(screen, name, screen.getHeight() / 100 * 58);

    if (difficultyLevel == 0) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, levelEasyString + spaceString.repeat(60), screen.getHeight() / 100 * 73);

    if (difficultyLevel == 1) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(screen, levelNormalString, screen.getHeight() / 100 * 73);

    if (difficultyLevel == 2) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(
        screen, spaceString.repeat(60) + levelHardString, screen.getHeight() / 100 * 73);

    if (selectedRow == 2) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(screen, startString, screen.getHeight() / 100 * 98);
  }
}
