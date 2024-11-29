package entity;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import entity.player.PlayerShip;
import entity.ship.*;
import org.junit.jupiter.api.Test;

public class ShipFactoryTest {

  @Test
  public void testCreateStarDefender() {
    PlayerShip ship = ShipFactory.create(PlayerShip.ShipType.StarDefender, 100, 200);
    assertInstanceOf(StarDefender.class, ship, "Ship should be an instance of StarDefender");
  }

  @Test
  public void testCreateVoidReaper() {
    PlayerShip ship = ShipFactory.create(PlayerShip.ShipType.VoidReaper, 100, 200);
    assertInstanceOf(VoidReaper.class, ship, "Ship should be an instance of VoidReaper");
  }

  @Test
  public void testCreateGalacticGuardian() {
    PlayerShip ship = ShipFactory.create(PlayerShip.ShipType.GalacticGuardian, 100, 200);
    assertInstanceOf(
        GalacticGuardian.class, ship, "Ship should be an instance of GalacticGuardian");
  }

  @Test
  public void testCreateCosmicCruiser() {
    PlayerShip ship = ShipFactory.create(PlayerShip.ShipType.CosmicCruiser, 100, 200);
    assertInstanceOf(CosmicCruiser.class, ship, "Ship should be an instance of CosmicCruiser");
  }
}
