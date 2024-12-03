package entity.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerLevelTest {

  private PlayerLevel playerLevel;

  @BeforeEach
  void setup() {
    playerLevel = new PlayerLevel(0, 1);
  }

  @Test
  void testIsLevelUpPossible() {
    // 경험치가 부족한 경우
    playerLevel.setExp(50);
    assertFalse(playerLevel.isLevelUpPossible());

    // 경험치가 충분한 경우
    playerLevel.setExp(100);
    assertTrue(playerLevel.isLevelUpPossible());
  }

  @Test
  void testLevelUp() {
    // 경험치를 충분히 설정
    playerLevel.setExp(150);

    // 레벨 업 전 상태 확인
    assertEquals(1, playerLevel.getLevel());
    assertEquals(150, playerLevel.getExp());

    // 레벨 업 수행
    playerLevel.levelUp();

    // 레벨 업 후 상태 확인
    assertEquals(2, playerLevel.getLevel());
    assertEquals(50, playerLevel.getExp()); // 150 - 100 (레벨 1에서 레벨 업에 필요한 경험치)

    // 추가로 레벨 업 가능한지 확인
    assertFalse(playerLevel.isLevelUpPossible());
  }

  @Test
  void testSetAndGetExp() {
    playerLevel.setExp(200);
    assertEquals(200, playerLevel.getExp());
  }
}
