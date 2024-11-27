package engine.drawmanager;

import engine.DrawManager;
import entity.card.Card;
import screen.Screen;

import java.awt.*;
import java.util.ArrayList;

public class CardSelectDrawManager extends DrawManager {

    public static void drawCardSelectTitle(final Screen screen) {
        String titleString = "Select The Card";
        String instructionsString =
                "select with arrows, confirm with space";

        backBufferGraphics.setColor(Color.GRAY);
        drawCenteredRegularString(screen, instructionsString,
                screen.getHeight() / 5 * 2);

        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredBigString(screen, titleString, screen.getHeight() / 5);
    }

    public static void drawCard(final Screen screen, final Card card, final int width, final int height, final boolean isSelected) {
        final int cardWidth = screen.getWidth() / 4;
        final int cardHeight = screen.getHeight() / 4;

        final int imageWidth = cardWidth / 2;
        final int imageHeight = cardHeight / 3;

        final String cardString = card.getCardName();

        if (isSelected)	backBufferGraphics.setColor(Color.GREEN);
        else 			backBufferGraphics.setColor(Color.DARK_GRAY);
        backBufferGraphics.fillRect(width, height, cardWidth, cardHeight);

        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(width + (int)(cardWidth * 0.05), height + (int)(cardHeight * 0.04), (int)(cardWidth * 0.9), (int)(cardHeight * 0.92));

        backBufferGraphics.drawImage(card.getCardImage(), width + cardWidth / 2 - imageWidth / 2, height + cardHeight / 4 , imageWidth, imageHeight, null);

        backBufferGraphics.setColor(Color.GRAY);
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(cardString, width + cardWidth / 2
                - fontRegularMetrics.stringWidth(cardString) / 2, height + cardHeight * 3 / 4);

    }

    // TODO: Card Design 추가 수정 필요
    public static void drawCardList(final Screen screen, final ArrayList<Card> cardList, final int selectedCardIndex) {
        final int cardListSize = cardList.size();
        for (int i=0; i < cardListSize; i++) {
            drawCard(screen, cardList.get(i), screen.getWidth() / 3 * i + screen.getWidth() / 24, screen.getHeight() / 5 * 3, i==selectedCardIndex);
        }
    }

}
