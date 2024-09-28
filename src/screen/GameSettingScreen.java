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

	/** Player name for record input. */
	private char[] name;
	/** Character of players name selected for change. */
	private int nameCharSelected;
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

		this.name = "AAA".toCharArray();
		this.nameCharSelected = 0;
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

			if (this.selectionCooldown.checkFinished()) {
				if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
					this.nameCharSelected = this.nameCharSelected == 2 ? 0
							: this.nameCharSelected + 1;
					this.selectionCooldown.reset();
				}
				if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
					this.nameCharSelected = this.nameCharSelected == 0 ? 2
							: this.nameCharSelected - 1;
					this.selectionCooldown.reset();
				}
				if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
					this.name[this.nameCharSelected] =
							(char) (this.name[this.nameCharSelected]
									== LAST_CHAR ? FIRST_CHAR
							: this.name[this.nameCharSelected] + 1);
					this.selectionCooldown.reset();
				}
				if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
					this.name[this.nameCharSelected] =
							(char) (this.name[this.nameCharSelected]
									== FIRST_CHAR ? LAST_CHAR
							: this.name[this.nameCharSelected] - 1);
					this.selectionCooldown.reset();
				}
			}
		}

	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawGameSetting(this);

		drawManager.drawNameInput(this, this.name, this.nameCharSelected);

		drawManager.completeDrawing(this);
	}
}
