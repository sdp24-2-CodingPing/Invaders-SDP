package engine.drawmanager;

import engine.DrawManager;
import java.awt.*;
import java.util.List;
import screen.Screen;

/** Manages drawing for the Credit Screen. */
public class CreditDrawManager extends DrawManager {

  /**
   * Draws credit screen.
   *
   * @param screen Screen to draw on.
   */
  public static void drawEndingCredit(
      final Screen screen, List<String> creditlist, int currentFrame) {
    backBufferGraphics.setColor(Color.WHITE);
    final int startPoint = screen.getHeight() / 2;

    for (int i = 0; i < creditlist.size(); i++) {
      String target = creditlist.get(i);
      drawCenteredRegularString(
          screen, target, startPoint + (fontRegularMetrics.getHeight() * 2) * i - currentFrame);
    }
  }
}
