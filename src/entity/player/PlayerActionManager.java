package entity.player;

import engine.Core;
import engine.GameState;
import engine.InputManager;
import engine.ItemManager;
import entity.Bullet;
import entity.Web;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * manages action of player ship
 * @author raewoo
 * */
public class PlayerActionManager {
    /** instance of player ship*/
    private final PlayerShip playerShip;
    /** instance of InputManager*/
    private final InputManager inputManager;
    /** instance of GameState*/
    private final GameState gameState;
    /** instance of ItemManager*/
    private final ItemManager itemManager;
    /** Application logger. */
    private final Logger logger;

    /**
     * constructor: constructed when GameScreen.initialize()
     * @param playerShip player ship
     * @param inputManager InputManager
     * @param gameState GameState
     * @param itemManager ItemManager
     * */
    public PlayerActionManager(PlayerShip playerShip, InputManager inputManager, GameState gameState, ItemManager itemManager) {
        this.playerShip = playerShip;
        this.inputManager = inputManager;
        this.gameState = gameState;
        this.itemManager = itemManager;
        this.logger = Core.getLogger();
    }

    /**
     * manage movement of player ship
     * @param playerNumber number of player ship. 0: 1-player, 1: 2-player
     * @param screenWidth width of current screen
     * @param balance balance for SoundManager
     * @param webs List of spider web
     * */
    public void manageMovement(int playerNumber, int screenWidth, float balance, List<Web> webs){
        boolean moveRight;
        boolean moveLeft;
        switch (playerNumber) {
            case 0:
                moveRight = inputManager.isKeyDown(KeyEvent.VK_D);
                moveLeft = inputManager.isKeyDown(KeyEvent.VK_A);
                break;
            case 1:
                moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT);
                moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT);
                break;
            default:
                moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                        || inputManager.isKeyDown(KeyEvent.VK_D);
                moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT)
                        || inputManager.isKeyDown(KeyEvent.VK_A);
        }

        boolean isRightBorder = this.playerShip.getPositionX()
                + this.playerShip.getWidth() + this.playerShip.getSpeed() > screenWidth - 1;
        boolean isLeftBorder = this.playerShip.getPositionX()
                - this.playerShip.getSpeed() < 1;

        if (moveRight && !isRightBorder) {
            this.playerShip.moveRight(balance);
        }
        if (moveLeft && !isLeftBorder) {
            this.playerShip.moveLeft(balance);
        }
        for(int i = 0; i < webs.size(); i++) {
            //escape Spider Web
            if (playerShip.getPositionX() + 6 <= webs.get(i).getPositionX() - 6
                    || webs.get(i).getPositionX() + 6 <= playerShip.getPositionX() - 6) {
                this.playerShip.setThreadWeb(false);
            }
            //get caught in a spider's web
            else {
                this.playerShip.setThreadWeb(true);
                break;
            }
        }
    }

    /**
     * manage bullet shoot of player ship
     * @param bullets set of bullets of player ship
     * */
    public void manageShooting(Set<Bullet> bullets) {
        boolean player1Attacking = inputManager.isKeyDown(KeyEvent.VK_SPACE);
        boolean player2Attacking = inputManager.isKeyDown(KeyEvent.VK_SHIFT);

        // Both players are attacking
        if (this.playerShip.shoot(bullets))
            this.gameState.setBulletsShot(this.gameState.getBulletsShot() + this.playerShip.getBulletCount());
    }
}
