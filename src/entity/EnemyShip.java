package entity;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import engine.GameState;
import engine.Sound;
import engine.SoundManager;

/**
 * Implements a enemy ship, to be destroyed by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
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
	/** Point value of a type F enemy*/
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

	public enum EnemyType {
		GRUNT,
		ELITE,
		CHAMPION
	}

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param spriteType
	 *            Sprite type, image corresponding to the ship.
	 */
	public EnemyShip(final int positionX, final int positionY,
			final SpriteType spriteType, final GameState gameState) {
		super(positionX, positionY, 12 * 2, 8 * 2, Color.BLUE);

		this.enemyType = determineEnemyType();
		super.setColor(getColorByEnemyType(this.enemyType));
		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;
		initializeHealth(gameState, this.enemyType);

		switch (this.spriteType) {
		case EnemyShipA1:
		case EnemyShipA2:
			this.pointValue = (int) (A_TYPE_POINTS+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			this.expValue = (int) (A_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		case EnemyShipB1:
		case EnemyShipB2:
			this.pointValue = (int) (B_TYPE_POINTS+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			this.expValue = (int) (B_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		case EnemyShipC1:
		case EnemyShipC2:
			this.pointValue = (int) (C_TYPE_POINTS+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			this.expValue = (int) (C_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		case EnemyShipD1:
		case EnemyShipD2:
			this.pointValue = D_TYPE_POINTS;
			this.expValue = (int) (D_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		case EnemyShipE1:
		case EnemyShipE2:
			this.pointValue = E_TYPE_POINTS;
			this.expValue = (int) (E_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		case EnemyShipF1:
			this.pointValue = F_TYPE_POINTS;
			this.expValue = (int) (F_TYPE_EXP+(gameState.getGameLevel()*0.1)+Core.getLevelSetting());
			break;
		default:
			this.pointValue = 0;
			break;
		}
	}

	/**
	 * Assigns base health for each enemy types, considering level scaling.
	 */
	private void initializeHealth(GameState gameState, EnemyType enemyType) {
		double baseHealth = 1;
		if (enemyType.equals(EnemyType.ELITE))	baseHealth = 3;
		else if (enemyType.equals(EnemyType.CHAMPION)) baseHealth = 7;

		double levelMultiplier = Math.pow(1.05, gameState.getGameLevel() - 1);
		this.health = baseHealth * levelMultiplier;
	}

	/**
	 * Determines the enemy type based on the assigned color.
	 */
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
		if (enemyType.equals(EnemyType.GRUNT))	return Color.BLUE;
		else if (enemyType.equals(EnemyType.ELITE))	return Color.GREEN;
		else if (enemyType.equals(EnemyType.CHAMPION))	return Color.RED;
		return Color.BLUE;
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with
	 * known starting properties.
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
	 * @param distanceX
	 *            Distance to move in the X axis.
	 * @param distanceY
	 *            Distance to move in the Y axis.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX;
		this.positionY += distanceY;
	}

	/**
	 * Updates attributes, mainly used for animation purposes.
	 */
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

    public final void HealthManageDestroy(final float balance) { //Determine whether to destroy the enemy ship based on its health
		health --;
		if(this.health <= 0){
            this.isDestroyed = true;
            this.spriteType = SpriteType.Explosion;
        }
        soundManager.playSound(Sound.ALIEN_HIT, balance);
    }

	public double getHealth(){ return this.health; }  //Receive enemy ship health


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
	 * */
	public int getExpValue() {
		return this.expValue;
	}
}
