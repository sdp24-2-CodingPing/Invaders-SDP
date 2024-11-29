package engine.drawmanager;

import engine.Core;
import engine.DrawManager;
import engine.Score;
import entity.Entity;
import entity.player.PlayerLevel;
import entity.player.PlayerShip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import screen.Screen;

public class GameDrawManager extends DrawManager {

  public GameDrawManager() {
    super();
  }

  public static void drawGameTitle(final Screen screen) {
    String titleString = "Invaders";
    backBufferGraphics.setColor(Color.DARK_GRAY);
    drawCenteredBigString(screen, titleString, screen.getHeight() / 2);
  }

  /**
   * Draws alert message on screen.
   *
   * @param screen Screen to draw on.
   * @param alertMessage Alert message.
   */
  public static void drawAlertMessage(final Screen screen, final String alertMessage) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.RED);
    backBufferGraphics.drawString(
        alertMessage, (screen.getWidth() - fontRegularMetrics.stringWidth(alertMessage)) / 2, 65);
  }

  /**
   * Draws level on screen.
   *
   * @param screen Screen to draw on.
   * @param level Current level.
   */
  public static void drawLevel(final Screen screen, final int level) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.WHITE);
    String scoreString = String.format("lv.%d", level);
    backBufferGraphics.drawString(scoreString, 20, 25);
  }

  /**
   * Draws current score on screen.
   *
   * @param screen Screen to draw on.
   * @param score Current score.
   */
  public static void drawScore(final Screen screen, final int score) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.WHITE);
    String scoreString = String.format("%04d", score);
    backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
  }

  /**
   * Draws Combo on screen.
   *
   * @param screen Screen to draw on.
   * @param combo Number of enemies killed in a row.
   */
  public static void drawCombo(final Screen screen, final int combo) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.WHITE);
    if (combo >= 2) {
      String comboString = String.format("Combo %03d", combo);
      backBufferGraphics.drawString(comboString, screen.getWidth() - 100, 85);
    }
  }

  /**
   * Draws ReloadTimer on screen.
   *
   * @param screen Screen to draw on.
   * @param playerShip player's ship.
   * @param remainingTime remaining reload time.
   */
  public static void drawReloadTimer(
      final Screen screen, final PlayerShip playerShip, final long remainingTime) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.WHITE);
    if (remainingTime > 0) {

      int shipX = playerShip.getPositionX();
      int shipY = playerShip.getPositionY();
      int shipWidth = playerShip.getWidth();
      int circleSize = 16;
      int startAngle = 90;
      int endAngle = 0;
      switch (Core.BASE_SHIP) {
        case PlayerShip.ShipType.VoidReaper:
          endAngle = 360 * (int) remainingTime / (int) (750 * 0.4);
          break;
        case PlayerShip.ShipType.CosmicCruiser:
          endAngle = 360 * (int) remainingTime / (int) (750 * 1.6);
          break;
        case PlayerShip.ShipType.StarDefender:
          endAngle = 360 * (int) remainingTime / (int) (750 * 1.0);
          break;
        case PlayerShip.ShipType.GalacticGuardian:
          endAngle = 360 * (int) remainingTime / (int) (750 * 1.2);
          break;
      }

      backBufferGraphics.fillArc(
          shipX + shipWidth / 2 - circleSize / 2,
          shipY - 3 * circleSize / 2,
          circleSize,
          circleSize,
          startAngle,
          endAngle);
    }
  }

  /**
   * Draws elapsed time on screen.
   *
   * @param screen Screen to draw on.
   * @param elapsedTime Elapsed time.
   */
  public static void drawElapsedTime(final Screen screen, final int elapsedTime) {
    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.LIGHT_GRAY);

    int cent = (elapsedTime % 1000) / 10;
    int seconds = elapsedTime / 1000;
    int sec = seconds % 60;
    int min = seconds / 60;

    String elapsedTimeString;
    if (min < 1) {
      elapsedTimeString = String.format("%d.%02d", sec, cent);
    } else {
      elapsedTimeString = String.format("%d:%02d.%02d", min, sec, cent);
    }
    backBufferGraphics.drawString(elapsedTimeString, 80, 25);
  }

  /**
   * Draws an entity, using the appropriate image.
   *
   * @param entity Entity to be drawn.
   * @param positionX Coordinates for the left side of the image.
   * @param positionY Coordinates for the upper side of the image.
   */
  public static void drawEntity(final Entity entity, final int positionX, final int positionY) {
    boolean[][] image = spriteMap.get(entity.getSpriteType());

    backBufferGraphics.setColor(entity.getColor());
    for (int i = 0; i < image.length; i++)
      for (int j = 0; j < image[i].length; j++)
        if (image[i][j]) backBufferGraphics.drawRect(positionX + i * 2, positionY + j * 2, 1, 1);
  }

  // Drawing an Entity (Blocker) that requires angle setting
  public static void drawRotatedEntity(Entity entity, int x, int y, double angle) {
    Graphics2D g2d = (Graphics2D) backBufferGraphics; // Convert to Graphics2D
    AffineTransform oldTransform = g2d.getTransform(); // Save previous conversion

    // Set center point to rotate
    int centerX = x + entity.getWidth() / 2;
    int centerY = y + entity.getHeight() / 2;

    // rotate by a given angle
    g2d.rotate(Math.toRadians(angle), centerX, centerY);

    // Drawing entities
    drawEntity(entity, x, y);

    g2d.setTransform(oldTransform); // Restore to original conversion state
  }

  /**
   * Draws launch trajectory on screen.
   *
   * @param screen Screen to draw on.
   * @param positionX X coordinate of the line.
   */
  public static void drawLaunchTrajectory(final Screen screen, final int positionX) {
    backBufferGraphics.setColor(Color.DARK_GRAY);
    for (int i = 0; i < screen.getHeight() - 140; i += 20) {
      backBufferGraphics.drawRect(positionX + 13, screen.getHeight() - 150 - i, 1, 10);
    }
  }

  /**
   * Draws a thick line from side to side of the screen.
   *
   * @param screen Screen to draw on.
   * @param positionY Y coordinate of the line.
   */
  public static void drawHorizontalLine(final Screen screen, final int positionY) {
    backBufferGraphics.setColor(Color.GREEN);
    backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
    backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(), positionY + 1);
  }

  /**
   * Draws a horizontal bar composed of multiple box segments.
   *
   * @param x X coordinate of the bar's starting point.
   * @param y Y coordinate of the bar's starting point.
   * @param w Total width of the bar.
   * @param h Height of each box segment.
   * @param currentValue Current value to display (how many boxes are filled).
   * @param maximumValue Maximum value (total number of boxes).
   * @param color Color of the filled boxes.
   */
  public static void drawSegmentedBar(
      int x, int y, int w, int h, int currentValue, int maximumValue, Color color) {
    int basicWidth = w / maximumValue; // Basic width of each box
    int remainingWidth = w % maximumValue; // Remaining width to be distributed

    // Draw filled and empty boxes
    int startX = x; // Initial x coordinate for the first box
    for (int i = 0; i < maximumValue; i++) {
      int boxWidth = basicWidth;
      if (i < remainingWidth) {
        boxWidth += 1; // Distribute the remaining width to the first few boxes
      }

      if (i < currentValue) {
        backBufferGraphics.setColor(color); // Color for filled boxes
      } else {
        backBufferGraphics.setColor(Color.GRAY); // Color for empty boxes
      }

      backBufferGraphics.fillRect(startX, y, boxWidth, h);
      startX += boxWidth; // Update startX for the next box
    }
  }

  /** Draws a stat value */
  public static void drawStat(final Screen screen, final int stat, int x, int y) {
    int BOX_WIDTH = 48;
    int BOX_HEIGHT = 48;
    backBufferGraphics.setColor(Color.WHITE);
    String statText = Integer.toString(stat);

    FontMetrics metrics = backBufferGraphics.getFontMetrics(fontBig);
    int textWidth = metrics.stringWidth(statText);
    int textHeight = metrics.getHeight();

    int drawX = x + (BOX_WIDTH - textWidth) / 2;
    int drawY = y + (BOX_HEIGHT + textHeight) / 2;

    backBufferGraphics.drawString(statText, drawX, drawY);
  }

  /** Draws a stat icon */
  public static void drawStatIcon(Screen screen, int num, int x, int y) {
    BufferedImage[] statImages =
        new BufferedImage[] {
          img_movespeed,
          img_bulletspeed,
          img_attackdamage,
          img_shotinterval,
          img_bulletscount,
          img_additionallife
        };
    backBufferGraphics.drawImage(statImages[num], x, y, 22, 14, null);
  }

  /** Draws a player level */
  public static void drawPlayerLevel(final Screen screen, final PlayerLevel level, int x, int y) {
    backBufferGraphics.setFont(fontBig);
    backBufferGraphics.setColor(Color.WHITE);

    int levelValue = level.getLevel();
    if (levelValue < 10) {
      String scoreString = String.format("lv.0%d", levelValue);
      backBufferGraphics.drawString(scoreString, x, y);
    } else {
      String scoreString = String.format("lv.%d", levelValue);
      backBufferGraphics.drawString(scoreString, x, y);
    }
  }

  /**
   * Countdown to game start.
   *
   * @param screen Screen to draw on.
   * @param level Game difficulty level.
   * @param number Countdown number.
   */
  public static void drawCountDown(final Screen screen, final int level, final int number) {
    int rectWidth = screen.getWidth();
    int rectHeight = screen.getHeight() / 6;
    backBufferGraphics.setColor(Color.BLACK);
    backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2, rectWidth, rectHeight);
    backBufferGraphics.setColor(Color.GREEN);
    if (number >= 4)
      drawCenteredBigString(
          screen, "Level " + level, screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
    else if (number != 0)
      drawCenteredBigString(
          screen,
          Integer.toString(number),
          screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
    else
      drawCenteredBigString(screen, "GO!", screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
  }

  /**
   * Draws recorded highscores on screen.
   *
   * @param highScores Recorded highscores.
   */
  public static void drawRecord(List<Score> highScores, final Screen screen) {

    // add variable for highest score
    int highestScore = -1;
    String highestPlayer = "";

    // find the highest score from highScores list
    for (Score entry : highScores) {
      if (entry.getScore() > highestScore) {
        highestScore = entry.getScore();
        highestPlayer = entry.getName();
      }
    }

    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.LIGHT_GRAY);
    FontMetrics metrics = backBufferGraphics.getFontMetrics(fontRegular);
    String highScoreDisplay = highestPlayer + " " + highestScore;

    backBufferGraphics.drawString(
        highScoreDisplay, screen.getWidth() - metrics.stringWidth(highScoreDisplay) - 76, 25);
  }

  /**
   * Draws intermediate aggregation on screen.
   *
   * @param screen Screen to draw on.
   * @param maxCombo Value of maxCombo.
   * @param elapsedTime Value of elapsedTime.
   * @param lapTime Value of lapTime/prevTime.
   * @param score Value of score/prevScore.
   * @param tempScore Value of tempScore.
   */
  public static void interAggre(
      final Screen screen,
      final int level,
      final int maxCombo,
      final int elapsedTime,
      final int lapTime,
      final int score,
      final int tempScore) {

    int prevTime = elapsedTime - lapTime;
    int prevScore = score - tempScore;

    int pcent = (prevTime % 1000) / 10;
    int pseconds = prevTime / 1000;
    int psec = pseconds % 60;
    int pmin = pseconds / 60;

    String timeString;
    if (pmin < 1) {
      timeString = String.format("Elapsed Time: %d.%02d", psec, pcent);
    } else {
      timeString = String.format("Elapsed Time: %d:%02d.%02d", pmin, psec, pcent);
    }

    String levelString = String.format("Statistics at Level %d", level);
    String comboString = String.format("MAX COMBO: %03d", maxCombo);
    String scoreString = String.format("Scores earned: %04d", prevScore);

    backBufferGraphics.setFont(fontRegular);
    backBufferGraphics.setColor(Color.GREEN);
    backBufferGraphics.drawString(
        levelString,
        (screen.getWidth() - fontRegularMetrics.stringWidth(levelString)) / 2,
        5 * screen.getHeight() / 7);
    backBufferGraphics.setColor(Color.WHITE);
    backBufferGraphics.drawString(
        comboString,
        (screen.getWidth() - fontRegularMetrics.stringWidth(comboString)) / 2,
        5 * screen.getHeight() / 7 + 21);
    backBufferGraphics.drawString(
        timeString,
        (screen.getWidth() - fontRegularMetrics.stringWidth(timeString)) / 2,
        5 * screen.getHeight() / 7 + 42);
    backBufferGraphics.drawString(
        scoreString,
        (screen.getWidth() - fontRegularMetrics.stringWidth(scoreString)) / 2,
        5 * screen.getHeight() / 7 + 63);
  }
}
