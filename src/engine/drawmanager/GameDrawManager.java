package engine.drawmanager;

import engine.Core;
import engine.DrawManager;
import engine.Score;
import entity.Entity;
import entity.ShipFactory;
import entity.player.PlayerShip;
import screen.Screen;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class GameDrawManager extends DrawManager {

    public GameDrawManager() {super();}

    public void drawGameTitle(final Screen screen) {
        String titleString = "Invaders";
        backBufferGraphics.setColor(Color.DARK_GRAY);
        drawCenteredBigString(screen, titleString, screen.getHeight() / 2);
    }

    public void drawGameTitle(final Screen screen, final int threadNumber) {
        String titleString = "Invaders";
        threadBufferGraphics[threadNumber].setColor(Color.DARK_GRAY);
        drawCenteredBigString(screen, titleString, screen.getHeight() / 2, threadNumber);
    }

    /**

     * Draws alert message on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param alertMessage
     *            Alert message.
     */
    public void drawAlertMessage(final Screen screen, final String alertMessage) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.RED);
        backBufferGraphics.drawString(alertMessage,
                (screen.getWidth() - fontRegularMetrics.stringWidth(alertMessage))/2, 65);
    }

    /**

     * Draws alert message on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param alertMessage
     *            Alert message.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawAlertMessage(final Screen screen, final String alertMessage, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.RED);
        threadBufferGraphics[threadNumber].drawString(alertMessage,
                (screen.getWidth() - fontRegularMetrics.stringWidth(alertMessage))/2, 65);
    }

    /**

     * Draws number of remaining lives on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param lives
     *            Current lives.
     */
    public void drawLives(final Screen screen, final int lives, final PlayerShip.ShipType shipType) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
        PlayerShip dummyPlayerShip = ShipFactory.create(shipType, 0, 0);
        for (int i = 0; i < lives; i++)
            drawEntity(dummyPlayerShip, 40 + 35 * i, 10);
    }

    /**
     * Draws number of remaining lives on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param lives
     *            Current lives.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawLives(final Screen screen, final int lives, final PlayerShip.ShipType shipType, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        threadBufferGraphics[threadNumber].drawString(Integer.toString(lives), 20, 25);
        PlayerShip dummyPlayerShip = ShipFactory.create(shipType, 0, 0);
        for (int i = 0; i < lives; i++)
            drawEntity(dummyPlayerShip, 40 + 35 * i, 10, threadNumber);
    }

    /**
     * Draws level on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param level
     *            Current level.
     */
    public void drawLevel(final Screen screen, final int level) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        String scoreString = String.format("lv.%d", level);
        backBufferGraphics.drawString(scoreString, 20, 25);
    }
    /**
     * Draws level on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param level
     *            Current level.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawLevel(final Screen screen, final int level, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        String scoreString = String.format("lv.%d", level);
        threadBufferGraphics[threadNumber].drawString(scoreString, 20, 25);
    }

    /**
     * Draws current score on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param score
     *            Current score.
     */
    public void drawScore(final Screen screen, final int score) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        String scoreString = String.format("%04d", score);
        backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
    }

    /**
     * Draws current score on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param score
     *            Current score.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawScore(final Screen screen, final int score, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        String scoreString = String.format("%04d", score);
        threadBufferGraphics[threadNumber].drawString(scoreString, screen.getWidth() - 60, 25);
    }

    /**
     * Draws Combo on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param combo
     *            Number of enemies killed in a row.
     */
    public void drawCombo(final Screen screen, final int combo) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        if (combo >= 2) {
            String comboString = String.format("Combo %03d", combo);
            backBufferGraphics.drawString(comboString, screen.getWidth() - 100, 85);
        }
    }
    /**
     * Draws Combo on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param combo
     *            Number of enemies killed in a row.
     * @param threadNumber
     * 			  Thread number for two player mode
     */
    public void drawCombo(final Screen screen, final int combo, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        if (combo >= 2) {
            String comboString = String.format("Combo %03d", combo);
            threadBufferGraphics[threadNumber].drawString(comboString, screen.getWidth() - 100, 85);
        }
    }

    /**
     * Draws ReloadTimer on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param playerShip
     *            player's ship.
     * @param remainingTime
     *            remaining reload time.
     */
    public void drawReloadTimer(final Screen screen, final PlayerShip playerShip, final long remainingTime) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        if(remainingTime > 0){

            int shipX = playerShip.getPositionX();
            int shipY = playerShip.getPositionY();
            int shipWidth = playerShip.getWidth();
            int circleSize = 16;
            int startAngle = 90;
            int endAngle = 0;
            switch(Core.BASE_SHIP){
                case PlayerShip.ShipType.VoidReaper:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 0.4);
                    break;
                case PlayerShip.ShipType.CosmicCruiser:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.6);
                    break;
                case PlayerShip.ShipType.StarDefender:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.0);
                    break;
                case PlayerShip.ShipType.GalacticGuardian:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.2);
                    break;


            }

            backBufferGraphics.fillArc(shipX + shipWidth/2 - circleSize/2, shipY - 3*circleSize/2,
                    circleSize, circleSize, startAngle, endAngle);
        }
    }

    /**
     * Draws ReloadTimer on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param playerShip
     *            player's ship.
     * @param remainingTime
     *            remaining reload time.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawReloadTimer(final Screen screen, final PlayerShip playerShip, final long remainingTime, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        if(remainingTime > 0){

            int shipX = playerShip.getPositionX();
            int shipY = playerShip.getPositionY();
            int shipWidth = playerShip.getWidth();
            int circleSize = 16;
            int startAngle = 90;
            int endAngle = 0;
            switch(Core.BASE_SHIP){
                case PlayerShip.ShipType.VoidReaper:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 0.4);
                    break;
                case PlayerShip.ShipType.CosmicCruiser:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.6);
                    break;
                case PlayerShip.ShipType.StarDefender:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.0);
                    break;
                case PlayerShip.ShipType.GalacticGuardian:
                    endAngle = 360 * (int)remainingTime / (int)(750 * 1.2);
                    break;
            }
            threadBufferGraphics[threadNumber].fillArc(shipX + shipWidth/2 - circleSize/2, shipY - 3*circleSize/2,
                    circleSize, circleSize, startAngle, endAngle);
        }
    }

    /**
     * Draws elapsed time on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param elapsedTime
     *            Elapsed time.
     */
    public void drawElapsedTime(final Screen screen, final int elapsedTime) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.LIGHT_GRAY);

        int cent = (elapsedTime % 1000)/10;
        int seconds = elapsedTime / 1000;
        int sec = seconds % 60;
        int min = seconds / 60;

        String elapsedTimeString;
        if (min < 1){
            elapsedTimeString = String.format("%d.%02d", sec, cent);
        } else {
            elapsedTimeString = String.format("%d:%02d.%02d", min, sec, cent);
        }
        backBufferGraphics.drawString(elapsedTimeString, 80, 25);
    }

    /**
     * Draws elapsed time on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param elapsedTime
     *            Elapsed time.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawElapsedTime(final Screen screen, final int elapsedTime, final int threadNumber) {
        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.LIGHT_GRAY);

        int cent = (elapsedTime % 1000)/10;
        int seconds = elapsedTime / 1000;
        int sec = seconds % 60;
        int min = seconds / 60;

        String elapsedTimeString;
        if (min < 1){
            elapsedTimeString = String.format("%d.%02d", sec, cent);
        } else {
            elapsedTimeString = String.format("%d:%02d.%02d", min, sec, cent);
        }
        threadBufferGraphics[threadNumber].drawString(elapsedTimeString, 80, 25);
    }

    /**
     * Draws an entity, using the appropriate image.
     *
     * @param entity
     *            Entity to be drawn.
     * @param positionX
     *            Coordinates for the left side of the image.
     * @param positionY
     *            Coordinates for the upper side of the image.
     */
    public static void drawEntity(final Entity entity, final int positionX,
                                  final int positionY) {
        boolean[][] image = spriteMap.get(entity.getSpriteType());

        backBufferGraphics.setColor(entity.getColor());
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++)
                if (image[i][j])
                    backBufferGraphics.drawRect(positionX + i * 2, positionY
                            + j * 2, 1, 1);
    }

    /**
     * Draws an entity, using the appropriate image.
     *
     * @param entity
     *            Entity to be drawn.
     * @param positionX
     *            Coordinates for the left side of the image.
     * @param positionY
     *            Coordinates for the upper side of the image.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public static void drawEntity(final Entity entity, final int positionX,
                                  final int positionY, final int threadNumber) {
        boolean[][] image = spriteMap.get(entity.getSpriteType());

        threadBufferGraphics[threadNumber].setColor(entity.getColor());
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[i].length; j++)
                if (image[i][j])
                    threadBufferGraphics[threadNumber].drawRect(positionX + i * 2, positionY
                            + j * 2, 1, 1);
    }

    //Drawing an Entity (Blocker) that requires angle setting
    public static void drawRotatedEntity(Entity entity, int x, int y, double angle) {
        Graphics2D g2d = (Graphics2D) backBufferGraphics; // Convert to Graphics2D
        AffineTransform oldTransform = g2d.getTransform(); // Save previous conversion

        //Set center point to rotate
        int centerX = x + entity.getWidth() / 2;
        int centerY = y + entity.getHeight() / 2;

        //rotate by a given angle
        g2d.rotate(Math.toRadians(angle), centerX, centerY);

        //Drawing entities
        drawEntity(entity, x, y);

        g2d.setTransform(oldTransform); // Restore to original conversion state
    }

    //Drawing an Entity (Blocker) that requires angle setting
    public static void drawRotatedEntity(Entity entity, int x, int y, double angle, final int threadNumber) {
        Graphics2D g2d = (Graphics2D) threadBufferGraphics[threadNumber]; // Convert to Graphics2D
        AffineTransform oldTransform = g2d.getTransform(); // Save previous conversion

        //Set center point to rotate
        int centerX = x + entity.getWidth() / 2;
        int centerY = y + entity.getHeight() / 2;

        //rotate by a given angle
        g2d.rotate(Math.toRadians(angle), centerX, centerY);

        //Drawing entities
        drawEntity(entity, x, y, threadNumber);

        g2d.setTransform(oldTransform); // Restore to original conversion state
    }

    /**
     * Draws launch trajectory on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param positionX
     *            X coordinate of the line.
     */

    public void drawLaunchTrajectory(final Screen screen, final int positionX) {
        backBufferGraphics.setColor(Color.DARK_GRAY);
        for (int i = 0; i < screen.getHeight() - 140; i += 20){
            backBufferGraphics.drawRect(positionX + 13, screen.getHeight() - 150 - i,1,10);
        }
    }
    /**
     * Draws launch trajectory on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param positionX
     *            X coordinate of the line.
     */
    public void drawLaunchTrajectory(final Screen screen, final int positionX,
                                     final int threadNumber) {
        threadBufferGraphics[threadNumber].setColor(Color.DARK_GRAY);
        for (int i = 0; i < screen.getHeight() - 140; i += 20){
            threadBufferGraphics[threadNumber].drawRect(positionX + 13, screen.getHeight() - 150 - i,1,10);
        }
    }

    /**
     * Draws a thick line from side to side of the screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param positionY
     *            Y coordinate of the line.
     */
    public void drawHorizontalLine(final Screen screen, final int positionY) {
        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
        backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
                positionY + 1);
    }

    /**
     * Draws a thick line from side to side of the screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param positionY
     *            Y coordinate of the line.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawHorizontalLine(final Screen screen, final int positionY, final int threadNumber) {
        threadBufferGraphics[threadNumber].setColor(Color.GREEN);
        threadBufferGraphics[threadNumber].drawLine(0, positionY, screen.getWidth(), positionY);
        threadBufferGraphics[threadNumber].drawLine(0, positionY + 1, screen.getWidth(),
                positionY + 1);
    }

    /**
     * Draws a horizontal bar composed of multiple box segments.
     *
     * @param x             X coordinate of the bar's starting point.
     * @param y             Y coordinate of the bar's starting point.
     * @param w             Total width of the bar.
     * @param h             Height of each box segment.
     * @param currentValue  Current value to display (how many boxes are filled).
     * @param maximumValue  Maximum value (total number of boxes).
     * @param color         Color of the filled boxes.
     */
    public void drawSegmentedBar(int x, int y, int w, int h, int currentValue, int maximumValue, Color color) {
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

    /**
     * Draws a thicker box outline by drawing two overlapping boxes.
     *
     * @param screen    Screen to draw on.
     * @param x         X coordinate of the upper-left corner of the inner box.
     * @param y         Y coordinate of the upper-left corner of the inner box.
     * @param w         Width of the inner box.
     * @param h         Height of the inner box.
     * @param thickness Thickness of the outer box.
     */
    public void drawThickBox(final Screen screen, final int x, final int y, final int w, final int h, final int thickness) {
        // Ensure thickness is not greater than the width or height
        if (thickness * 2 > w || thickness * 2 > h) {
            throw new IllegalArgumentException("Thickness is too large for the given width or height");
        }

        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.fillRect(x, y, w, h); // Outer box
        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(x + thickness, y + thickness, w - 2 * thickness, h - 2 * thickness); // Draw inner box
    }

    /**
     * Countdown to game start.
     *
     * @param screen
     *            Screen to draw on.
     * @param level
     *            Game difficulty level.
     * @param number
     *            Countdown number.
     */

    public void drawCountDown(final Screen screen, final int level,
                              final int number) {
        int rectWidth = screen.getWidth();
        int rectHeight = screen.getHeight() / 6;
        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
                rectWidth, rectHeight);
        backBufferGraphics.setColor(Color.GREEN);
        if (number >= 4)
            drawCenteredBigString(screen, "Level " + level,
                    screen.getHeight() / 2
                            + fontBigMetrics.getHeight() / 3);
        else if (number != 0)
            drawCenteredBigString(screen, Integer.toString(number),
                    screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
        else
            drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
                    + fontBigMetrics.getHeight() / 3);
    }

    /**
     * Countdown to game start.
     *
     * @param screen
     *            Screen to draw on.
     * @param level
     *            Game difficulty level.
     * @param number
     *            Countdown number.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawCountDown(final Screen screen, final int level,
                              final int number, final int threadNumber) {
        int rectWidth = screen.getWidth();
        int rectHeight = screen.getHeight() / 6;
        threadBufferGraphics[threadNumber].setColor(Color.BLACK);
        threadBufferGraphics[threadNumber].fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
                rectWidth, rectHeight);
        threadBufferGraphics[threadNumber].setColor(Color.GREEN);
        if (number >= 4)
            drawCenteredBigString(screen, "Level " + level,
                    screen.getHeight() / 2
                            + fontBigMetrics.getHeight() / 3, threadNumber);
        else if (number != 0)
            drawCenteredBigString(screen, Integer.toString(number),
                    screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3, threadNumber);
        else
            drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
                    + fontBigMetrics.getHeight() / 3, threadNumber);
    }

    /**
     * Draws game over for 2player mode
     *
     * @param screen
     *            Screen to draw on.
     * @param height
     *            Height of the drawing.
     * @param threadNumber
     *            Thread number for two player mode
     */
    public void drawInGameOver(final Screen screen,
                               final int height, final int threadNumber) {
        String gameOverString = "Game Over";

        int rectWidth = screen.getWidth();
        int rectHeight = screen.getHeight() / 6;
        threadBufferGraphics[threadNumber].setColor(Color.BLACK);
        threadBufferGraphics[threadNumber].fillRect(0, screen.getHeight() / 2 - rectHeight / 2, rectWidth, rectHeight);
        threadBufferGraphics[threadNumber].setColor(Color.GREEN);

        drawCenteredBigString(screen, gameOverString,
                screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3, threadNumber);

    }

    /**
     * Draws recorded highscores on screen.
     *
     * @param highScores
     *            Recorded highscores.
     */

    public void drawRecord(List<Score> highScores, final Screen screen) {

        //add variable for highest score
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

        backBufferGraphics.drawString(highScoreDisplay,
                screen.getWidth() - metrics.stringWidth(highScoreDisplay) - 76, 25);
    }

    /**
     * Draws recorded highscores on screen.
     *
     * @param highScores
     *            Recorded highscores.
     * @param threadNumber
     *            Thread number for two player mode
     */

    public void drawRecord(List<Score> highScores, final Screen screen, final int threadNumber) {

        //add variable for highest score
        int highestScore = -1;
        String highestPlayer = "";

        // find the highest score from highScores list
        for (Score entry : highScores) {
            if (entry.getScore() > highestScore) {
                highestScore = entry.getScore();
                highestPlayer = entry.getName();
            }
        }


        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.LIGHT_GRAY);
        FontMetrics metrics = threadBufferGraphics[threadNumber].getFontMetrics(fontRegular);
        String highScoreDisplay = highestPlayer + " " + highestScore;

        threadBufferGraphics[threadNumber].drawString(highScoreDisplay,
                screen.getWidth() - metrics.stringWidth(highScoreDisplay) - 76, 25);
    }

    /**
     * Draws intermediate aggregation on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param maxCombo
     *            Value of maxCombo.
     * @param elapsedTime
     *            Value of elapsedTime.
     * @param lapTime
     *            Value of lapTime/prevTime.
     * @param score
     *            Value of score/prevScore.
     * @param tempScore
     *            Value of tempScore.
     */
    public static void interAggre(final Screen screen, final int level, final int maxCombo,
                                  final int elapsedTime, final int lapTime,
                                  final int score, final int tempScore) {

        int prevTime = elapsedTime - lapTime;
        int prevScore = score - tempScore;

        int pcent = (prevTime % 1000)/10;
        int pseconds = prevTime / 1000;
        int psec = pseconds % 60;
        int pmin = pseconds / 60;

        String timeString;
        if (pmin < 1){
            timeString = String.format("Elapsed Time: %d.%02d", psec, pcent);
        } else {
            timeString = String.format("Elapsed Time: %d:%02d.%02d", pmin, psec, pcent);
        }

        String levelString = String.format("Statistics at Level %d", level);
        String comboString = String.format("MAX COMBO: %03d", maxCombo);
        String scoreString = String.format("Scores earned: %04d", prevScore);

        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.drawString(levelString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(levelString))/2,
                5*screen.getHeight()/7);
        backBufferGraphics.setColor(Color.WHITE);
        backBufferGraphics.drawString(comboString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(comboString))/2,
                5*screen.getHeight()/7 + 21);
        backBufferGraphics.drawString(timeString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(timeString))/2,
                5*screen.getHeight()/7 + 42);
        backBufferGraphics.drawString(scoreString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(scoreString))/2,
                5*screen.getHeight()/7 + 63);
    }

    /**
     * Draws intermediate aggregation on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param maxCombo
     *            Value of maxCombo.
     * @param elapsedTime
     *            Value of elapsedTime.
     * @param lapTime
     *            Value of lapTime/prevTime.
     * @param score
     *            Value of score/prevScore.
     * @param tempScore
     *            Value of tempScore.
     * @param threadNumber
     * 			  Thread number for two player mode
     */
    public void interAggre(final Screen screen, final int level, final int maxCombo,
                           final int elapsedTime, final int lapTime,
                           final int score, final int tempScore, final int threadNumber) {

        int prevTime = elapsedTime - lapTime;
        int prevScore = score - tempScore;

        int pcent = (prevTime % 1000)/10;
        int pseconds = prevTime / 1000;
        int psec = pseconds % 60;
        int pmin = pseconds / 60;

        String timeString;
        if (pmin < 1){
            timeString = String.format("Elapsed Time: %d.%02d", psec, pcent);
        } else {
            timeString = String.format("Elapsed Time: %d:%02d.%02d", pmin, psec, pcent);
        }

        String levelString = String.format("Statistics at Level %d", level);
        String comboString = String.format("MAX COMBO: %03d", maxCombo);
        String scoreString = String.format("Scores earned: %04d", prevScore);

        threadBufferGraphics[threadNumber].setFont(fontRegular);
        threadBufferGraphics[threadNumber].setColor(Color.GREEN);
        threadBufferGraphics[threadNumber].drawString(levelString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(levelString))/2,
                5*screen.getHeight()/7);
        threadBufferGraphics[threadNumber].setColor(Color.WHITE);
        threadBufferGraphics[threadNumber].drawString(comboString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(comboString))/2,
                5*screen.getHeight()/7 + 21);
        threadBufferGraphics[threadNumber].drawString(timeString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(timeString))/2,
                5*screen.getHeight()/7 + 42);
        threadBufferGraphics[threadNumber].drawString(scoreString,
                (screen.getWidth() - fontRegularMetrics.stringWidth(scoreString))/2,
                5*screen.getHeight()/7 + 63);
    }

    /**
     * Flush buffer to second buffer
     *
     * @param screen
     *            Screen to draw on.
     * @param threadNumber
     * 			  Thread number for two player mode
     */
    public static void flushBuffer(final Screen screen, final int threadNumber) {
        BufferedImage threadBuffer = new BufferedImage(screen.getWidth(),screen.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics threadGraphic = threadBuffer.getGraphics();

        threadGraphic.drawImage(threadBuffers[threadNumber], 0, 0, frame);
        threadBuffers[threadNumber + 2] = threadBuffer;
    }

}
