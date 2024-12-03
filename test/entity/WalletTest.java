package entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalletTest {

  private Wallet wallet;

  @BeforeEach
  void setup() {
    wallet = new Wallet(); // 테스트 전에 지갑 객체 생성
  }

  @Test
  void testInitialWalletState() {
    // 초기 지갑 상태 확인
    assertNotNull(wallet, "지갑 객체가 null이 아닙니다.");
    assertEquals(0, wallet.getCoin(), "초기 코인은 0이어야 합니다.");
    assertEquals(1, wallet.getBullet_lv(), "초기 bullet_lv는 1이어야 합니다.");
    assertEquals(1, wallet.getShot_lv(), "초기 shot_lv는 1이어야 합니다.");
    assertEquals(1, wallet.getLives_lv(), "초기 lives_lv는 1이어야 합니다.");
    assertEquals(1, wallet.getCoin_lv(), "초기 coin_lv는 1이어야 합니다.");
  }

  @Test
  void testDepositPositiveAmount() {
    int depositAmount = 100; // 입금할 금액
    boolean result = wallet.deposit(depositAmount); // 입금
    assertTrue(result, "입금은 성공해야 합니다."); // 입금 성공
    assertEquals(depositAmount, wallet.getCoin(), "코인 수가 올바르지 않습니다."); // 코인 수 확인
  }

  @Test
  void testDepositNegativeAmount() { // 음수 금액 입금 시도
    int depositAmount = -50;
    boolean result = wallet.deposit(depositAmount);
    assertFalse(result, "음수 금액 입금은 실패해야 합니다.");
    assertEquals(0, wallet.getCoin(), "코인 수는 변함이 없어야 합니다.");
  }

  @Test
  void testWithdrawSufficientFunds() { // 잔액이 충분할 때 출금 시도
    wallet.deposit(100);
    int withdrawAmount = 50;
    boolean result = wallet.withdraw(withdrawAmount);
    assertTrue(result, "출금은 성공해야 합니다.");
    assertEquals(50, wallet.getCoin(), "코인 수가 올바르지 않습니다.");
  }

  @Test
  void testWithdrawInsufficientFunds() { // 잔액이 부족할 때 출금 시도
    int withdrawAmount = 50;
    boolean result = wallet.withdraw(withdrawAmount);
    assertFalse(result, "잔액 부족으로 출금은 실패해야 합니다.");
    assertEquals(0, wallet.getCoin(), "코인 수는 변함이 없어야 합니다.");
  }

  @Test
  void testUpgradeBulletLevel() { // Bullet 레벨 업그레이드
    int initialLevel = wallet.getBullet_lv();
    wallet.setBullet_lv(initialLevel + 1);
    assertEquals(initialLevel + 1, wallet.getBullet_lv(), "Bullet 레벨 업그레이드가 올바르지 않습니다.");
  }
}
