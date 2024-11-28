package engine.difficulty;

import engine.GameSettings;

public class EasyDifficultyHandler implements DifficultyHandler {
  @Override
  public GameSettings adjustSettings(
      int formationWidth,
      int formationHeight,
      int baseSpeed,
      int shootingFrequency,
      int gameLevel) {
    boolean isMultipleOfThreeLevelUnderFive = (gameLevel % 3 == 0 && gameLevel < 5);
    boolean isEvenLevelAtLeastFive = (gameLevel % 2 == 0 && gameLevel >= 5);

    if (isMultipleOfThreeLevelUnderFive || isEvenLevelAtLeastFive) {
      if (formationWidth == formationHeight && formationWidth < 14) formationWidth += 1;
      else if (formationHeight < 10) formationHeight += 1;

      baseSpeed = Math.max(baseSpeed - 10, -150);
      shootingFrequency = Math.max(shootingFrequency - 100, 100);
    }
    return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
  }
}
