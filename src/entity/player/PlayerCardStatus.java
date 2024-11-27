package entity.player;

public class PlayerCardStatus {
    private static int attackDamage;
    private static int intervalLevel;
    private static int bulletsCountLevel;
    private static int bulletsSpeedLevel;
    private static int hpLevel;
    private static int moveSpeedLevel;

    public PlayerCardStatus() {
        reset();
    }

    public static void reset() {
        attackDamage = 0;
        intervalLevel = 0;
        bulletsCountLevel = 0;
        bulletsSpeedLevel = 0;
        hpLevel = 0;
        moveSpeedLevel = 0;
    }

    public int getAttackDamageLevel() {
        return attackDamage;
    }
    public void attackDamageLevelUp() {
        attackDamage++;
    }

    public int getIntervalLevel() {
        return intervalLevel;
    }
    public void intervalLevelUp() {
        intervalLevel++;
    }

    public int getBulletsCountLevel() {
        return bulletsCountLevel;
    }
    public void bulletsCountLevelUp() {
        bulletsCountLevel++;
    }

    public int getBulletsSpeedLevel() {
        return bulletsSpeedLevel;
    }
    public void bulletsSpeedLevelUp() {
        bulletsSpeedLevel++;
    }

    public int getHpLevel() {
        return hpLevel;
    }
    public void hpLevelUp() {
        hpLevel++;
    }

    public int getMoveSpeedLevel() {
        return moveSpeedLevel;
    }
    public void moveSpeedLevelUp() {
        moveSpeedLevel++;
    }
}
