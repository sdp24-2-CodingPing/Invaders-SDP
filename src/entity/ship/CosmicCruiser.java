package entity.ship;

import engine.DrawManager;
import entity.ShipMultipliers;
import entity.player.PlayerShip;

/**
 * Slow ship with very strong bullets. It moves slower than the default ship, but its bullets are a
 * lot stronger.
 */
public class CosmicCruiser extends PlayerShip {
  public CosmicCruiser(final int positionX, final int positionY) {
    super(
        positionX,
        positionY,
        "Cosmic Cruiser",
        new ShipMultipliers(0.8f, 2f, 1.6f, 2f),
        DrawManager.SpriteType.Ship4);
  }
}
