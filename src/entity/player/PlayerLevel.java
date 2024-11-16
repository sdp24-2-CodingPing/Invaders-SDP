package entity.player;

import engine.Core;
import entity.Card;
import entity.skill.LaserStrike;
import entity.skill.Skill;
import screen.CardSelectScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    // 각 레벨에 필요한 경험치를 저장하는 배열 (예: level 1 -> 100 exp, level 2 -> 200 exp)
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
        return (this.exp >= this.getRequiredExpForLevel(this.level));
    }

    public void levelUp () {
        if (isLevelUpPossible()) {
            this.level += 1;
            this.exp -= this.getRequiredExpForLevel(this.level);
        }
    }

    public Card selectLevelUpCard () {
        ArrayList<Card> cardList = new ArrayList<>();

        cardList.add(new Card("card1"));
        cardList.add(new Card("card2"));
        cardList.add(new Card("card3"));

        CardSelectScreen cardSelectScreen = new CardSelectScreen(Core.getWidth(), Core.getHeight(), Core.getFps(), cardList);
        cardSelectScreen.initialize();
        int selectedCard = cardSelectScreen.run();

        logger.info("selected Card: " + selectedCard);
        return cardList.get(selectedCard);
    }

    /**
     * get required EXP for particular level
     * @param level particular level that you want to figure out how many EXP is required to get that level
     * @return int EXP required to get that level
     * */
    public int getRequiredExpForLevel(int level) {
        if (level < EXP_REQUIRED.length) {
            return EXP_REQUIRED[level];
        } else {
            return 100000000; // 경험치 요구치가 없거나 추가적인 레벨을 위한 설정이 필요할 때
        }
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
