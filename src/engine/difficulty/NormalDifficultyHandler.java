package engine.difficulty;

import engine.GameSettings;

public class NormalDifficultyHandler implements DifficultyHandler {
    @Override
    public GameSettings adjustSettings(int formationWidth, int formationHeight, int baseSpeed, int shootingFrequency, int gameLevel) {
        boolean isEvenLevelUnderFive = (gameLevel % 2 == 0 && gameLevel < 5);
        boolean isLevelAtLeastFive = gameLevel >= 5;

        if (isEvenLevelUnderFive || isLevelAtLeastFive) {
            int adjustBaseSpeed = isEvenLevelUnderFive ? 10 : 20;
            int adjustShootingFrequency = isEvenLevelUnderFive ? 200 : 300;

            if (formationWidth == formationHeight && formationWidth < 14) formationWidth += 1;
            else if (formationHeight < 10) formationHeight += 1;

            baseSpeed = Math.max(baseSpeed - adjustBaseSpeed, -150);
            shootingFrequency = (shootingFrequency - adjustShootingFrequency > adjustShootingFrequency) ? shootingFrequency - adjustShootingFrequency : 100;
        }
        return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
    }
}
