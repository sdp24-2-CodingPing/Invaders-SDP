package entity.player;

import engine.Core;
import entity.card.*;
import screen.CardSelectScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
/**
 * manages exp, level, skill and stats
 * @author raewookang
 * */
public class PlayerLevel {
    int exp;
    int level;
    /** Application logger. */
    private final Logger logger;

    // 현제 레벨에서 레벨업 시 필요한 경험치를 저장하는 배열 (예: level 1 -> 100 exp, level 2 -> 200 exp)
    private static final int[] EXP_REQUIRED = {0, 100, 200, 400, 800, 1600, 3200, 6400, 12800};

    /**
     * constructor
     * */
    public PlayerLevel(int exp, int level) {
        this.exp = exp;
        this.level = level;
        this.logger = Core.getLogger();
    }

    public boolean isLevelUpPossible () {
        return (this.exp >= this.getRequiredExpForLevelUp(this.level));
    }

    public void levelUp () {
        if (isLevelUpPossible()) {
            this.exp -= this.getRequiredExpForLevelUp(this.level);
            this.level += 1;
            logger.info("Level Up! your level: " + this.level);
        }
    }

    public Card selectLevelUpCard () {
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.add(new AttackDamageCard());
        allCards.add(new MoveSpeedCard());
        allCards.add(new BulletsCountCard());
        allCards.add(new ShotIntervalCard());
        allCards.add(new BulletsSpeedCard());
        allCards.add(new HpCard());

        Collections.shuffle(allCards);

        ArrayList<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cardList.add(allCards.get(i));
        }

        CardSelectScreen cardSelectScreen = new CardSelectScreen(Core.getWidth(), Core.getHeight(), Core.getFps(), cardList);
        cardSelectScreen.initialize();
        int selectedCard = cardSelectScreen.run();

        logger.info("selected Card: " + selectedCard);
        return cardList.get(selectedCard);
    }

    /**
     * get required level up EXP for current level
     * @param level player ship current level
     * @return int EXP required to current level for level up
     * */
    public int getRequiredExpForLevelUp(int level) {
        return 100 * (int)(Math.pow(1.2, level - 1));
//        if (level < EXP_REQUIRED.length) {
//            return EXP_REQUIRED[level];
//        } else {
//            return 100000000; // 경험치 요구치가 없거나 추가적인 레벨을 위한 설정이 필요할 때
//        }
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
}
