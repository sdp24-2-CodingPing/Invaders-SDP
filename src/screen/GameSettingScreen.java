package screen;

import engine.Cooldown;
import engine.Core;

import java.awt.event.KeyEvent;

/**
 * Implements the game setting screen.
 * 
 * @author <a href="mailto:dayeon.dev@gmail.com">Dayeon Oh</a>
 * 
 */
public class GameSettingScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;
	/** Code of first mayus character. */
	private static final int FIRST_CHAR = 65;
	/** Code of last mayus character. */
	private static final int LAST_CHAR = 90;

	/** Player name1 for record input. */
	private String name1;
	/** Player name2 for record input. */
	private String name2;
	/** Difficulty level. */
	private int difficultyLevel;
	/** Multiplayer mode. */
	private boolean isMultiplayer;
	/** Selected column. */
	private int selectedColumn;
	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public GameSettingScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		this.name1 = "P1";
		this.name2 = "P2";
		this.difficultyLevel = 1;
		this.isMultiplayer = false;
		this.selectedColumn = 0;

		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
	}

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		draw();
		if (this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
				// Return to main menu.
				this.returnCode = 1;
				this.isRunning = false;
			} else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
				// Play again.
				this.returnCode = 2;
				this.isRunning = false;
			}
		}

	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawGameSetting(this);

		drawManager.drawGameSettingColumn(this, this.selectedColumn);

		drawManager.drawGameSettingElements(this, 0, this.isMultiplayer, this.name1, this.name2,this.difficultyLevel);

		drawManager.completeDrawing(this);
	}
}
