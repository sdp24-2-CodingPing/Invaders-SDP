package engine.drawmanager;

import engine.DrawManager;
import screen.Screen;

import java.awt.*;

/**
 * Manages drawing for the Stop screen.
 */
public class StopDrawManager extends DrawManager {

    public static void drawPauseOverlay(Screen screen, int yOffset) {
        Graphics2D g = (Graphics2D) backBufferGraphics;

        // 화면 전체를 반투명한 검은색으로 채우기
        g.setColor(new Color(0, 0, 0, 150));  // RGB (0, 0, 0), 알파 값 150으로 반투명하게 설정
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());

        // "PAUSED" 문자열 그리기
        g.setColor(Color.WHITE);
        g.setFont(fontBig);  // 큰 글씨체로 설정
        String pauseText = "PAUSED";
        int textWidth = g.getFontMetrics().stringWidth(pauseText);
        int textX = (screen.getWidth() - textWidth) / 2;
        int textY = yOffset;
        g.drawString(pauseText, textX, textY);
    }

    public static void drawLeftAlignedRegularString(Screen screen, String text, int x, int y, boolean isSelected) {
        Graphics2D g = (Graphics2D) backBufferGraphics;

        if (isSelected) {
            g.setColor(Color.GREEN); // 색상 선택: 선택된 옵션일 때
        } else {
            g.setColor(Color.WHITE); // 색상 선택: 선택되지 않은 옵션일 때
        }

        g.setFont(fontRegular);
        g.drawString(text, x, y);
    }

}
