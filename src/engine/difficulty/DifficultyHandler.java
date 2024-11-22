package engine.difficulty;

import engine.GameSettings;

public interface DifficultyHandler {
    GameSettings adjustSettings(int formationWidth, int formationHeight, int baseSpeed, int shootingFrequency, int level);
}
