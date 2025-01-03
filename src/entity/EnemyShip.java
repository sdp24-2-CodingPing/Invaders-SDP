package entity;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import engine.GameState;
import engine.Sound;
import engine.SoundManager;
import java.awt.Color;
import java.util.Random;

/**
 * Implements a enemy ship, to be destroyed by the player.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public class EnemyShip extends Entity {
  /** Point value of a type A enemy. */
  private static final int A_TYPE_POINTS = 10;

  /** EXP value of a type A enemy. */
  private static final int A_TYPE_EXP = 10;

  /** Point value of a type B enemy. */
  private static final int B_TYPE_POINTS = 20;

  /** EXP value of a type B enemy. */
  private static final int B_TYPE_EXP = 20;

  /** Point value of a type C enemy. */
  private static final int C_TYPE_POINTS = 30;

  /** EXP value of a type C enemy. */
  private static final int C_TYPE_EXP = 30;

  /** Point value of a type D enemy. */
  private static final int D_TYPE_POINTS = 40;

  /** EXP value of a type D enemy. */
  private static final int D_TYPE_EXP = 40;

  /** Point value of a type E enemy. */
  private static final int E_TYPE_POINTS = 50;

  /** EXP value of a type E enemy. */
  private static final int E_TYPE_EXP = 50;

  /** Point value of a type F enemy */
  private static final int F_TYPE_POINTS = 60;

  /** EXP value of a type F enemy. */
  private static final int F_TYPE_EXP = 60;

  /** Point value of a bonus enemy. */
  private static final int BONUS_TYPE_POINTS = 100;

  /** EXP value of a type bonus enemy. */
  private static final int BONUS_TYPE_EXP = 100;

  /** Cooldown between sprite changes. */
  private Cooldown animationCooldown;

  /** Checks if the ship has been hit by a bullet. */
  private boolean isDestroyed;

  /** Values of the ship, in points, when destroyed. */
  private int pointValue;

  /** Values of the ship, in EXP, when destroyed. */
  private int expValue;

  /** Singleton instance of SoundManager */
  private final SoundManager soundManager = SoundManager.getInstance();

  private EnemyType enemyType;
  private double health;
  private int attackDamage;

  public enum EnemyType {
    GRUNT,
    ELITE,
    CHAMPION
  }

  /**
   * Constructor, establishes the ship's properties.
   *
   * @param positionX Initial position of the ship in the X axis.
   * @param positionY Initial position of the ship in the Y axis.
   * @param spriteType Sprite type, image corresponding to the ship.
   */
  public EnemyShip(
      final int positionX,
      final int positionY,
      final SpriteType spriteType,
      final GameState gameState) {
    super(positionX, positionY, 12 * 2, 8 * 2, Color.BLUE);

    this.enemyType = determineEnemyType();
    super.setColor(getColorByEnemyType(this.enemyType));
    this.spriteType = spriteType;
    this.animationCooldown = Core.getCooldown(500);
    this.isDestroyed = false;
    initializeHealth(gameState, this.enemyType);
    initializeExp(gameState, this.enemyType);
    initializePoint(gameState, this.enemyType);
    initializeAttackDamage(gameState, this.enemyType);
  }

  /** Assigns base health for each enemy types, considering level scaling. */
  private void initializeHealth(GameState gameState, EnemyType enemyType) {
    double baseHealth = 10;
    if (enemyType.equals(EnemyType.ELITE)) baseHealth = 30;
    else if (enemyType.equals(EnemyType.CHAMPION)) baseHealth = 70;

    double levelMultiplier = Math.pow(1.05, gameState.getGameLevel() - 1);
    this.health = baseHealth * levelMultiplier;
  }

  /**
   * Assign base attack damage for each enemy types, considering level scaling.
   *
   * @param gameState gameState instance
   * @param enemyType enemy type
   */
  private void initializeAttackDamage(GameState gameState, EnemyType enemyType) {
    // base exp for enemies.
    // index 0: GRUNT, index 1: ELITE, index 2: CHAMPION
    final int[] BASE_ATTACK_DAMAGE = {1, 2, 3};

    double levelMultiplier = Math.pow(2.0, gameState.getGameLevel() - 1);
    switch (enemyType) {
      case GRUNT:
        this.attackDamage = BASE_ATTACK_DAMAGE[0] * (int) levelMultiplier;
        break;
      case ELITE:
        this.attackDamage = BASE_ATTACK_DAMAGE[1] * (int) levelMultiplier;
        break;
      case CHAMPION:
        this.attackDamage = BASE_ATTACK_DAMAGE[2] * (int) levelMultiplier;
        break;
    }
  }

  /**
   * Assign base point for each enemy types, considering level scaling.
   *
   * @param gameState gameState instance
   * @param enemyType enemy type
   */
  private void initializePoint(GameState gameState, EnemyType enemyType) {
    // base exp for enemies.
    // index 0: GRUNT, index 1: ELITE, index 2: CHAMPION
    final int[] BASE_POINT = {10, 20, 30};

    double levelMultiplier = Math.pow(1.05, gameState.getGameLevel() - 1);
    switch (enemyType) {
      case GRUNT:
        this.pointValue = BASE_POINT[0] * (int) levelMultiplier;
        break;
      case ELITE:
        this.pointValue = BASE_POINT[1] * (int) levelMultiplier;
        break;
      case CHAMPION:
        this.pointValue = BASE_POINT[2] * (int) levelMultiplier;
        break;
    }
  }

  /**
   * Assign base exp for each enemy types, considering level scaling.
   *
   * @param gameState gameState instance
   * @param enemyType enemy type
   */
  private void initializeExp(GameState gameState, EnemyType enemyType) {
    // base exp for enemies.
    // index 0: GRUNT, index 1: ELITE, index 2: CHAMPION
    final int[] BASE_EXP = {10, 20, 30};

    double levelMultiplier = Math.pow(1.05, gameState.getGameLevel() - 1);
    switch (enemyType) {
      case GRUNT:
        this.expValue = BASE_EXP[0] * (int) levelMultiplier;
        break;
      case ELITE:
        this.expValue = BASE_EXP[1] * (int) levelMultiplier;
        break;
      case CHAMPION:
        this.expValue = BASE_EXP[2] * (int) levelMultiplier;
        break;
    }
  }

  /** Determines the enemy type based on the assigned color. */
  private EnemyType determineEnemyType(Color color) {
    if (color.equals(Color.BLUE)) {
      return EnemyType.GRUNT;
    } else if (color.equals(Color.GREEN)) {
      return EnemyType.ELITE;
    } else if (color.equals(Color.RED)) {
      return EnemyType.CHAMPION;
    }
    return EnemyType.GRUNT;
  }

  private EnemyType determineEnemyType() {
    Random random = new Random();
    int range = random.nextInt(100);
    if (range > 40) {
      return EnemyType.GRUNT;
    } else if (range > 10) {
      return EnemyType.ELITE;
    } else {
      return EnemyType.CHAMPION;
    }
  }

  private Color getColorByEnemyType(EnemyType enemyType) {
    if (enemyType.equals(EnemyType.GRUNT)) return Color.BLUE;
    else if (enemyType.equals(EnemyType.ELITE)) return Color.GREEN;
    else if (enemyType.equals(EnemyType.CHAMPION)) return Color.RED;
    return Color.BLUE;
  }

  /**
   * Constructor, establishes the ship's properties for a special ship, with known starting
   * properties.
   */
  public EnemyShip() {
    super(-32, 60, 16 * 2, 7 * 2, Color.RED);

    this.spriteType = SpriteType.EnemyShipSpecial;
    this.isDestroyed = false;
    this.pointValue = BONUS_TYPE_POINTS;
  }

  /**
   * Getter for the score bonus if this ship is destroyed.
   *
   * @return Value of the ship.
   */
  public final int getPointValue() {
    return this.pointValue;
  }

  /**
   * Moves the ship the specified distance.
   *
   * @param distanceX Distance to move in the X axis.
   * @param distanceY Distance to move in the Y axis.
   */
  public final void move(final int distanceX, final int distanceY) {
    this.positionX += distanceX;
    this.positionY += distanceY;
  }

  /** Updates attributes, mainly used for animation purposes. */
  public final void update() {
    if (this.animationCooldown.checkFinished()) {
      this.animationCooldown.reset();

      switch (this.spriteType) {
        case EnemyShipA1:
          this.spriteType = SpriteType.EnemyShipA2;
          break;
        case EnemyShipA2:
          this.spriteType = SpriteType.EnemyShipA1;
          break;
        case EnemyShipB1:
          this.spriteType = SpriteType.EnemyShipB2;
          break;
        case EnemyShipB2:
          this.spriteType = SpriteType.EnemyShipB1;
          break;
        case EnemyShipC1:
          this.spriteType = SpriteType.EnemyShipC2;
          break;
        case EnemyShipC2:
          this.spriteType = SpriteType.EnemyShipC1;
          break;
        case EnemyShipD1:
          this.spriteType = SpriteType.EnemyShipD2;
          break;
        case EnemyShipD2:
          this.spriteType = SpriteType.EnemyShipD1;
          break;
        case EnemyShipE1:
          this.spriteType = SpriteType.EnemyShipE2;
          break;
        case EnemyShipE2:
          this.spriteType = SpriteType.EnemyShipE1;
          break;
        default:
          break;
      }
    }
  }

  /**
   * Destroys the ship, causing an explosion.
   *
   * @param balance 1p -1.0, 2p 1.0, both 0.0
   */
  public final void destroy(final float balance) {
    this.isDestroyed = true;
    this.spriteType = SpriteType.Explosion;
    soundManager.playSound(Sound.ALIEN_HIT, balance);
  }

  /**
   * Applies damage to the enemy and determines if it is destroyed.
   *
   * @param damage Amount of damage dealt to the enemy.
   * @param balance Sound balance (-1.0 for player1, 1.0 for player2).
   */
  public final void applyDamageToEnemy(
      int damage,
      final float balance) { // Determine whether to destroy the enemy ship based on its health
    this.health -= damage;

    if (this.health <= 0) {
      this.isDestroyed = true;
      this.spriteType = SpriteType.Explosion;
    }
    soundManager.playSound(Sound.ALIEN_HIT, balance);
  }

  public double getHealth() {
    return this.health;
  } // Receive enemy ship health

  /**
   * Checks if the ship has been destroyed.
   *
   * @return True if the ship has been destroyed.
   */
  public final boolean isDestroyed() {
    return this.isDestroyed;
  }

  /**
   * get EXP of enemy ship
   *
   * @return expValue
   */
  public int getExpValue() {
    return this.expValue;
  }

  public int getAttackDamage() {
    return this.attackDamage;
  }
}
