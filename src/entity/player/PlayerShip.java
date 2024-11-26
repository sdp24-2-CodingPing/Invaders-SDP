package entity.player;

import java.awt.Color;
import java.util.Set;
import java.util.logging.Logger;

import engine.*;
import engine.DrawManager.SpriteType;
import entity.*;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public abstract class PlayerShip extends Entity {

	/** Application logger. */
	protected Logger logger;

	/** Time between shots. */
	private static int SHOOTING_INTERVAL = 750;
	/** Speed of the bullets shot by the ship. */
	private static int BULLET_SPEED = -6;
	/** Player bullets default damage */
	private static int DAMAGE = 10;
	/** Movement of the ship for each unit of time. */
	private static int SPEED = 2;
	/** Player bullets default count */
	private static int BULLET_COUNT = 1;

    /** Play the sound every 0.5 second */
	private static final int SOUND_COOLDOWN_INTERVAL = 500;
    /** Cooldown for playing sound */
	private Cooldown soundCooldown;

	/** Multipliers for the ship's properties. */
	protected final ShipMultipliers multipliers;
	/** Name of the ship. */
	public final String name;
	/** Type of sprite to be drawn. */
	private final SpriteType baseSprite;

	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;
	/** Singleton instance of SoundManager */
	private final SoundManager soundManager = SoundManager.getInstance();

	/** Player Max HP */
	private int playerMaxHP;
	/** Player HP */
	private int playerHP;
	/** Player level and exp */
	private PlayerLevel playerLevel;
	/** Player Card Status */
	private PlayerCardStatus playerCardStatus;

	private long lastShootTime;
	private boolean threadWeb = false;

	public void setThreadWeb(boolean threadWeb) {
		this.threadWeb = threadWeb;
	}

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param name
	 * 		  	  Name of the ship.
	 * @param multipliers
	 * 		      Multipliers for the ship's properties.
	 * 		      @see ShipMultipliers
	 * @param spriteType
	 * 		      Type of sprite to be drawn.
	 * 		      @see SpriteType
	 */
	protected PlayerShip(final int positionX, final int positionY,
						 final String name, final ShipMultipliers multipliers,
						 final SpriteType spriteType) {
		super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);

		this.logger = Core.getLogger();
		this.playerMaxHP = 3;
		this.playerHP = 3;
		this.playerLevel = new PlayerLevel(90, 1);
		this.playerCardStatus = new PlayerCardStatus();

		this.name = name;
		this.multipliers = multipliers;
		this.baseSprite = spriteType;
		this.spriteType = spriteType;
		this.shootingCooldown = Core.getCooldown(this.getShootingInterval());
		this.destructionCooldown = Core.getCooldown(1000);
		this.lastShootTime = 0;
		this.soundCooldown = Core.getCooldown(SOUND_COOLDOWN_INTERVAL);
	}

	/**
	 * Types of ships available.
	 */
	public enum ShipType {
		StarDefender,
		VoidReaper,
		GalacticGuardian,
		CosmicCruiser,
	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		moveRight(0.0f);
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		moveLeft(0.0f);
	}

	public final void moveRight(float balance) {
		if(threadWeb){
			this.positionX += this.getSpeed() / 2;
		} else {
			this.positionX += this.getSpeed();
		}
		if (soundCooldown.checkFinished()) {
			soundManager.playSound(Sound.PLAYER_MOVE, balance);
			soundCooldown.reset();
		}
	}

	public final void moveLeft(float balance) {
		if(threadWeb){
			this.positionX -= this.getSpeed() / 2;
		} else {
			this.positionX -= this.getSpeed();
		}
		if (soundCooldown.checkFinished()) {
			soundManager.playSound(Sound.PLAYER_MOVE, balance);
			soundCooldown.reset();
		}
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets) {
		return shoot(bullets, 0.0f);
	}

	/**
	 * bullet sound (2-players)
	 * @param bullets
	 *          List of bullets on screen, to add the new bullet.
	 * @param balance
	 * 			1p -1.0, 2p 1.0, both 0.0
	 *
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets, float balance) {
		if (this.shootingCooldown.checkFinished()) {

			this.shootingCooldown.reset();
			this.lastShootTime = System.currentTimeMillis();

			switch (this.getBulletCount()) {
				case 1:
					bullets.add(BulletPool.getBullet(positionX + this.width / 2, positionY, this.getBulletSpeed()));
					soundManager.playSound(Sound.PLAYER_LASER, balance);
					break;
				case 2:
					bullets.add(BulletPool.getBullet(positionX + this.width, positionY, this.getBulletSpeed()));
					bullets.add(BulletPool.getBullet(positionX, positionY, this.getBulletSpeed()));
					soundManager.playSound(Sound.ITEM_2SHOT, balance);
					break;
				default:
					bullets.add(BulletPool.getBullet(positionX + this.width, positionY, this.getBulletSpeed()));
					bullets.add(BulletPool.getBullet(positionX, positionY, this.getBulletSpeed()));
					for (int i = 1; i <= this.getBulletCount() - 2; i++) bullets.add(BulletPool.getBullet(positionX + this.width * i / this.getBulletCount() - 1, positionY, this.getBulletSpeed()));
					soundManager.playSound(Sound.ITEM_3SHOT, balance);
					break;
			}

			return true;
		}

		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = this.baseSprite;
	}

	/**
	 * Player HP decrease by received damage
	 * @param damage
	 * 			received damage value
	 * @param balance
	 * 			sound balance (for two player mode)
	 */
	public final void receiveDamage(int damage, float balance) {
		this.destructionCooldown.reset();
		this.playerHP = Math.max(this.playerHP - damage, 0);
		soundManager.playSound(Sound.PLAYER_HIT, balance);
		this.logger.info("Hit on player ship, " + getPlayerHP() + " HP remaining.");
	}

	/**
	 * Player HP increase by received heal
	 * @param heal
	 * 			received heal value
	 */
	public final void receiveHeal(int heal) {
		this.playerHP = Math.min(this.playerHP + heal, getPlayerMaxHP());
		this.logger.info("Heal on player ship, " + getPlayerHP() + " HP remaining.");
	}

	/**
	 * Checks if the ship can receive damage.
	 *
	 * @return True if the ship is currently receive damage
	 */
	public final boolean isReceiveDamagePossible() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Checks if the player ship can level up
	 *
	 * @return True if the player ship is level up
	 */
	public final boolean isPlayerLevelUpPossible() {
		return this.playerLevel.isLevelUpPossible();
	}

	public final void increasePlayerExp(int exp) {
		this.playerLevel.setExp(this.playerLevel.getExp() + exp);
		logger.info("you get the " + exp + "exp, your current exp: " + this.playerLevel.getExp());
	}

	/**
	 * Player levelup logic
	 */
	public final void managePlayerLevelUp () {
		while (playerLevel.isLevelUpPossible()) {
			playerLevel.levelUp();
			playerLevel.selectLevelUpCard().applyCardStatus(this);
		}
	}

	/**
	 * Get player level
	 *
	 * @return player level
	 */
	public final PlayerLevel getPlayerLevel() {
		return this.playerLevel;
	}

	/**
	 * Get player card status
	 *
	 * @return player card status
	 */
	public final PlayerCardStatus getPlayerCardStatus() {
		return this.playerCardStatus;
	}

	/**
	 * Get Player HP
	 *
	 * @return player HP
	 */
	public final int getPlayerHP() {
		return this.playerHP;
	}

	/**
	 * Get Player Max HP
	 *
	 * @return player Max HP
	 */
	public final int getPlayerMaxHP() {
		return (this.playerMaxHP + playerCardStatus.getHpLevel() * 2);
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return Math.round((SPEED + playerCardStatus.getMoveSpeedLevel()) * this.multipliers.speed());
	}

	/**
	 * Getter for the ship's bullet speed.
	 *
	 * @return Speed of the bullets.
	 */
	public final int getBulletSpeed() {
		return Math.round((BULLET_SPEED - playerCardStatus.getBulletsSpeedLevel()) * this.multipliers.bulletSpeed());
	}

	/**
	 * Getter for the ship's shooting interval.
	 *
	 * @return Time between shots.
	 */
	public final int getShootingInterval() {
		return (int)Math.round(SHOOTING_INTERVAL * (Math.pow(0.9 ,(double)playerCardStatus.getIntervalLevel())) * this.multipliers.shootingInterval());
	}

	/**
	 * Calculates and returns the player's attack power.
	 * The attack power can depend on player's level, upgrades, or items.
	 *
	 * @return The calculated attack power.
	 */
	public final int getPlayerDamage() {
		return (Math.round((DAMAGE + playerCardStatus.getDamageLevel() * 5) * this.multipliers.damage()));
	}

	/**
	 * Getter for the ship's bullet count.
	 *
	 * @return Number of bullets shot by the ship.
	 */
	public final int getBulletCount() {
		return BULLET_COUNT + playerCardStatus.getBulletsCountLevel();
	}

	/**
	 * Getter for the ship's remaining reload time.
	 *
	 * @return Remaining time for the next shot.
	 */
	public long getRemainingReloadTime(){
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - this.lastShootTime;
		long remainingTime = this.getShootingInterval() - elapsedTime;
		return remainingTime > 0 ? remainingTime : 0;
	}

	/**
	 * Checks if the ship is destroyed.
	 *
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return getPlayerHP() <= 0;
	}

	public void applyShopItem(Wallet wallet){
		int bulletLv = wallet.getBullet_lv();
		switch (bulletLv){
			case 1:
				BULLET_SPEED = -6;
				break;
			case 2:
				BULLET_SPEED = -7;
				break;
			case 3:
				BULLET_SPEED = -9;
				break;
			case 4:
				BULLET_SPEED = -10;
				break;
			default:
				BULLET_SPEED = -6;
		}

		int intervalLv = wallet.getShot_lv();
		switch (intervalLv){
			case 1:
				SHOOTING_INTERVAL = 750;
				shootingCooldown = Core.getCooldown(this.getShootingInterval());
				break;
			case 2:
				SHOOTING_INTERVAL = 675;
				shootingCooldown = Core.getCooldown(this.getShootingInterval());
				break;
			case 3:
				SHOOTING_INTERVAL = 607;
				shootingCooldown = Core.getCooldown(this.getShootingInterval());
				break;
			case 4:
				SHOOTING_INTERVAL = 546;
				shootingCooldown = Core.getCooldown(this.getShootingInterval());
				break;
			default:
				SHOOTING_INTERVAL = 750;
				shootingCooldown = Core.getCooldown(this.getShootingInterval());
		}
	}
}
