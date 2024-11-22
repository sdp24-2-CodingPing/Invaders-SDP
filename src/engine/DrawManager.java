package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import entity.*;
import entity.player.PlayerShip;
import screen.Screen;

/**
 * Manages screen drawing.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class DrawManager {
	/** Singleton instance of the class. */
	private static DrawManager instance;
	/** Current frame. */
	public static Frame frame;
	/** FileManager instance. */
	private static FileManager fileManager;
	/** Application logger. */
	protected static Logger logger;
	/** Graphics context. */
	public static Graphics graphics;
	/** Buffer Graphics. */
	protected static Graphics backBufferGraphics;
	/** Buffer Graphics for multi screens. */
	public static final Graphics[] threadBufferGraphics = new Graphics[2];
	/** Buffer image. */
	public static BufferedImage backBuffer;
	/** Buffer images for multi screens **/
	public static final BufferedImage[] threadBuffers = new BufferedImage[4];
	/** Small sized font. */
	protected static Font fontSmall;
	/** Small sized font properties. */
	protected static FontMetrics fontSmallMetrics;
	/** Normal sized font. */
	protected static Font fontRegular;
	/** Normal sized font properties. */
	protected static FontMetrics fontRegularMetrics;
	/** Big sized font. */
	protected static Font fontBig;
	/** Big sized font properties. */
	protected static FontMetrics fontBigMetrics;
	/** Vertical line width for two player mode **/
	public static final int LINE_WIDTH = 1;

	/** Sprite types mapped to their images. */
	public static Map<SpriteType, boolean[][]> spriteMap;

	/** For ShopScreen image */
	protected static BufferedImage img_additionallife;
	protected static BufferedImage img_bulletspeed;
	protected static BufferedImage img_coin;
	protected static BufferedImage img_coingain;
	protected static BufferedImage img_shotinterval;


	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		Ship,
		/** Destroyed player ship. */
		ShipDestroyed,
		/** Player bullet. */
		Bullet,
		/** Enemy bullet. */
		EnemyBullet,
		/** First enemy ship - first form. */
		EnemyShipA1,
		/** First enemy ship - second form. */
		EnemyShipA2,
		/** Second enemy ship - first form. */
		EnemyShipB1,
		/** Second enemy ship - second form. */
		EnemyShipB2,
		/** Third enemy ship - first form. */
		EnemyShipC1,
		/** Third enemy ship - second form. */
		EnemyShipC2,
		/** Bonus ship. */
		EnemyShipSpecial,
		/** Destroyed enemy ship. */
		Explosion,
		/** Barrier. */
		Barrier,
        /** Item Box. */
        ItemBox,
		/** Spider webs restricting player movement */
		Web,
		/** Obstacles preventing a player's bullet */
		Block,
		/** Obstruction 1 (satellite) */
		Blocker1,
		/** Obstruction 2 (Astronaut) */
		Blocker2,
        /** 2nd player ship. */
        Ship2,
        /** 3rd player ship. */
        Ship3,
        /** 4th player ship. */
        Ship4,
		/** Fourth enemy ship - first form. */
		EnemyShipD1,
		/** Fourth enemy ship - second form. */
		EnemyShipD2,
		/** Fifth enemy ship - first form. */
		EnemyShipE1,
		/** Fifth enemy ship - second form. */
		EnemyShipE2,
		/** Elite enemy ship - first form. */
		EnemyShipF1
	};

	/**
	 * Protected constructor.
	 */
    protected DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");

		try {
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();

			spriteMap.put(SpriteType.Ship, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.Barrier, new boolean[39][11]);
			spriteMap.put(SpriteType.ItemBox, new boolean[7][7]);
			spriteMap.put(SpriteType.Web, new boolean[12][8]);
			spriteMap.put(SpriteType.Block, new boolean[20][7]);
			spriteMap.put(SpriteType.Blocker1, new boolean[182][93]); // artificial satellite
			spriteMap.put(SpriteType.Blocker2, new boolean[82][81]); // astronaut
			spriteMap.put(SpriteType.Ship2, new boolean[13][8]);
			spriteMap.put(SpriteType.Ship3, new boolean[13][8]);
			spriteMap.put(SpriteType.Ship4, new boolean[13][8]);
			spriteMap.put(SpriteType.EnemyShipD1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipD2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipE1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipE2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipF1, new boolean[16][7]);

			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontSmall = fileManager.loadFont(10f);
			fontRegular = fileManager.loadFont(14f);
			fontBig = fileManager.loadFont(24f);
			logger.info("Finished loading the fonts.");

		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}

		/** Shop image load*/
		try{
			img_additionallife = ImageIO.read(new File("res/image/additional life.jpg"));
			img_bulletspeed = ImageIO.read(new File("res/image/bullet speed.jpg"));
			img_coin = ImageIO.read(new File("res/image/coin.jpg"));
			img_coingain = ImageIO.read(new File("res/image/coin gain.jpg"));
			img_shotinterval = ImageIO.read(new File("res/image/shot interval.jpg"));
		} catch (IOException e) {
			logger.info("Shop image loading failed");
		}
	}

	/**
	 * Returns shared instance of DrawManager.
	 *
	 * @return Shared instance of DrawManager.
	 */
	protected static DrawManager getInstance() {
		if (instance == null)
			instance = new DrawManager();
		return instance;
	}

	/**
	 * Sets the frame to draw the image on.
	 *
	 * @param currentFrame
	 *            Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
	}

	/**
	 * First part of the drawing process. Initializes buffers, draws the
	 * background and prepares the images.
	 *
	 * @param screen
	 *            Screen to draw in.
	 */
	public void initDrawing(final Screen screen) {
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		graphics = frame.getGraphics();
		backBufferGraphics = backBuffer.getGraphics();

		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics
				.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		fontSmallMetrics = backBufferGraphics.getFontMetrics(fontSmall);
		fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
		fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);

		//drawBorders(screen);
		//drawGrid(screen);
	}

	/**
	 * First part of the drawing process in thread. Initializes buffers each thread, draws the
	 * background and prepares the images.
	 *
	 * @param screen
	 *            Screen to draw in.
	 * @param threadNumber
	 * 			  Thread number for two player mode
	 */
	public void initThreadDrawing(final Screen screen, final int threadNumber) {
		BufferedImage threadBuffer = new BufferedImage(screen.getWidth(),screen.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics threadGraphic = threadBuffer.getGraphics();

		threadBuffers[threadNumber] = threadBuffer;
		threadBufferGraphics[threadNumber] = threadGraphic;
	}

	/**
	 * Draws the completed drawing on screen.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * For debugging purposes, draws the canvas borders.
	 * 
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
		backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
		backBufferGraphics.drawLine(screen.getWidth() - 1, 0,
				screen.getWidth() - 1, screen.getHeight() - 1);
		backBufferGraphics.drawLine(0, screen.getHeight() - 1,
				screen.getWidth() - 1, screen.getHeight() - 1);
	}

	/**
	 * For debugging purposes, draws a grid over the canvas.
	 * 
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawGrid(final Screen screen) {
		backBufferGraphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < screen.getHeight() - 1; i += 2)
			backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
		for (int j = 0; j < screen.getWidth() - 1; j += 2)
			backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
	}

	/**
	 * Draws a box outline on the screen with the specified color.
	 *
	 * @param screen    Screen to draw on.
	 * @param color     Color of the box outline.
	 * @param x         X coordinate of the upper-left corner of the outline.
	 * @param y         Y coordinate of the upper-left corner of the outline.
	 * @param w         Width of the outline.
	 * @param h         Height of the outline.
	 */
	public void drawBox(final Screen screen, final Color color, final int x, final int y, final int w, final int h) {
		backBufferGraphics.setColor(color);
		backBufferGraphics.drawRect(x, y, w, h);
	}

	/**
	 * Draws game title.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "Invaders";
		String instructionsString =
				"select with w+s / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5 * 2);

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, titleString, screen.getHeight() / 5);
	}

	/**
	 * Draws main menu.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param option
	 *            Option selected.
	 */
	public void drawMenu(final Screen screen, final int option, final int coin) {
		String playString = "Play";
		String shopString = "SHOP";
		String coinString = "YOUR COIN: " + coin;
		String achievementString = "ACHIEVEMENT";
		String settingString = "SETTING";
		String exitString = "EXIT";


		if (option == 6) /*option2 => Game Settings */
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 7 * 4);

		if (option == 3) /*option3 => Shop */
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, shopString, screen.getHeight()
				/ 7 * 4 + fontRegularMetrics.getHeight() * 2);

		backBufferGraphics.setColor(Color.ORANGE);
		drawCenteredSmallString(screen, coinString, screen.getHeight()
				/ 7 * 4 + fontRegularMetrics.getHeight() * 3);

		if (option == 4) /*option4 => Achievement */
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, achievementString, screen.getHeight()
				/ 7 * 4 + fontRegularMetrics.getHeight() * 5);


		if (option == 5) /*option5 => Setting */
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, settingString, screen.getHeight()
				/ 7 * 4 + fontRegularMetrics.getHeight() * 7);

		if (option == 0) /*option0 => exit */
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, exitString, screen.getHeight()
				/ 7 * 4 + fontRegularMetrics.getHeight() * 9);
	}


	/**
	 * Draws a centered string on small font.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
    protected static void drawCenteredSmallString(final Screen screen, final String string, final int height) {
		backBufferGraphics.setFont(fontSmall);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontSmallMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on regular font.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public static void drawCenteredRegularString(final Screen screen,
                                                 final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontRegularMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on big font.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public static void drawCenteredBigString(final Screen screen, final String string,
                                             final int height) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, screen.getWidth() / 2
				- fontBigMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on big font.
	 *
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 * @param threadNumber
	 *            Thread number for two player mode
	 */
	public static void drawCenteredBigString(final Screen screen, final String string,
                                             final int height, final int threadNumber) {
		threadBufferGraphics[threadNumber].setFont(fontBig);
		threadBufferGraphics[threadNumber].drawString(string, screen.getWidth() / 2
				- fontBigMetrics.stringWidth(string) / 2, height);
	}

}
