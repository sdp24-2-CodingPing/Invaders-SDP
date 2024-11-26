package entity.ship;

import engine.DrawManager;
import entity.player.PlayerShip;
import entity.ShipMultipliers;

/**
 * Faster ship with weaker bullets.
 * It moves faster than the default ship, but its bullets are weaker.
 */
public class VoidReaper extends PlayerShip {
    public VoidReaper(final int positionX, final int positionY) {
        super(positionX, positionY,
                "Void Reaper", new ShipMultipliers(1.4f, 1.2f, 0.4f, 0.5f),
                DrawManager.SpriteType.Ship2);
    }
}
