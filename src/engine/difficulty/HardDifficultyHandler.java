package engine.difficulty;

import engine.GameSettings;

public class HardDifficultyHandler implements DifficultyHandler {
  @Override
  public GameSettings adjustSettings(
      int formationWidth,
      int formationHeight,
      int baseSpeed,
      int shootingFrequency,
      int gameLevel) {
    boolean isEvenLevelUnderFive = (gameLevel % 2 == 0 && gameLevel < 5);
    boolean isLevelAtLeastFive = gameLevel >= 5;

    if (isEvenLevelUnderFive || isLevelAtLeastFive) {
      int adjustFormationWidth = isEvenLevelUnderFive ? 1 : 2;
      int adjustFormationHeight = isEvenLevelUnderFive ? 1 : 2;
      int adjustShootingFrequency = isEvenLevelUnderFive ? 300 : 400;

      if (formationWidth == formationHeight && formationWidth < 14)
        formationWidth += adjustFormationWidth;
      else if (formationHeight < 10) formationHeight += adjustFormationHeight;

      baseSpeed = Math.max(baseSpeed - 20, -150);
      shootingFrequency =
          (shootingFrequency - adjustShootingFrequency > adjustShootingFrequency)
              ? shootingFrequency - adjustShootingFrequency
              : 100;
    }
    return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
  }
}
