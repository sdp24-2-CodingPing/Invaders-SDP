package screen;

import engine.*;
import engine.Cooldown;
import engine.Core;
import engine.InputManager;
import engine.drawmanager.GameSettingDrawManager;
import java.awt.event.KeyEvent;

/**
 * Implements the game setting screen.
 *
 * @author <a href="mailto:dayeon.dev@gmail.com">Dayeon Oh</a>
 */
public class GameSettingScreen extends Screen {
  private static GameSettingScreen instance;

  /** Milliseconds between changes in user selection. */
  private static final int SELECTION_TIME = 200;

  /** Maximum number of characters for player name. draw를 용이하게 하기 위해 NAME_LIMIT을 4로 제한 */
  private static final int NAME_LIMIT = 4;

  /** Player name for record input. */
  private static String name;

  /** Multiplayer mode. */
  private static boolean isMultiplayer = false;

  /** Difficulty level. */
  private int difficultyLevel;

  /** Selected row. */
  private int selectedRow;

  /** Time between changes in user selection. */
  private final Cooldown selectionCooldown;

  /** Total number of rows for selection. */
  private static final int TOTAL_ROWS = 3; // Multiplayer, Difficulty, Start

  /** Singleton instance of SoundManager */
  private final SoundManager soundManager = SoundManager.getInstance();

  /**
   * Constructor, establishes the properties of the screen.
   *
   * @param width Screen width.
   * @param height Screen height.
   * @param fps Frames per second, frame rate at which the game is run.
   */
  public GameSettingScreen(final int width, final int height, final int fps) {
    super(width, height, fps);

    // row 0: player
    this.name = "AAAA";

    // row 1: difficulty level
    this.difficultyLevel = 1; // 0: easy, 1: normal, 2: hard

    // row 3: start
    this.selectedRow = 0;

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

  /** Updates the elements on screen and checks for events. */
  protected final void update() {
    super.update();

    draw();
    if (this.inputDelay.checkFinished() && this.selectionCooldown.checkFinished()) {
      if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
        this.selectedRow = (this.selectedRow - 1 + TOTAL_ROWS) % TOTAL_ROWS;
        this.selectionCooldown.reset();
        soundManager.playSound(Sound.MENU_MOVE);
      } else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
        this.selectedRow = (this.selectedRow + 1) % TOTAL_ROWS;
        this.selectionCooldown.reset();
        soundManager.playSound(Sound.MENU_MOVE);
      }

      if (this.selectedRow == 0) {
        if (inputManager.isKeyDown(KeyEvent.VK_BACK_SPACE)) {
          if (!this.name.isEmpty()) {
            this.name = this.name.substring(0, this.name.length() - 1);
            this.selectionCooldown.reset();
            soundManager.playSound(Sound.MENU_TYPING);
          }
        }
        handleNameInput(inputManager);
      } else if (this.selectedRow == 1) {
        if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
          if (this.difficultyLevel != 0) {
            this.difficultyLevel--;
            this.selectionCooldown.reset();
            soundManager.playSound(Sound.MENU_MOVE);
          }
        } else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
          if (this.difficultyLevel != 2) {
            this.difficultyLevel++;
            this.selectionCooldown.reset();
            soundManager.playSound(Sound.MENU_MOVE);
          }
        }
      } else if (this.selectedRow == 2) {
        if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
          this.returnCode = isMultiplayer ? 8 : 2;
          this.isRunning = false;
          soundManager.playSound(Sound.MENU_CLICK);
        }
      }
      if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
        // Return to main menu.
        this.returnCode = 1;
        this.isRunning = false;
        soundManager.playSound(Sound.MENU_BACK);
      }
    }
  }

  /**
   * Handles the input for player name.
   *
   * @param inputManager Input manager.
   */
  private void handleNameInput(InputManager inputManager) {
    for (int keyCode = KeyEvent.VK_A; keyCode <= KeyEvent.VK_Z; keyCode++) {
      if (inputManager.isKeyDown(keyCode)) {
        if (this.name.length() < NAME_LIMIT) {
          this.name += (char) keyCode;
          this.selectionCooldown.reset();
          soundManager.playSound(Sound.MENU_TYPING);
        }
      }
    }
  }

  public static GameSettingScreen getInstance() {
    if (instance == null) {
      instance = new GameSettingScreen(0, 0, 0);
    }
    return instance;
  }

  /**
   * Get player's name.
   *
   * @return Player's name
   */
  public static String getName() {
    return name;
  }

  /** Draws the elements associated with the screen. */
  private void draw() {
    drawManager.initDrawing(this);

    GameSettingDrawManager.drawGameSetting(this);

    GameSettingDrawManager.drawGameSettingRow(this, this.selectedRow);

    GameSettingDrawManager.drawGameSettingElements(
        this, this.selectedRow, name, this.difficultyLevel);

    drawManager.completeDrawing(this);

    Core.setLevelSetting(this.difficultyLevel);
  }
}
