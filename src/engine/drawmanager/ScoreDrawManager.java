package engine.drawmanager;

import engine.DrawManager;
import java.awt.*;
import screen.Screen;

public class ScoreDrawManager extends DrawManager {

  /**
   * Draws basic content of game over screen.
   *
   * @param screen Screen to draw on.
   * @param acceptsInput If the screen accepts input.
   * @param isNewRecord If the score is a new high score.
   */
  public static void drawGameOver(
      final Screen screen, final boolean acceptsInput, final boolean isNewRecord) {
    String gameOverString = "Game Over";
    String continueOrExitString = "Press Space to play again, Escape to exit";

    int height = isNewRecord ? 4 : 2;

    backBufferGraphics.setColor(Color.GREEN);
    drawCenteredBigString(
        screen, gameOverString, screen.getHeight() / height - fontBigMetrics.getHeight() * 2);

    if (acceptsInput) backBufferGraphics.setColor(Color.GREEN);
    else backBufferGraphics.setColor(Color.GRAY);
    drawCenteredRegularString(
        screen, continueOrExitString, screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
  }

  /**
   * Draws game results.
   *
   * @param screen Screen to draw on.
   * @param score Score obtained.
   * @param playerHp Lives remaining when finished.
   * @param shipsDestroyed Total ships destroyed.
   * @param accuracy Total accuracy.
   * @param isNewRecord If the score is a new high score.
   */
  public static void drawResults(
      final Screen screen,
      final int score,
      final int playerHp,
      final int shipsDestroyed,
      final double accuracy,
      final boolean isNewRecord,
      final int coinsEarned) {
    String scoreString = String.format("score %04d", score);
    String playerHpString = "lives remaining " + playerHp;
    String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
    String accuracyString = String.format("accuracy %.2f%%", accuracy);
    String coinsEarnedString = "EARNED COIN " + coinsEarned;

    int height = isNewRecord ? 4 : 2;

    backBufferGraphics.setColor(Color.WHITE);
    drawCenteredRegularString(screen, scoreString, screen.getHeight() / height);
    drawCenteredRegularString(
        screen, playerHpString, screen.getHeight() / height + fontRegularMetrics.getHeight() * 2);
    drawCenteredRegularString(
        screen,
        shipsDestroyedString,
        screen.getHeight() / height + fontRegularMetrics.getHeight() * 4);
    drawCenteredRegularString(
        screen, accuracyString, screen.getHeight() / height + fontRegularMetrics.getHeight() * 6);
    backBufferGraphics.setColor(Color.YELLOW);
    drawCenteredRegularString(
        screen,
        coinsEarnedString,
        screen.getHeight() / height + fontRegularMetrics.getHeight() * 9);
  }
}
