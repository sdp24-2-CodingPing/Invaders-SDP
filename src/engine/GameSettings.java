package engine;

import engine.difficulty.DifficultyHandler;
import engine.difficulty.EasyDifficultyHandler;
import engine.difficulty.HardDifficultyHandler;
import engine.difficulty.NormalDifficultyHandler;

/**
 * Implements an object that stores a single game's difficulty settings.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameSettings {
	private int difficulty;
	/** Width of the level's enemy formation. */
	private int formationWidth;
	/** Height of the level's enemy formation. */
	private int formationHeight;
	/** Speed of the enemies, function of the remaining number. */
	private int baseSpeed;
	/** Frequency of enemy shootings, +/- 30%. */
	private int shootingFrequency;

	/**
	 * Constructor.
	 * 
	 * @param formationWidth
	 *            Width of the level's enemy formation.
	 * @param formationHeight
	 *            Height of the level's enemy formation.
	 * @param baseSpeed
	 *            Speed of the enemies.
	 * @param shootingFrequency
	 *            Frequency of enemy shootings, +/- 30%.
	 */
	public GameSettings(final int formationWidth, final int formationHeight,
			final int baseSpeed, final int shootingFrequency) {
		this.formationWidth = formationWidth;
		this.formationHeight = formationHeight;
		this.baseSpeed = baseSpeed;
		this.shootingFrequency = shootingFrequency;
	}

	public GameSettings(GameSettings gameSettings) {
		this.formationWidth = gameSettings.formationWidth;
		this.formationHeight = gameSettings.formationHeight;
		this.baseSpeed = gameSettings.baseSpeed;
		this.shootingFrequency = gameSettings.shootingFrequency;
	}

	/**
	 * @return the formationWidth
	 */
	public final int getFormationWidth() {
		return formationWidth;
	}

	/**
	 * @return the formationHeight
	 */
	public final int getFormationHeight() {
		return formationHeight;
	}

	/**
	 * @return the baseSpeed
	 */
	public final int getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * @return the shootingFrequency
	 */
	public final int getShootingFrequency() {
		return shootingFrequency;
	}

	/**
	 * @return difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 *
	 * @param formationWidth control Enemy width
	 * @param formationHeight control Enemy height
	 * @param baseSpeed control Enemy speed
	 * @param shootingFrequency control Enemy shooting Frequency
	 * @param gameLevel Level
	 * @param difficulty set difficulty
	 * @return return type GameSettings
	 */
	public GameSettings LevelSettings(int formationWidth, int formationHeight, int baseSpeed, int shootingFrequency, int gameLevel, int difficulty) {

		DifficultyHandler handler = switch(difficulty) {
			case 0 -> new EasyDifficultyHandler();
			case 1 -> new NormalDifficultyHandler();
			case 2 -> new HardDifficultyHandler();
			default -> null;
		};
		return handler != null ? handler.adjustSettings(formationWidth, formationHeight, baseSpeed, shootingFrequency, gameLevel) : null;
	}

}
