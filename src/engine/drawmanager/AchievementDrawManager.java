package engine.drawmanager;

import engine.DrawManager;
import engine.Score;
import screen.Screen;

import java.awt.*;
import java.util.List;

/**
 * Manages drawing for the Achievement screen.
 */
public class AchievementDrawManager extends DrawManager {

    public AchievementDrawManager() {
        super();
    }

    /**
     * Draws achievement screen title and instructions.
     *
     * @param screen
     *            Screen to draw on.
     */
    public static void drawAchievementMenu(final Screen screen, final int totalScore, final int totalPlayTime, final int maxCombo,
                                           final int currentPerfectStage, final int nextPerfectStage, boolean checkFlawlessFailure) {
        //high score section
        String highScoreTitle = "High Scores";

        //cumulative section
        String totalScoreTitle = "Total Score";
        String totalPlayTimesTitle = "-Total  Playtime-";

        // centered strings
        String achievementTitle = "Achievement";
        String instructionsString = "Press ESC to return";
        String achievementsStatusTitle = "Achievements Status";
        String achievementsExplain = "Applies to single-player play only";

        // Achievements names
        String maxComboTitle = " Combo Mastery ";
        String perfectClearTitle = "perfect clear";
        String flawlessFailureTitle = "Flawless Failure";
        String eternityTimeTitle = "A time of eternity";

        // draw "perfect clear"
        if (currentPerfectStage <= 6) {
            String[] PERFECT_COIN_REWARD = { "200", "400", "800", "2000", "3000", "4000", "5000"}; // 퍼펙트 스테이지 리워드
            backBufferGraphics.setColor(Color.orange);
            drawRightSideAchievementCoinBigString(screen, PERFECT_COIN_REWARD[currentPerfectStage],
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*3);

            backBufferGraphics.setColor(Color.green);
            drawRightSideAchievementSmallString_1(screen,"current",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*2+7);
            backBufferGraphics.setColor(Color.red);
            drawRightSideAchievementSmallString_2(screen,"target",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*2+7);

            String sampleAchievementsString2 = "lv." + currentPerfectStage + "   =>  lv." +
                    nextPerfectStage;
            backBufferGraphics.setColor(Color.WHITE);
            drawRightSideAchievementBigString(screen, sampleAchievementsString2,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*3);

        }
        else{
            backBufferGraphics.setColor(Color.gray);
            drawRightSideAchievementCoinBigString(screen, "5000",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*3);

            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementSmallEventString2(screen, "You clear all levels perfectly",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*2+fontBigMetrics.getHeight()*3-5);

            String sampleAchievementsString2 = " 100% Clear !! ";
            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementBigString(screen, sampleAchievementsString2,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()*3);

        }

        // draw "achievement"
        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredBigString(screen, achievementTitle, screen.getHeight() / 8);

        // draw instruction
        backBufferGraphics.setColor(Color.GRAY);
        drawCenteredRegularString(screen, instructionsString,
                screen.getHeight() / 8 + fontRegularMetrics.getHeight());

        backBufferGraphics.setColor(Color.cyan);
        drawCenteredRegularString(screen, achievementsExplain,
                screen.getHeight() / 7 + fontBigMetrics.getHeight() );

        // draw "high score"
        backBufferGraphics.setColor(Color.GREEN);
        drawLeftSideScoreRegularString(screen, highScoreTitle,
                screen.getHeight() / 5+ fontBigMetrics.getHeight());

        // draw total score
        backBufferGraphics.setColor(Color.yellow);
        drawRightSideCumulativeRegularString(screen, totalScoreTitle,
                screen.getHeight() / 5 + fontBigMetrics.getHeight());

        // draw "Total play-time"
        backBufferGraphics.setColor(Color.yellow);
        drawRightSideCumulativeRegularString(screen, totalPlayTimesTitle,
                screen.getHeight() / 5 + 2*fontRegularMetrics.getHeight()+2* fontBigMetrics.getHeight()+10 );

        // draw "Total Score"
        backBufferGraphics.setColor(Color.WHITE);
        String totalScoreString = String.format("%s", totalScore);
        drawRightSideCumulativeBigString(screen, totalScoreString, screen.getHeight() / 3
                - fontRegularMetrics.getHeight() + 10);

        // draw "achievement status"
        backBufferGraphics.setColor(Color.MAGENTA);
        drawCenteredBigString(screen, achievementsStatusTitle,
                screen.getHeight() / 2 + fontBigMetrics.getHeight() );



        // draw "high accuracy"
        backBufferGraphics.setColor(Color.WHITE);
        drawLeftSideAchievementRegularString(screen, maxComboTitle,
                screen.getHeight() /2 + fontRegularMetrics.getHeight()*3+fontBigMetrics.getHeight()+7);

        // draw "Perfect clear"
        backBufferGraphics.setColor(Color.WHITE);
        drawLeftSideAchievementRegularString(screen, perfectClearTitle,
                screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*2+7);

        // draw "Flawless Failure"
        backBufferGraphics.setColor(Color.WHITE);
        drawLeftSideAchievementRegularString(screen, flawlessFailureTitle,
                screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*3+5);

        // draw "best friends"
        backBufferGraphics.setColor(Color.WHITE);
        drawLeftSideAchievementRegularString(screen, eternityTimeTitle,
                screen.getHeight() /2 + fontRegularMetrics.getHeight()*6+fontBigMetrics.getHeight()*4+3);

        int totalHours = totalPlayTime / 3600;
        int remainHours = totalPlayTime % 3600;

        int totalMinutes = remainHours / 60;
        int remainMinutes = remainHours % 60;

        int totalSeconds = remainMinutes % 60;

        // draw total play time record
        String totalPlayTimeeString = String.format("%02dH %02dm %02ds",totalHours,totalMinutes,totalSeconds);
        backBufferGraphics.setColor(Color.WHITE);
        drawRightSideCumulativeBigString(screen, totalPlayTimeeString, screen.getHeight() / 2
                - fontRegularMetrics.getHeight() - 15);

        // draw accuracy reward
        final String[] ACCURACY_COIN_REWARD = {"500", "1500", "2000", "2500"};

        // draw accuracy achievement
        if (maxCombo >= 25) {
            backBufferGraphics.setColor(Color.gray);
            drawRightSideAchievementCoinBigString(screen, ACCURACY_COIN_REWARD[3],
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*2+fontBigMetrics.getHeight()*2);

            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementSmallEventString(screen, "You record high combo",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*2+fontBigMetrics.getHeight()+8);

            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementBigString(screen, "You are crazy!",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*2+fontBigMetrics.getHeight()*2);
        } else {
            backBufferGraphics.setColor(Color.orange);

            drawRightSideAchievementComboString_1(screen, "combo",
                    screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 5+5);
            drawRightSideAchievementComboString_2(screen, "combo",
                    screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 5+5);


            backBufferGraphics.setColor(Color.green);
            drawRightSideAchievementSmallString_1(screen, "current",
                    screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 4 - 2);
            backBufferGraphics.setColor(Color.red);
            drawRightSideAchievementSmallString_2(screen, "target",
                    screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 4 - 2);
            if (maxCombo < 10) {
                backBufferGraphics.setColor(Color.orange);
                drawRightSideAchievementCoinBigString(screen, ACCURACY_COIN_REWARD[0],
                        screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 2 + fontBigMetrics.getHeight() * 2);

                backBufferGraphics.setColor(Color.WHITE);
                String accuracyAchievement = String.format("             %d", maxCombo) + " =>" + "         10";
                drawRightSideAchievementBigString(screen, accuracyAchievement,
                        screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 5 + 5);
            } else {
                backBufferGraphics.setColor(Color.orange);
                drawRightSideAchievementCoinBigString(screen, ACCURACY_COIN_REWARD[maxCombo <= 9 ? 0 : maxCombo / 5 - 1],
                        screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 2 + fontBigMetrics.getHeight() * 2);

                backBufferGraphics.setColor(Color.WHITE);
                String accuracyAchievement = String.format("             %d", maxCombo) + " =>" + String.format("         %d", maxCombo <= 9 ? 10 : (((( (maxCombo - 10) / 5) + 1) * 5 ) + 10));
                drawRightSideAchievementBigString(screen, accuracyAchievement,
                        screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 5 + 5);
            }
        }

        // draw flawless failure achievement
        String flawlessFailureReward = "1000";
        if (checkFlawlessFailure) {
            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementBigString(screen, "Complete!",
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*4-5);
            backBufferGraphics.setColor(Color.gray);
            drawRightSideAchievementCoinBigString(screen, flawlessFailureReward,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*4-5);

        } else {
            String explainFlawlessFailure_1 = "    Achieved when the game ends";
            String explainFlawlessFailure_2 = "                with 0% accuracy.";
            backBufferGraphics.setColor(Color.GRAY);
            drawRightSideAchievementSmallString_3(screen, explainFlawlessFailure_1,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*3+fontSmallMetrics.getHeight());

            backBufferGraphics.setColor(Color.GRAY);
            drawRightSideAchievementSmallString_3(screen, explainFlawlessFailure_2,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*3+fontSmallMetrics.getHeight()*2);
            backBufferGraphics.setColor(Color.orange);
            drawRightSideAchievementCoinBigString(screen, flawlessFailureReward,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*4+fontBigMetrics.getHeight()*4-5);
        }

        // draw play time achievement
        String eternityTimeReward = "1000";
        String sampleAchievementsString = "complete!";
        String explainEternityTime_1 = "              Total play time ";
        String explainEternityTime_2 = "        must exceed 10 minutes...";
        if (totalPlayTime >= 600) {
            backBufferGraphics.setColor(Color.GREEN);
            drawRightSideAchievementBigString(screen, sampleAchievementsString,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*5-5);
            backBufferGraphics.setColor(Color.gray);
            drawRightSideAchievementCoinBigString(screen, eternityTimeReward,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*5-5);

        } else {
            backBufferGraphics.setColor(Color.GRAY);
            drawRightSideAchievementSmallString_3(screen, explainEternityTime_1,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*4+fontSmallMetrics.getHeight());
            backBufferGraphics.setColor(Color.GRAY);
            drawRightSideAchievementSmallString_3(screen, explainEternityTime_2,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*4+fontSmallMetrics.getHeight()*2);
            backBufferGraphics.setColor(Color.orange);
            drawRightSideAchievementCoinBigString(screen, eternityTimeReward,
                    screen.getHeight() /2 + fontRegularMetrics.getHeight()*5+fontBigMetrics.getHeight()*5-5);

        }
    }

