package engine.difficulty;

import engine.GameSettings;

public class NormalDifficultyHandler implements DifficultyHandler {
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
      int adjustBaseSpeed =
          isEvenLevelUnderFive ? 10 : 12; // 5레벨 이상의 경우 baseSpeed 15 -> 12로 난이도 하향조정
      int adjustShootingFrequency =
          isEvenLevelUnderFive ? 200 : 230; // 5레벨 이상의 경우 shootingFrequency 300 -> 230으로 난이도 하향조정

      if (formationWidth == formationHeight && formationWidth < 12)
        formationWidth += 1; // Width 증가 기준 14 -> 16 난이도 하향 조정
      else if (formationHeight < 8) formationHeight += 1; // Height 증가 기준 10 -> 8 난이도 하향 조정

      baseSpeed = Math.max(baseSpeed - adjustBaseSpeed, -100); // -150 -> 100으로 속도 난이도 하향조정
      shootingFrequency =
          (shootingFrequency - adjustShootingFrequency > adjustShootingFrequency)
              ? shootingFrequency - adjustShootingFrequency
              : 100;
    }
    return new GameSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency);
  }
}
