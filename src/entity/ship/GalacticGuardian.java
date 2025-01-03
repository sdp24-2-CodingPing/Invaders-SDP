package entity.ship;

import engine.DrawManager;
import entity.ShipMultipliers;
import entity.player.PlayerShip;

/**
 * Stronger ship with slower speed. It moves slower than the default ship, but its bullets are
 * stronger.
 */
public class GalacticGuardian extends PlayerShip {
  public GalacticGuardian(final int positionX, final int positionY) {
    super(
        positionX,
        positionY,
        "Galactic Guardian",
        new ShipMultipliers(0.8f, 1.5f, 1.2f, 1.5f),
        DrawManager.SpriteType.Ship3);
  }
}
