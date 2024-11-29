package engine.difficulty;

import engine.GameSettings;

public class EasyDifficultyHandler implements DifficultyHandler {
    @Override
    public GameSettings adjustSettings(int formationWidth, int formationHeight, int baseSpeed, int shootingFrequency, int gameLevel) {
        boolean isMultipleOfThreeLevelUnderFive = (gameLevel % 3 == 0 && gameLevel < 5); //3레벨에만 해당
        boolean isEvenLevelAtLeastFive = (gameLevel % 2 == 0 && gameLevel >= 5); //6,8,10...레벨에 해당

        if (isMultipleOfThreeLevelUnderFive || isEvenLevelAtLeastFive) {
            if (formationWidth == formationHeight && formationWidth < 10) formationWidth += 1; //width 증가 기준 14 -> 10으로, 난이도 하향조정
            else if (formationHeight < 8) formationHeight += 1; //height 증가 기준 12 -> 8 으로, 난이도 하향조정

            baseSpeed = Math.max(baseSpeed - 10, -100); //-150 -> 100으로 속도 난이도 하향조정
            shootingFrequency = Math.max(shootingFrequency - 100, 100);
        }
        return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
    }
}