    /**
     * Draws high scores.
     *
     * @param screen
     *            Screen to draw on.
     * @param highScores
     *            List of high scores.
     */
    public static void drawHighScores(final Screen screen,
                                      final List<Score> highScores) {
        backBufferGraphics.setColor(Color.WHITE);
        int i = 0;
        String scoreString = "";

        final int limitDrawingScore = 3;
        int countDrawingScore = 0;
        for (Score score : highScores) {
            scoreString = String.format("%s        %04d", score.getName(),
                    score.getScore());
            drawLeftSideScoreRegularString(screen, scoreString, screen.getHeight()
                    / 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
            i++;
            countDrawingScore++;
            if(countDrawingScore>=limitDrawingScore){
                break;
            }
        }
    }

    // left side score
    public static void drawLeftSideScoreRegularString(final Screen screen,
                                                      final String string, final int height) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(string, screen.getWidth() / 4
                - fontRegularMetrics.stringWidth(string) / 2, height);
    }
    public static void drawLeftSideScoreSmallString(final Screen screen,
                                                    final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() / 4
                - fontRegularMetrics.stringWidth(string) / 3, height);
    }

    //right side Cumulative score
    public static void drawRightSideCumulativeRegularString(final Screen screen,
                                                            final String string, final int height) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(string, screen.getWidth() *71/ 100
                - fontRegularMetrics.stringWidth(string)/2 , height);
    }
    public static void drawRightSideCumulativeBigString(final Screen screen,
                                                        final String string, final int height) {
        backBufferGraphics.setFont(fontBig);
        backBufferGraphics.drawString(string, screen.getWidth() *71/ 100
                - fontBigMetrics.stringWidth(string)/2, height);
    }

    // left side achievement
    public static void drawLeftSideAchievementRegularString(final Screen screen,
                                                     final String string, final int height) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(string, screen.getWidth() *22/ 100
                - fontRegularMetrics.stringWidth(string) / 2, height);
    }
    public static void drawLeftSideAchievementSmallString(final Screen screen,
                                                   final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *26/ 100
                - fontRegularMetrics.stringWidth(string) / 2, height);
    }

    // right side achievement(sample)
    public static void drawRightSideAchievementSmallEventString(final Screen screen,
                                                         final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *65/100-
                fontRegularMetrics.stringWidth(string)/2, height);
    }
    public static void drawRightSideAchievementSmallEventString2(final Screen screen,
                                                           final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *68/100-
                fontRegularMetrics.stringWidth(string)/2, height);
    }

    public static void drawRightSideAchievementBigString(final Screen screen,
                                                  final String string, final int height) {
        backBufferGraphics.setFont(fontBig);
        backBufferGraphics.drawString(string, screen.getWidth() *63/100-
                fontRegularMetrics.stringWidth(string), height);
    }
    public static void drawRightSideAchievementComboString_1(final Screen screen,
                                                      final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *52/100-
                fontRegularMetrics.stringWidth(string), height);
    }public static void drawRightSideAchievementComboString_2(final Screen screen,
                                                       final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *74/100-
                fontRegularMetrics.stringWidth(string), height);
    }
    public static void drawRightSideAchievementSmallString_1(final Screen screen,
                                                      final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *59/100-
                fontRegularMetrics.stringWidth(string), height);
    }public static void drawRightSideAchievementSmallString_2(final Screen screen,
                                                       final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() *77/100-
                fontRegularMetrics.stringWidth(string), height);
    }

    public static void drawRightSideAchievementSmallString_3(final Screen screen,
                                                      final String string, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, screen.getWidth() / 2-
                fontRegularMetrics.stringWidth(string) / 7, height);
    }

    public static void drawRightSideAchievementCoinBigString(final Screen screen,
                                                             final String string, final int height) {
        backBufferGraphics.setFont(fontBig);
        backBufferGraphics.drawString(string, screen.getWidth()*81/100 , height);
    }

}
