package engine.difficulty;

import engine.GameSettings;

public class HardDifficultyHandler implements DifficultyHandler {
    @Override
    public GameSettings adjustSettings(int formationWidth, int formationHeight, int baseSpeed, int shootingFrequency, int gameLevel) {
        if (gameLevel % 2 == 0 && gameLevel < 5) {
            if (formationWidth == formationHeight && formationWidth < 14) formationWidth += 1;
            else if (formationHeight < 10) formationHeight += 1;
            baseSpeed = Math.max(baseSpeed - 20, -150);
            shootingFrequency = (shootingFrequency - 300 > 300) ? shootingFrequency - 300 : 100;
        } else if (gameLevel >= 5) {
            if (formationWidth == formationHeight && formationWidth < 14) formationWidth += 2;
            else if (formationHeight < 10) formationHeight += 2;
            baseSpeed = Math.max(baseSpeed - 20, -150);
            shootingFrequency = (shootingFrequency - 400 > 400) ? shootingFrequency - 400 : 100;
        }
        return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
    }
}
