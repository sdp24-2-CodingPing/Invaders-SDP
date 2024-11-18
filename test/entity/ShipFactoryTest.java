package entity;

import entity.ship.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ShipFactoryTest {

    @Test
    public void testCreateStarDefender() {
        Ship ship = ShipFactory.create(Ship.ShipType.StarDefender, 100, 200);
        assertInstanceOf(StarDefender.class, ship, "Ship should be an instance of StarDefender");
    }

    @Test
    public void testCreateVoidReaper() {
        Ship ship = ShipFactory.create(Ship.ShipType.VoidReaper, 100, 200);
        assertInstanceOf(VoidReaper.class, ship, "Ship should be an instance of VoidReaper");
    }

    @Test
    public void testCreateGalacticGuardian() {
        Ship ship = ShipFactory.create(Ship.ShipType.GalacticGuardian, 100, 200);
        assertInstanceOf(GalacticGuardian.class, ship, "Ship should be an instance of GalacticGuardian");
    }

    @Test
    public void testCreateCosmicCruiser() {
        Ship ship = ShipFactory.create(Ship.ShipType.CosmicCruiser, 100, 200);
        assertInstanceOf(CosmicCruiser.class, ship, "Ship should be an instance of CosmicCruiser");
    }
}
