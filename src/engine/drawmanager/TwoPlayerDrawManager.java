package engine.drawmanager;

import engine.DrawManager;
import screen.Screen;

import java.awt.*;
import java.util.Date;

public class TwoPlayerDrawManager extends DrawManager {

    /**
     * Merge second buffers to back buffer
     *
     * @param screen
     *            Screen to draw on.
     */
    public static void mergeDrawing(final Screen screen) {
        backBufferGraphics.drawImage(threadBuffers[2], 0, 0, frame);
        backBufferGraphics.drawImage(threadBuffers[3], screen.getWidth() / 2 + LINE_WIDTH, 0, frame);
    }

    /**
     * Draws a thick line from side to side of the screen.
     *
     * @param screen
     *            Screen to draw on.
     */
    public static void drawVerticalLine(final Screen screen) {
        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.drawLine(screen.getWidth() /2  ,0,screen.getWidth() / 2,screen.getHeight());
    }

}
