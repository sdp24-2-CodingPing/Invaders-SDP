package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	private int speed;
	/**
	 * Attack damage for the bullet, especially for enemies.
	 * it varies depend on enemy type and game level.
	 * */
	private int attackDamage;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed, int attackDamage) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);

		this.speed = speed;
		this.attackDamage = attackDamage;
		setSprite();
	}

	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {
		if (speed < 0)
			this.spriteType = SpriteType.Bullet;
		else
			this.spriteType = SpriteType.EnemyBullet;
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		this.positionY += this.speed;
	}

	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *            New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return this.speed;
	}

	/**
	 * Setter of the attack damage for the bullet.
	 *
	 * @param attackDamage
	 *            New attack damage for the bullet.
	 */
	public final void setAttackDamage(final int attackDamage) {
		this.attackDamage = attackDamage;
	}

	/**
	 * Getter for the attack damage for the bullet.
	 *
	 * @return attack damage for the bullet.
	 */
	public final int getAttackDamage(){
		return this.attackDamage;
	}
}
