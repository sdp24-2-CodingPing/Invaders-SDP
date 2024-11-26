package entity.ship;

import engine.DrawManager;
import entity.player.PlayerShip;
import entity.ShipMultipliers;

/**
 * Default ship controlled by the player.
 * It does not have any special abilities, nor any special properties.
 */
public class StarDefender extends PlayerShip {
    public StarDefender(final int positionX, final int positionY) {
        super(positionX, positionY,
                "Star Defender", new ShipMultipliers(1, 1, 1, 1),
                DrawManager.SpriteType.Ship);
    }
}
