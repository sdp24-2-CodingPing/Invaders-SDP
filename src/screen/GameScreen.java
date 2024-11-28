package screen;

import engine.*;
import engine.drawmanager.GameDrawManager;
import entity.*;
import entity.player.PlayerActionManager;
import entity.player.PlayerShip;
import entity.skill.LaserStrike;
import entity.skill.Skill;
import entity.player.PlayerLevel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public class GameScreen extends Screen implements Callable<GameState> {

  /** Milliseconds until the screen accepts user input. */
  private static final int INPUT_DELAY = 6000;

  /** Bonus score for each life remaining at the end of the level. */
  private static final int LIFE_SCORE = 100;

  /** Minimum time between bonus ship's appearances. */
  private static final int BONUS_SHIP_INTERVAL = 20000;

  /** Maximum variance in the time between bonus ship's appearances. */
  private static final int BONUS_SHIP_VARIANCE = 10000;

  /** Time until bonus ship explosion disappears. */
  private static final int BONUS_SHIP_EXPLOSION = 500;

  /** Time from finishing the level to screen change. */
  private static final int SCREEN_CHANGE_INTERVAL = 1500;

  /** Height of the interface separation line. */
  private static final int SEPARATION_LINE_HEIGHT = 40;

  /** Current game difficulty settings. */
  private GameSettings gameSettings;

  /** Current game state */
  private GameState gameState;

  /** Formation of enemy ships. */
  private EnemyShipFormation enemyShipFormation;

  /** Player's ship. */
  private PlayerShip playerShip;

  /** Bonus enemy ship that appears sometimes. */
  private EnemyShip enemyShipSpecial;

  /** Minimum time between bonus ship appearances. */
  private Cooldown enemyShipSpecialCooldown;

  /** Time until bonus ship explosion disappears. */
  private Cooldown enemyShipSpecialExplosionCooldown;

  /** Time from finishing the level to screen change. */
  private Cooldown screenFinishedCooldown;

  private Cooldown shootingCooldown;

  /** Set of all bullets fired by on screen ships. */
  private Set<Bullet> bullets;

  /** Moment the game starts. */
  private long gameStartTime;

  /** Checks if the level is finished. */
  private boolean levelFinished;

  /** Player number for two player mode * */
  private int playerNumber;

  /** list of highScores for find recode. */
  private List<Score> highScores;

  /** Keep previous timestamp. */
  private Integer prevTime;

  /** Alert Message when a special enemy appears. */
  private String alertMessage;

  /** checks if it's executed. */
  private boolean isExecuted = false;

  /** timer.. */
  private Timer timer;

  private TimerTask timerTask;

  /** Spider webs restricting player movement */
  private List<Web> web;

  /** Obstacles preventing a player's bullet */
  private List<Block> block;

  private Wallet wallet;
  /* Blocker appearance cooldown */
  private Cooldown blockerCooldown;
  /* Blocker visible time */
  private Cooldown blockerVisibleCooldown;
  /* Is Blocker visible */
  private boolean blockerVisible;
  private Random random;
  private List<Blocker> blockers = new ArrayList<>();

  /** Singleton instance of SoundManager */
  private final SoundManager soundManager = SoundManager.getInstance();

  /** instance of playerActionManager */
  private PlayerActionManager playerActionManager;

  /** Singleton instance of ItemManager. */
  private ItemManager itemManager;

  /** Item boxes that dropped when kill enemy ships. */
  private Set<ItemBox> itemBoxes;

  /** Barriers appear in game screen. */
  private Set<Barrier> barriers;

  /** Sound balance for each player */
  private float balance = 0.0f;

  private int MAX_BLOCKERS = 0;

  private boolean isGotoMainMenu;

  /**
   * Constructor, establishes the properties of the screen.
   *
   * @param gameState Current game state.
   * @param gameSettings Current game settings.
   * @param width Screen width.
   * @param height Screen height.
   * @param fps Frames per second, frame rate at which the game is run.
   */
  public GameScreen(
      final GameState gameState,
      final GameSettings gameSettings,
      final int width,
      final int height,
      final int fps,
      final Wallet wallet) {
    super(width, height, fps);

    this.gameSettings = gameSettings;
    this.gameState = gameState;
    this.playerNumber = -1;

    try {
      this.highScores = Core.getFileManager().loadHighScores();

    } catch (IOException e) {
      logger.warning("Couldn't load high scores!");
    }

    this.wallet = wallet;

    this.random = new Random();
    this.blockerVisible = false;
    this.blockerCooldown = Core.getVariableCooldown(10000, 14000);
    this.blockerCooldown.reset();
    this.blockerVisibleCooldown = Core.getCooldown(20000);

    try {
      this.highScores = Core.getFileManager().loadHighScores();
    } catch (IOException e) {
      logger.warning("Couldn't load high scores!");
    }
    this.alertMessage = "";

    this.wallet = wallet;
  }

  /**
   * Constructor, establishes the properties of the screen for two player mode.
   *
   * @param gameState Current game state.
   * @param gameSettings Current game settings.
   * @param width Screen width.
   * @param height Screen height.
   * @param fps Frames per second, frame rate at which the game is run.
   * @param playerNumber Player number for two player mode
   */
  public GameScreen(
      final GameState gameState,
      final GameSettings gameSettings,
      final int width,
      final int height,
      final int fps,
      final Wallet wallet,
      final int playerNumber) {
    this(gameState, gameSettings, width, height, fps, wallet);
    this.playerNumber = playerNumber;
    this.balance =
        switch (playerNumber) {
          case 0:
            yield -1.0f; // 1P
          case 1:
            yield 1.0f; // 2P
          default:
            yield 0.0f; // default
        };
  }

  /** Initializes basic screen properties, and adds necessary elements. */
  public final void initialize() {
    super.initialize();

    // Create enemy ship formation and attach formation to this screen.
    enemyShipFormation = new EnemyShipFormation(this.gameSettings, this.gameState);
    enemyShipFormation.attach(this);

    // Get your ship from GameState
    this.playerShip = gameState.getPlayerShip();

    // Apply items to the ship.
    // TODO: 한번만 적용되도록 위치 변경해야함.
    playerShip.applyShopItem(wallet);

    // Create random Spider Web.
    int web_count = 1 + gameState.getGameLevel() / 3;
    web = new ArrayList<>();
    for (int i = 0; i < web_count; i++) {
      double randomValue = Math.random();
      this.web.add(new Web((int) Math.max(0, randomValue * width - 12 * 2), this.height - 105));
      this.logger.info("Spider web creation location : " + web.get(i).getPositionX());
    }

    // Create random Block.
    int blockCount = gameState.getGameLevel() / 2;
    int playerTopY_contain_barrier = this.height - 40 - 150;
    int enemyBottomY = 100 + (gameSettings.getFormationHeight() - 1) * 48;
    this.block = new ArrayList<Block>();
    for (int i = 0; i < blockCount; i++) {
      Block newBlock;
      boolean overlapping;
      do {
        newBlock = new Block(0, 0);
        int positionX = (int) (Math.random() * (this.width - newBlock.getWidth()));
        int positionY =
            (int)
                    (Math.random()
                        * (playerTopY_contain_barrier - enemyBottomY - newBlock.getHeight()))
                + enemyBottomY;
        newBlock = new Block(positionX, positionY);
        overlapping = false;
        for (Block block : block) {
          if (checkCollision(newBlock, block)) {
            overlapping = true;
            break;
          }
        }
      } while (overlapping);
      block.add(newBlock);
    }

    // Appears each 10-30 seconds.
    this.enemyShipSpecialCooldown =
        Core.getVariableCooldown(BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
    this.enemyShipSpecialCooldown.reset();
    this.enemyShipSpecialExplosionCooldown = Core.getCooldown(BONUS_SHIP_EXPLOSION);
    this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
    this.bullets = new HashSet<>();
    this.barriers = new HashSet<>();
    this.itemBoxes = new HashSet<>();
    this.itemManager =
        new ItemManager(this.playerShip, this.enemyShipFormation, this.barriers, balance);
    // create Player Action Manager by new
    this.playerActionManager =
        new PlayerActionManager(
            this.playerShip, this.inputManager, this.gameState, this.itemManager);

    // Special input delay / countdown.
    this.gameStartTime = System.currentTimeMillis();
    this.inputDelay = Core.getCooldown(INPUT_DELAY);
    this.inputDelay.reset();
    if (soundManager.isSoundPlaying(Sound.BGM_MAIN)) soundManager.stopSound(Sound.BGM_MAIN);
    soundManager.playSound(Sound.COUNTDOWN);

    switch (this.gameState.getGameLevel()) {
      case 1:
        soundManager.loopSound(Sound.BGM_LV1);
        break;
      case 2:
        soundManager.loopSound(Sound.BGM_LV2);
        break;
      case 3:
        soundManager.loopSound(Sound.BGM_LV3);
        break;
      case 4:
        soundManager.loopSound(Sound.BGM_LV4);
        break;
      case 5:
        soundManager.loopSound(Sound.BGM_LV5);
        break;
      case 6:
        soundManager.loopSound(Sound.BGM_LV6);
        break;
      case 7:
      // From level 7 and above, it continues to play at BGM_LV7.
      default:
        soundManager.loopSound(Sound.BGM_LV7);
        break;
    }
  }

  /**
   * Starts the action.
   *
   * @return Next screen code.
   */
  public final int run() {
    super.run();

    this.logger.info("Screen cleared with a score of " + this.gameState.getScore());

    return this.returnCode;
  }

  /** Updates the elements on screen and checks for events. */
  private boolean isPaused = false;

  private Long pauseStartTime = null;
  private boolean escKeyPressed = false;
  private boolean isGameOver = false; // 게임 오버 상태를 나타내는 플래그

  protected final void update() {
    super.update();

    // ESC 키 입력 처리 (토글 상태)
    manageGameStop();

    if (this.inputDelay.checkFinished() && !this.levelFinished) {
      managePlayerShooting(this.bullets);
      /*Elapsed Time Update*/
      manageElapsedTime();

      // check if the ship is ghost mode, if not, set color green
      if (!itemManager.isGhostActive()) changePlayerColor(Color.GREEN);

      // move ship left or right direction
      if (!this.playerShip.isReceiveDamagePossible()) {
        managePlayerShipMovement(playerNumber, this.width, balance, web);
      }

      // Todo: enemyShipSpecialManager를 새로 만들어서 구현하기
      // Special enemy ship moves to right side.
      manageEnemyShipSpecial();

      // Check if the player ship has been shot or not.
      this.playerShip.update();

      // If Time-stop is active, Stop updating enemy ships' move and their shoots.
      if (!itemManager.isTimeStopActive()) {
        this.enemyShipFormation.update();
        this.enemyShipFormation.shoot(this.bullets, this.gameState.getGameLevel(), balance);
      }

      // Events where vision obstructions appear start from level 3 onwards.
      if (gameState.getGameLevel() >= 3) {
        handleBlockerAppearance();
      }
    }

    manageCollisions();

    manageLevelUpSkillStats(this.gameState.getPlayerShip());

    cleanBullets();

    draw();

    checkLevelCompletion();
  }

  /** check current level is finished or not. */
  private void checkLevelCompletion() {
    this.enemyShipFormation.update();
    if ((this.enemyShipFormation.isEmpty() || this.playerShip.isDestroyed())
        && !this.levelFinished) {
      this.levelFinished = true;
      this.screenFinishedCooldown.reset();
      if (this.playerShip.isDestroyed()) { // 게임 오버
        this.isGameOver = true;
        soundManager.playSound(Sound.GAME_END);
      } else {
        soundManager.stopSound(soundManager.getCurrentBGM());
      }
    }

    if (this.levelFinished && this.screenFinishedCooldown.checkFinished()) {
      // Reset alert message when level is finished
      this.alertMessage = "";
      this.isRunning = false;
    }
  }

  // Todo: enemyShipSpecialManager()로 분리
  /** */
  private void manageEnemyShipSpecial() {
    // Special enemy ship movement
    handleEnemyShipSpecialMovement();

    // Special enemy ship appears.
    spawnEnemyShipSpecial();
    makeAlertEnemyShipSpecialAppears();

    // Special enemy ship disappears.
    handleEnemyShipSpecialDisappear();
  }

  /** make an alert that special enemy ship is going to appear */
  private void makeAlertEnemyShipSpecialAppears() {
    if (this.enemyShipSpecial == null && this.enemyShipSpecialCooldown.checkAlert()) {
      switch (this.enemyShipSpecialCooldown.checkAlertAnimation()) {
        case 1:
          this.alertMessage = "--! ALERT !--";
          break;

        case 2:
          this.alertMessage = "-!! ALERT !!-";
          break;

        case 3:
          this.alertMessage = "!!! ALERT !!!";
          break;

        default:
          this.alertMessage = "";
          break;
      }
    }
  }

  /** spawn special enemy ship */
  private void spawnEnemyShipSpecial() {
    if (this.enemyShipSpecial == null && this.enemyShipSpecialCooldown.checkFinished()) {
      this.enemyShipSpecial = new EnemyShip();
      this.alertMessage = "";
      this.enemyShipSpecialCooldown.reset();
      soundManager.playSound(Sound.UFO_APPEAR, balance);
      this.logger.info("A special ship appears");
    }
  }

  /** make special enemy ship disappear */
  private void handleEnemyShipSpecialDisappear() {
    if (this.enemyShipSpecial != null && this.enemyShipSpecial.getPositionX() > this.width) {
      this.enemyShipSpecial = null;
      this.logger.info("The special ship has escaped");
    }
  }

  /** handle movement of special enemy ship */
  private void handleEnemyShipSpecialMovement() {
    if (this.enemyShipSpecial != null) {
      if (!this.enemyShipSpecial.isDestroyed()) this.enemyShipSpecial.move(2, 0);
      else if (this.enemyShipSpecialExplosionCooldown.checkFinished()) this.enemyShipSpecial = null;
    }
  }

  /**
   * change color of player ship.
   *
   * @param color the color that the player ship to be.
   */
  private void changePlayerColor(Color color) {
    this.playerShip.setColor(color);
  }

  /** update elapsed time of playing game. */
  private void manageElapsedTime() {
    long currentTime = System.currentTimeMillis();

    if (!this.isPaused) {
      if (this.prevTime != null) {
        this.gameState.setElapsedTime(
            (int)
                (this.gameState.getElapsedTime()
                    + (currentTime - this.prevTime))); // 일시정지 상태가 아닐 때만 시간 업데이트
      }
      this.prevTime = (int) currentTime;
    } else {
      // 일시정지 상태에서는 prevTime을 업데이트하지 않음
      this.prevTime = null;
    }
  }

  /**
   * manage movement of player ship
   *
   * @param playerNumber number of player. 0: 1-player, 1: 2-player
   * @param width width of current screen
   * @param balance balacne for sound
   * @param webs list of spider web
   */
  private void managePlayerShipMovement(
      int playerNumber, int width, float balance, List<Web> webs) {
    playerActionManager.manageMovement(playerNumber, width, balance, webs);
  }

  /**
   * manage shooting of player ship
   *
   * @param bullets set of bullet of player ship
   */
  private void managePlayerShooting(Set<Bullet> bullets) {
    playerActionManager.manageShooting(bullets);
  }

  private void manageGameStop() {
    if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
      if (!escKeyPressed) {
        // 게임이 레벨업 상태이거나 카운트다운 상태일 때 ESC 입력을 무시
        if (!this.levelFinished && this.inputDelay.checkFinished()) {
          togglePause();

          // 게임을 일시정지 상태로 전환했을 때, StopScreen 호출
          if (this.isPaused) {
            StopScreen stopScreen = new StopScreen(this.width, this.height, this.fps);
            int returnCode = stopScreen.run();
            if (returnCode == 1 && !this.playerShip.isDestroyed()) {
              // 메인 메뉴로 돌아가기
              this.isGotoMainMenu = true;
              this.isRunning = false;
            } else {
              // 게임을 재개
              togglePause(); // StopScreen에서 Resume을 선택했을 때 게임을 다시 재개
            }
          }
        }
        escKeyPressed = true; // 키가 눌렸음을 기록
      }
    } else {
      escKeyPressed = false; // 키가 떼어진 경우 초기화
    }
  }

  /**
   * manages Level up
   *
   * @param playerShip current Player Ship
   */
  private void manageLevelUpSkillStats(PlayerShip playerShip) {
    if (playerShip.isPlayerLevelUpPossible()) {
      togglePause();
      playerShip.managePlayerLevelUp();
      togglePause();
    }
  }

  /**
   * If current state is not a pause state, switch on the pause button, else, switch off the pause
   * button
   */
  private void togglePause() {
    if (!this.isPaused) {
      // 게임을 일시정지 상태로 설정하고 일시정지 시작 시간을 기록
      this.isPaused = true;
      this.pauseStartTime = System.currentTimeMillis();
      this.logger.info("Game paused");

    } else {
      // 게임이 멈춘 상태라면, 게임을 재개하고 일시정지 시간을 제외
      if (this.pauseStartTime != null) {
        long pauseEndTime = System.currentTimeMillis();
        this.gameState.setElapsedTime(
            (int)
                (this.gameState.getElapsedTime()
                    - (pauseEndTime - this.pauseStartTime))); // 일시정지된 시간만큼 빼기 (밀리초 단위)
        this.pauseStartTime = null;
      }
      this.isPaused = false;
      this.logger.info("Game resumed");
    }
  }

  /** Draws the elements associated with the screen. */
  private void draw() {
    drawManager.initDrawing(this);
    GameDrawManager.drawGameTitle(this);

    GameDrawManager.drawLaunchTrajectory(this, this.playerShip.getPositionX());

    GameDrawManager.drawEntity(
        this.playerShip, this.playerShip.getPositionX(), this.playerShip.getPositionY());

    // draw Spider Web
    for (int i = 0; i < web.size(); i++) {
      GameDrawManager.drawEntity(
          this.web.get(i), this.web.get(i).getPositionX(), this.web.get(i).getPositionY());
    }
    // draw Blocks
    for (Block block : block)
      GameDrawManager.drawEntity(block, block.getPositionX(), block.getPositionY());

    if (this.enemyShipSpecial != null)
      GameDrawManager.drawEntity(
          this.enemyShipSpecial,
          this.enemyShipSpecial.getPositionX(),
          this.enemyShipSpecial.getPositionY());

    enemyShipFormation.draw();

    for (ItemBox itemBox : this.itemBoxes)
      GameDrawManager.drawEntity(itemBox, itemBox.getPositionX(), itemBox.getPositionY());

    for (Barrier barrier : this.barriers)
      GameDrawManager.drawEntity(barrier, barrier.getPositionX(), barrier.getPositionY());

    for (Bullet bullet : this.bullets)
      GameDrawManager.drawEntity(bullet, bullet.getPositionX(), bullet.getPositionY());

    // Interface.
    GameDrawManager.drawScore(this, this.gameState.getScore());
    GameDrawManager.drawElapsedTime(this, this.gameState.getElapsedTime());
    GameDrawManager.drawAlertMessage(this, this.alertMessage);
    //		drawManager.drawLives(this, this.lives, this.shipType);
    GameDrawManager.drawLevel(this, this.gameState.getGameLevel());
    GameDrawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
    GameDrawManager.drawReloadTimer(this, this.playerShip, playerShip.getRemainingReloadTime());
    GameDrawManager.drawCombo(this, this.gameState.getCombo());

    // HUD with essential information. (Item, HP, EXP)
    int HUD_Y = 640;
    GameDrawManager.drawHorizontalLine(this, HUD_Y);
    GameDrawManager.drawHorizontalLine(this, HUD_Y);
    int HUD_MARGIN_TOP = 16; // Padding from the top of the item box
    int BOX_WIDTH = 48; // Width of the item box
    int BOX_HEIGHT = 48; // Height of the item box
    int BOX_MARGIN = 10; // Margin between boxes

//    // 배열로 스킬 객체 생성, 추후 다른 스킬 객체로 채워질 수 있음
//    Skill[] skills = new Skill[3];
//    for (int i = 0; i < skills.length; i++) {
//      skills[i] = new LaserStrike(); // 초기화는 LaserStrike로, 다른 스킬로 대체 가능
//    }
//
//    // 스킬 박스와 스킬 아이콘 그리기
//    for (int i = 0; i < skills.length; i++) {
//      int offsetX = 20 + i * (BOX_WIDTH + BOX_MARGIN); // X 좌표 계산
//      GameDrawManager.drawThickBox(
//          this, offsetX, HUD_Y + HUD_MARGIN_TOP, BOX_WIDTH, BOX_HEIGHT, 2); // 스킬 박스 그리기
//      GameDrawManager.drawEntity(skills[i], offsetX + 2, HUD_Y + HUD_MARGIN_TOP + 2); // 스킬 아이콘 그리기
//    }

    // 스탯 박스와 스탯 아이콘 그리기
    int[] statValues = {
            gameState.getPlayerShip().getSpeed(),
            gameState.getPlayerShip().getBulletSpeed(),
            gameState.getPlayerShip().getPlayerAttackDamage()
    };

    int[] offsetX = {20, 20 + 1 * (BOX_WIDTH + BOX_MARGIN), 20 + 2 * (BOX_WIDTH + BOX_MARGIN)};

    for (int i = 0; i < 3; i++) {
      int currentOffsetX = offsetX[i];  // X 좌표
      int offsetY = HUD_Y + HUD_MARGIN_TOP;  // Y 좌표
      GameDrawManager.drawThickBox(this, currentOffsetX, offsetY, BOX_WIDTH, BOX_HEIGHT, 2);
      GameDrawManager.drawStat(this, statValues[i], currentOffsetX, offsetY);
      //GameDrawManager.drawStatIcon();
      GameDrawManager.drawStatIcon(this, i, currentOffsetX + 32, offsetY + 2);
    }

    // Draw HP & EXP
    int currentHP = gameState.getPlayerShip().getPlayerHP(); // Current HP of the player
    int maxHP =gameState.getPlayerShip().getPlayerMaxHP(); // Maximum HP of the player
    int currentEXP = PlayerLevel.getExp(); // Current EXP of the player
    int maxEXP = PlayerLevel.getRequiredExpForLevelUp(PlayerLevel.level); // Maximum EXP required for level up
    GameDrawManager.drawSegmentedBar(220, HUD_Y + HUD_MARGIN_TOP + 7, 350, 12, currentHP, maxHP, Color.GREEN);
    GameDrawManager.drawSegmentedBar(220, HUD_Y + HUD_MARGIN_TOP + 17 + 10, 350, 12, currentEXP, maxEXP, Color.YELLOW);

    // Countdown to game start.
    if (!this.inputDelay.checkFinished()) {
      int countdown =
          (int) ((INPUT_DELAY - (System.currentTimeMillis() - this.gameStartTime)) / 1000);
      GameDrawManager.drawCountDown(this, this.gameState.getGameLevel(), countdown);
      GameDrawManager.drawHorizontalLine(this, this.height / 2 - this.height / 12);
      GameDrawManager.drawHorizontalLine(this, this.height / 2 + this.height / 12);

      // Intermediate aggregation
      if (this.gameState.getGameLevel() > 1) {
        if (countdown == 0) {
          // Reset max combo and edit temporary values
          this.gameState.setPrevTime(this.gameState.getElapsedTime());
          this.gameState.setPrevScore(this.gameState.getScore());
          this.gameState.setMaxCombo(0);
        } else {
          // Don't show it just before the game starts, i.e. when the countdown is zero.
          GameDrawManager.interAggre(
              this,
              this.gameState.getGameLevel() - 1,
              this.gameState.getMaxCombo(),
              this.gameState.getElapsedTime(),
              this.gameState.getPrevTime(),
              this.gameState.getScore(),
              this.gameState.getPrevScore());
        }
      }
    }

    // add drawRecord method for drawing
    GameDrawManager.drawRecord(highScores, this);

    // Blocker drawing part
    if (!blockers.isEmpty()) {
      for (Blocker blocker : blockers) {
        GameDrawManager.drawRotatedEntity(
            blocker, blocker.getPositionX(), blocker.getPositionY(), blocker.getAngle());
      }
    }

    drawManager.completeDrawing(this);
  }

  // Methods that handle the position, angle, sprite, etc. of the blocker (called repeatedly in
  // update.)
  private void handleBlockerAppearance() {

    if (gameState.getGameLevel() >= 3 && gameState.getGameLevel() < 6) MAX_BLOCKERS = 1;
    else if (gameState.getGameLevel() >= 6 && gameState.getGameLevel() < 11) MAX_BLOCKERS = 2;
    else if (gameState.getGameLevel() >= 11) MAX_BLOCKERS = 3;

    int kind = random.nextInt(2 - 1 + 1) + 1; // 1~2
    DrawManager.SpriteType newSprite;
    switch (kind) {
      case 1:
        newSprite = DrawManager.SpriteType.Blocker1; // artificial satellite
        break;
      case 2:
        newSprite = DrawManager.SpriteType.Blocker2; // astronaut
        break;
      default:
        newSprite = DrawManager.SpriteType.Blocker1;
        break;
    }

    // Check number of blockers, check timing of exit
    if (blockers.size() < MAX_BLOCKERS && blockerCooldown.checkFinished()) {
      boolean moveLeft =
          random.nextBoolean(); // Randomly sets the movement direction of the current blocker
      int startY =
          random.nextInt(this.height - 90)
              + 25; // Random Y position with margins at the top and bottom of the screen
      int startX =
          moveLeft
              ? this.width + 300
              : -300; // If you want to move left, outside the right side of the screen, if you want
      // to move right, outside the left side of the screen.
      // Add new Blocker
      if (moveLeft) {
        blockers.add(new Blocker(startX, startY, newSprite, moveLeft)); // move from right to left
      } else {
        blockers.add(new Blocker(startX, startY, newSprite, moveLeft)); // move from left to right
      }
      blockerCooldown.reset();
    }

    // Items in the blocker list that will disappear after leaving the screen
    List<Blocker> toRemove = new ArrayList<>();
    for (int i = 0; i < blockers.size(); i++) {
      Blocker blocker = blockers.get(i);

      // If the blocker leaves the screen, remove it directly from the list.
      if (blocker.getMoveLeft() && blocker.getPositionX() < -300
          || !blocker.getMoveLeft() && blocker.getPositionX() > this.width + 300) {
        blockers.remove(i);
        i--; // When an element is removed from the list, the index must be decreased by one place.
        continue;
      }

      // Blocker movement and rotation (positionX, Y value change)
      if (blocker.getMoveLeft()) {
        blocker.move(-1.5, 0); // move left
      } else {
        blocker.move(1.5, 0); // move right
      }
      blocker.rotate(0.2); // Blocker rotation
    }

    // Remove from the blocker list that goes off screen
    blockers.removeAll(toRemove);
  }

  /** Cleans bullets that go off screen. */
  private void cleanBullets() {
    Set<Bullet> recyclable = new HashSet<Bullet>();
    for (Bullet bullet : this.bullets) {
      bullet.update();
      if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT || bullet.getPositionY() > this.height)
        recyclable.add(bullet);
    }
    this.bullets.removeAll(recyclable);
    BulletPool.recycle(recyclable);
  }

  /** Manages collisions among entities in the game. */
  private void manageCollisions() {
    Set<Bullet> recyclable = new HashSet<Bullet>();

    // reset combo timer
    resetComboTimer();

    // calculate the top position of enemy
    int topEnemyY = calculateTopEnemyY();

    for (Bullet bullet : this.bullets) {
      // process collision between bullets and entities
      handleBulletCollisions(bullet, recyclable, topEnemyY);
    }

    handleEnemyShipAndBlockCollision();

    this.bullets.removeAll(recyclable);
    BulletPool.recycle(recyclable);
  }

  /**
   * handle collision between enemy ship and block. if they collide, then the block is destroyed.
   */
  private void handleEnemyShipAndBlockCollision() {
    // check the collision between the obstacle and the enemyship
    Set<Block> removableBlocks = new HashSet<>();
    for (EnemyShip enemyShip : this.enemyShipFormation) {
      if (enemyShip != null && !enemyShip.isDestroyed()) {
        for (Block block : block) {
          if (checkCollision(enemyShip, block)) {
            removableBlocks.add(block);
          }
        }
      }
    }
    // remove crashed obstacle
    block.removeAll(removableBlocks);
  }

  /**
   * handles both enemy's bullet and entities, and player's bullet and entities
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   * @param topEnemyY top position of enemy
   */
  private void handleBulletCollisions(Bullet bullet, Set<Bullet> recyclable, int topEnemyY) {
    // Enemy ship's bullets
    if (bullet.getSpeed() > 0) {
      manageEnemyBulletCollision(bullet, recyclable);

    } else { // Player ship's bullets
      managePlayerBulletCollision(bullet, recyclable, topEnemyY);
    }
  }

  /**
   * manages player's bullet and entities(enemy ship, special ship, item box, block)
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   * @param topEnemyY top position of enemy
   */
  private void managePlayerBulletCollision(Bullet bullet, Set<Bullet> recyclable, int topEnemyY) {
    // 플레이어 총알과 적군의 충돌을 검사
    for (EnemyShip enemyShip : this.enemyShipFormation) {
      if (enemyShip != null && !enemyShip.isDestroyed() && checkCollision(bullet, enemyShip)) {
        processEnemyHit(bullet, recyclable, enemyShip);
      }
    }
    // 플레이어의 총알과 스페셜 적군과 총알의 충돌을 검사
    if (this.enemyShipSpecial != null
        && !this.enemyShipSpecial.isDestroyed()
        && checkCollision(bullet, this.enemyShipSpecial)) {
      processSpecialEnemyHit(bullet, recyclable);
    }
    // 콤보 초기화
    if (bullet.getPositionY() < topEnemyY) {
      this.gameState.setCombo(0);
      isExecuted = true;
    }
    // 플레이어 총알과 아이템박스의 충돌을 검사
    handleBulletAndItemBoxCollision(bullet, recyclable);

    // 플레이어 총알과 블럭 장애물과의 충돌을 검사
    handleBulletAndBlockCollision(bullet, recyclable);
  }

  /**
   * handles collision between player bullet and block.
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   */
  private void handleBulletAndBlockCollision(Bullet bullet, Set<Bullet> recyclable) {
    for (Block block : this.block) {
      if (checkCollision(bullet, block)) {
        recyclable.add(bullet);
        soundManager.playSound(Sound.BULLET_BLOCKING, balance);
        break;
      }
    }
  }

  /**
   * handles collision between player bullet and item box.
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   */
  private void handleBulletAndItemBoxCollision(Bullet bullet, Set<Bullet> recyclable) {
    Iterator<ItemBox> itemBoxIterator = this.itemBoxes.iterator();
    while (itemBoxIterator.hasNext()) {
      ItemBox itemBox = itemBoxIterator.next();
      if (checkCollision(bullet, itemBox) && !itemBox.isDroppedRightNow()) {
        this.gameState.setHitBullets(this.gameState.getHitBullets() + 1);
        itemBoxIterator.remove();
        recyclable.add(bullet);
        List<Integer> itemResult = this.itemManager.useItem();

        // only in case of bomb item, itemResult is not null
        if (itemResult != null) {
          this.gameState.setScore(this.gameState.getScore() + itemResult.getFirst());
          this.gameState.getPlayerShip().increasePlayerExp(itemResult.get(1));
          this.gameState.setShipsDestroyed(
              this.gameState.getShipsDestroyed() + itemResult.getLast());
        }
      }
    }
  }

  /**
   * Handles the logic for processing when a special enemy is hit by a player's bullet. This
   * includes updating the enemy's destruction state, awarding the player with score and experience
   * points, managing combo counters, and determining item drops.
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   */
  private void processSpecialEnemyHit(Bullet bullet, Set<Bullet> recyclable) {
    this.gameState.setScore(
        this.gameState.getScore()
            + Score.comboScore(this.enemyShipSpecial.getPointValue(), this.gameState.getCombo()));
    this.gameState.getPlayerShip().increasePlayerExp(this.enemyShipSpecial.getExpValue());
    logger.info("You got this exp by shooing bullets to speical enemy: " + this.gameState.getExp());
    this.gameState.setShipsDestroyed(this.gameState.getShipsDestroyed() + 1);
    this.gameState.setCombo(this.gameState.getCombo() + 1);
    this.gameState.setHitBullets(this.gameState.getHitBullets() + 1);
    if (this.gameState.getCombo() > this.gameState.getMaxCombo())
      this.gameState.setMaxCombo(this.gameState.getCombo());
    this.enemyShipSpecial.destroy(balance);
    this.enemyShipSpecialExplosionCooldown.reset();
    timer.cancel();
    isExecuted = false;

    recyclable.add(bullet);
  }

  /**
   * Handles the logic for processing when an enemy is hit by a player's bullet. This includes
   * applying damage, updating the enemy's health or destruction state, awarding the player with
   * score and experience points, managing combo counters, and determining item drops.
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   * @param enemyShip enemy ship hit by player ship
   */
  private void processEnemyHit(Bullet bullet, Set<Bullet> recyclable, EnemyShip enemyShip) {
    // Decide whether to destroy according to physical strength
    int playerAttackPower = this.gameState.getPlayerShip().getPlayerAttackDamage();
    this.enemyShipFormation.applyDamageToEnemy(playerAttackPower, enemyShip, balance);
    // If the enemy doesn't die, the combo increases;
    // if the enemy dies, both the combo and score increase.
    this.gameState.setScore(
        this.gameState.getScore()
            + Score.comboScore(this.enemyShipFormation.getPointValue(), this.gameState.getCombo()));
    logger.info("your score: " + gameState.getScore());
    this.gameState.getPlayerShip().increasePlayerExp(this.enemyShipFormation.getExpValue());
    this.gameState.setShipsDestroyed(
        this.gameState.getShipsDestroyed() + this.enemyShipFormation.getDistroyedship());
    this.gameState.setCombo(this.gameState.getCombo() + 1);
    this.gameState.setHitBullets(this.gameState.getHitBullets() + 1);
    if (this.gameState.getCombo() > this.gameState.getMaxCombo())
      this.gameState.setMaxCombo(this.gameState.getCombo());
    timer.cancel();
    isExecuted = false;
    recyclable.add(bullet);

    if (enemyShip.getHealth() <= 0 && itemManager.dropItem()) {
      this.itemBoxes.add(
          new ItemBox(enemyShip.getPositionX() + 6, enemyShip.getPositionY() + 1, balance));
      logger.info("Item box dropped");
    }
  }

  /**
   * manages enemy's bullet and entities(player ship, barrier)
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   */
  private void manageEnemyBulletCollision(Bullet bullet, Set<Bullet> recyclable) {
    // collision between enemy's bullet and player ship
    if (checkCollision(bullet, this.playerShip)
        && !this.levelFinished
        && !itemManager.isGhostActive()) {
      recyclable.add(bullet);
      deductPlayerHp(1); // Todo: damage를 적군의 다양성에 따라 다르게 변수로 집어넣기
    }
    // collision between enemy's bullet and barrier
    manageBarrierDestroy(bullet, recyclable);
  }

  /**
   * manages destroy of barrier by enemy bullet
   *
   * @param bullet bullet that entity shoots
   * @param recyclable recyclable bullet pools
   */
  private void manageBarrierDestroy(Bullet bullet, Set<Bullet> recyclable) {
    if (this.barriers != null) {
      Iterator<Barrier> barrierIterator = this.barriers.iterator();
      while (barrierIterator.hasNext()) {
        Barrier barrier = barrierIterator.next();
        if (checkCollision(bullet, barrier)) {
          recyclable.add(bullet);
          barrier.deductHealth(1, balance); // Todo: damage를 적군의 다양성에 따라 다르게 변수로 집어넣기
          if (barrier.isDestroyed()) {
            barrierIterator.remove();
          }
        }
      }
    }
  }

  /**
   * deduct HP of player ship
   *
   * @param damage damage that get from enemy
   */
  private void deductPlayerHp(int damage) {
    if (!this.playerShip.isReceiveDamagePossible()) {
      this.playerShip.receiveDamage(damage, balance);
    }
  }

  /** calculate the Y position of top enemy in the screen */
  private int calculateTopEnemyY() {
    int topEnemyY = Integer.MAX_VALUE;
    for (EnemyShip enemyShip : this.enemyShipFormation) {
      if (enemyShip != null && !enemyShip.isDestroyed() && enemyShip.getPositionY() < topEnemyY) {
        topEnemyY = enemyShip.getPositionY();
      }
    }
    if (this.enemyShipSpecial != null
        && !this.enemyShipSpecial.isDestroyed()
        && this.enemyShipSpecial.getPositionY() < topEnemyY) {
      topEnemyY = this.enemyShipSpecial.getPositionY();
    }
    return topEnemyY;
  }

  /** reset combo timer */
  private void resetComboTimer() {
    if (!isExecuted) {
      isExecuted = true;
      timer = new Timer();
      timerTask =
          new TimerTask() {
            public void run() {
              gameState.setCombo(0);
            }
          };
      timer.schedule(timerTask, 3000);
    }
  }

  /**
   * Checks if two entities are colliding.
   *
   * @param a First entity, the bullet.
   * @param b Second entity, the ship.
   * @return Result of the collision test.
   */
  private boolean checkCollision(final Entity a, final Entity b) {
    // Calculate center point of the entities in both axis.
    int centerAX = a.getPositionX() + a.getWidth() / 2;
    int centerAY = a.getPositionY() + a.getHeight() / 2;
    int centerBX = b.getPositionX() + b.getWidth() / 2;
    int centerBY = b.getPositionY() + b.getHeight() / 2;
    // Calculate maximum distance without collision.
    int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
    int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
    // Calculates distance.
    int distanceX = Math.abs(centerAX - centerBX);
    int distanceY = Math.abs(centerAY - centerBY);

    return distanceX < maxDistanceX && distanceY < maxDistanceY;
  }

  /**
   * Returns a GameState object representing the status of the game.
   *
   * @return Current game state.
   */
  public final GameState getGameState() {
    return this.gameState;
  }

  //	public final GameState getGameState() {
  //		return new GameState(this.gameLevel, this.shipLevel, this.score, this.exp, this.shipType,
  // this.lives,
  //				this.bulletsShot, this.shipsDestroyed, this.elapsedTime, this.alertMessage, 0,
  // this.maxCombo, this.lapTime, this.tempScore, this.hitBullets);	}

  /**
   * Start the action for two player mode
   *
   * @return Current game state.
   */
  @Override
  public final GameState call() {
    run();
    return getGameState();
  }

  public boolean getIsGotoMainMenu() {
    return this.isGotoMainMenu;
  }
}