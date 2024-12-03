package entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BulletPoolTest {

  @BeforeEach
  void setup() {
    BulletPool.clearPool(); // 테스트 전에 풀을 비움
  }

  @Test
  void testGetBulletFromEmptyPool() {
    int positionX = 100, positionY = 200, speed = 5; // 탄환의 초기 위치와 속도

    Bullet bullet = BulletPool.getBullet(positionX, positionY, speed, 10); // 풀에서 탄환 요청

    assertNotNull(bullet, "Bullet should not be null"); // 반환된 탄환이 null이 아닌지 확인
    assertEquals(
        positionX - bullet.getWidth() / 2, bullet.getPositionX(), "X position mismatch"); // X 위치 확인
    assertEquals(positionY, bullet.getPositionY(), "Y position mismatch"); // Y 위치 확인
    assertEquals(speed, bullet.getSpeed(), "Speed mismatch"); // 속도 확인
  }

  @Test
  void testGetBulletFromNonEmptyPool() {
    // 탄환 생성
    Bullet bullet = new Bullet(50, 60, 5, 10);
    Set<Bullet> bullets = new HashSet<>();
    bullets.add(bullet);
    BulletPool.recycle(bullets);

    Bullet recycledBullet = BulletPool.getBullet(100, 200, -10, 10); // 풀에서 재활용된 탄환 요청

    assertNotNull(recycledBullet, "Recycled bullet should not be null"); // 반환된 탄환이 null이 아닌지 확인
    assertEquals(
        100 - recycledBullet.getWidth() / 2,
        recycledBullet.getPositionX(),
        "X position mismatch after recycling");
    assertEquals(200, recycledBullet.getPositionY(), "Y position mismatch after recycling");
    assertEquals(-10, recycledBullet.getSpeed(), "Speed mismatch after recycling");

    assertTrue(BulletPool.isPoolEmpty(), "Pool should be empty after recycling"); // 풀이 비어 있어야 함
  }

  @Test
  void testRecycleBullets() {
    // 탄환 생성
    Bullet bullet1 = new Bullet(50, 60, 5, 10);
    Bullet bullet2 = new Bullet(70, 80, -5, 10);
    Set<Bullet> bullets = new HashSet<>();
    bullets.add(bullet1);
    bullets.add(bullet2);

    BulletPool.recycle(bullets); // 풀에 탄환 추가

    assertFalse(
        BulletPool.isPoolEmpty(), "Pool should not be empty after recycling"); // 풀이 비어 있지 않아야 함
    assertEquals(2, BulletPool.getPoolSize(), "Pool size mismatch after recycling"); // 풀 크기 확인
  }

  @Test
  void testClearPool() {
    // 탄환 생성
    Bullet bullet = new Bullet(50, 60, 5, 10);
    Set<Bullet> bullets = new HashSet<>();
    bullets.add(bullet);
    BulletPool.recycle(bullets);

    BulletPool.clearPool(); // 풀 비우기

    assertTrue(BulletPool.isPoolEmpty(), "Pool should be empty after clearing"); // 풀이 비어 있어야 함
  }
}
