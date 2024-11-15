package screen;

import engine.Cooldown;
import engine.Core;
import engine.Sound;
import engine.SoundManager;
import entity.Card;
import entity.Wallet;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


/**
 * Implements the title screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class CardSelectScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

	/** Singleton instance of SoundManager */
	private final SoundManager soundManager = SoundManager.getInstance();

	private ArrayList<Card> cardList;

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
	public CardSelectScreen(final int width, final int height, final int fps, ArrayList<Card> cardList) {
		super(width, height, fps);

		// Defaults to play.
		if (!soundManager.isSoundPlaying(Sound.BGM_MAIN))
			soundManager.loopSound(Sound.BGM_MAIN);

		this.returnCode = 0;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
		this.cardList = cardList;
	}

	/**
	 * Starts the action.
	 *
	 * @return Selected Card Index
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
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
				previousMenuItem();
				this.selectionCooldown.reset();
				soundManager.playSound(Sound.MENU_MOVE);
			}
			if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
				nextMenuItem();
				this.selectionCooldown.reset();
				soundManager.playSound(Sound.MENU_MOVE);
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)){
				this.isRunning = false;
				soundManager.playSound(Sound.MENU_CLICK);
			}
		}
	}

	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
	/*
	  TODO: Refactor returnCode & Core Logic
	 */
		this.returnCode = (this.returnCode + 1) % cardList.size();
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
	/*
	  TODO: Refactor returnCode & Core Logic
	 */
		if (this.returnCode == 0)
			this.returnCode = cardList.size() - 1;
		else
			this.returnCode--;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawCardSelectTitle(this);
		drawManager.drawCardList(this, this.cardList, this.returnCode);
//		drawManager.drawMenu(this, this.returnCode);

		drawManager.completeDrawing(this);
	}
}