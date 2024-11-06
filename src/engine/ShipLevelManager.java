package engine;

import java.util.List;
import java.util.logging.Logger;
/**
 * manages exp, level, skill and stats
 * @author raewookang
 * */
public class ShipLevelManager {
    int exp;
    int shipLevel;
    /** Application logger. */
    private final Logger logger;

    // 각 레벨에 필요한 경험치를 저장하는 배열 (예: level 1 -> 100 exp, level 2 -> 200 exp)
    private static final int[] EXP_REQUIRED = {0, 100, 200, 400, 800, 1600, 3200, 6400, 12800};

    /**
     * constructor
     * */
    public ShipLevelManager(int exp, int shipLevel) {
        this.exp = exp;
        this.shipLevel = shipLevel;
        this.logger = Core.getLogger();
    }

    /**
     * check current exp and current level, level up if it is possible.
     * @param exp current exp
     * @param shipLevel current ship level
     * @return List<Integer> contains surplus exp after level up and level that you got if your exp is sufficient to level up. Unless, your current exp and current level
     * */
    public List<Integer> levelUp(int exp, int shipLevel) {
        int newExp = exp;
        int newShipLevel = shipLevel;

        //logger.info("Old exp: " + newExp + ", Old ship level: " + newShipLevel);
        int requiredExp = getRequiredExpForLevel(shipLevel+1);
        while (newExp >= requiredExp) {
            newExp -= requiredExp;
            newShipLevel++;
        }

        //logger.info("New exp: " + newExp + ", New ship level: " + newShipLevel);
        return List.of(newExp, newShipLevel);
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
            return 0; // 경험치 요구치가 없거나 추가적인 레벨을 위한 설정이 필요할 때
        }
    }

    public int getLevel() {
        return shipLevel;
    }
}
