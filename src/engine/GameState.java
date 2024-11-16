package engine;

import entity.PlayerShip;
import entity.ShipFactory;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {
	/** Current game level. */
	private int gameLevel;
	/** Current score. */
	private int score;
	/** Current score. */
	private int exp;
	/** Current ship level. */
	private int shipLevel;
	/** Current ship type. */
	private PlayerShip.ShipType shipType;
	/** Current player ship */
	private PlayerShip playerShip;
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
	/** Elapsed time */
	private int elapsedTime;
	/** Number of consecutive hits */
	private int combo;
	/** Intermediate aggregation variables
	 * max combo, elapsed time and total score, total exp
	 * you get from previous level */
	private int maxCombo;
	private int prevTime;
	private int prevScore;
	private int prevShipLevel;

	private int hitBullets;

	private int isGotoMainMenu;

	/**
	 * Constructor.
	 *
	 * @param gameLevel      Current game level.
	 * @param score          Current score.
	 * @param shipType       Current ship type.
	 * @param bulletsShot    Bullets shot until now.
	 * @param shipsDestroyed Ships destroyed until now.
	 * @param elapsedTime    Elapsed time.
	 * @param combo          Ships destroyed consequtive.
	 */
	public GameState(final int gameLevel, final int shipLevel, final int score, final int exp,
					 final PlayerShip.ShipType shipType, final int bulletsShot,
					 final int shipsDestroyed, final int elapsedTime, final int combo,
					 final int maxCombo, final int prevTime, final int prevScore, final int hitBullets) {
		this.gameLevel = gameLevel;
		this.shipLevel = shipLevel;
		this.score = score;
		this.exp = exp;
		this.shipType = shipType;
		this.playerShip = ShipFactory.create(shipType, Core.getWidth() / 2, Core.getHeight() - 130);
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.elapsedTime = elapsedTime;
		this.combo = combo;
		this.maxCombo = maxCombo;
		this.prevTime = prevTime;
		this.prevScore = prevScore;
		this.hitBullets = hitBullets;
	}

	/**
	 * Constructor only used in two-player-mode
	 * @param gameState GameState
	 * */
	public GameState(GameState gameState) {
		this.gameLevel = gameState.gameLevel;
		this.shipLevel = gameState.shipLevel;
		this.score = gameState.score;
		this.shipType = gameState.shipType;
		this.playerShip = gameState.playerShip;
		this.bulletsShot = gameState.bulletsShot;
		this.shipsDestroyed = gameState.shipsDestroyed;
		this.elapsedTime = gameState.elapsedTime;
		this.combo = 0;
		this.maxCombo = gameState.maxCombo;
		this.prevTime = gameState.prevTime;
		this.prevScore = gameState.prevScore;
		this.hitBullets = gameState.hitBullets;
	}

	/**
	 * constructor that used to get previous stats of game, and get next level of game.
	 * */
	public GameState(GameState gameState, int nextLevel) {
		this.gameLevel = nextLevel;
		this.shipLevel = gameState.getShipLevel();
		this.score = gameState.score;
		this.shipType = gameState.shipType;
		this.playerShip = gameState.playerShip;
		this.bulletsShot = gameState.bulletsShot;
		this.shipsDestroyed = gameState.shipsDestroyed;
		this.elapsedTime = gameState.elapsedTime;
		this.combo = 0;
		this.maxCombo = gameState.maxCombo;
		this.prevTime = gameState.prevTime;
		this.prevScore = gameState.prevScore;
		this.hitBullets = gameState.hitBullets;
	}


	/**
	 * Get game level
	 * @return the game level
	 */
	public final int getGameLevel() {
		return gameLevel;
	}
	/**
	 * Set game level
	 * @param gameLevel
	 */
	public final void setGameLevel(int gameLevel) {
		this.gameLevel = gameLevel;
	}

	/**
	 * Get ship level
	 * @return the ship level
	 */
	public final int getShipLevel() {
		return shipLevel;
	}
	/**
	 * Set ship level
	 * @param shipLevel
	 */
	public final void setShipLevel(int shipLevel) {
		this.shipLevel = shipLevel;
	}

	/**
	 * Get score
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}
	/**
	 * Set score
	 * @param score
	 */
	public final void setScore(int score) {
		this.score = score;
	}

	/**
	 * Get exp
	 * @return the exp
	 */
	public final int getExp() {
		return exp;
	}
	/**
	 * Set exp
	 * @param exp
	 */
	public final void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * Get ship type
	 * @return the shipType
	 */
	public final PlayerShip.ShipType getShipType() {
		return shipType;
	}
	/**
	 * Set ship type
	 * @param shipType
	 */
	public final void setShipType (PlayerShip.ShipType shipType) {
		this.shipType = shipType;
	}

	/**
	 * @return the player ship
	 */
	public final PlayerShip getPlayerShip() {
		return playerShip;
	}

	/**
	 * Get bulletsShot
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}
	/**
	 * Set bulletsShot
	 * @param bulletsShot
	 */
	public final void setBulletsShot(int bulletsShot) {
		this.bulletsShot = bulletsShot;
	}

	/**
	 * Get ship destroyed
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}
	/**
	 * Set ship destroyed
	 * @param shipsDestroyed
	 */
	public final void setShipsDestroyed(int shipsDestroyed) {
		this.shipsDestroyed = shipsDestroyed;
	}


	/**
	 * Get elapsed time
	 * @return the elapsedTime
	 */
	public final int getElapsedTime() { return elapsedTime; }
	/**
	 * Set elapsed time
	 * @param elapsedTime
	 */
	public final void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getAccuracy() {
		if (bulletsShot == 0){
			return 0;
		}
		return ((double) hitBullets / bulletsShot) * 100;
	}

	/**
	 * Get combo
	 * @return the number of consecutive hits.
	 */
	public final int getCombo() {
		return combo;
	}
	/**
	 * Set combo
	 * @param combo
	 */
	public final void setCombo(int combo) {
		this.combo = combo;
	}

	/**
	 * Get max combo
	 * @return the maxCombo
	 */
	public final int getMaxCombo() { return maxCombo;}
	/**
	 * Set max combo
	 * @param maxCombo
	 */
	public final void setMaxCombo(int maxCombo) {
		this.maxCombo = maxCombo;
	}

	/**
	 * Get prev time
	 * @return the prevTime/lapTime
	 */
	public final int getPrevTime() { return prevTime;}
	/**
	 * Set prev time
	 * @param prevTime
	 */
	public final void setPrevTime(int prevTime) {
		this.prevTime = prevTime;
	}

	/**
	 * Get prev score
	 * @return the prevScore/tempScore
	 */
	public final int getPrevScore() { return prevScore;}
	/**
	 * Set prev score
	 * @param prevScore
	 */
	public final void setPrevScore(int prevScore) {
		this.prevScore = prevScore;
	}

	/**
	 * Get hit bullets
	 * @return bullets hit count
	 */
	public final int getHitBullets() { return hitBullets;}
	/**
	 * Set hit bullets
	 * @param hitBullets
	 */
	public final void setHitBullets(int hitBullets) {
		this.hitBullets = hitBullets;
	}

}

