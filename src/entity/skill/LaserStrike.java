package entity.skill;

import engine.DrawManager;
import java.awt.*;

/**
 * Represents the Laser Strike skill, a powerful offensive ability in the game. This class extends
 * the Skill class to implement the specific behavior of the Laser Strike. The Laser Strike emits a
 * vertical beam that destroys enemies it touches.
 *
 * <p>This skill is typically used by player or special enemy entities to deal damage in a vertical
 * line.
 */
public class LaserStrike extends Skill {

  /**
   * Constructor for Laser Strike skill. Initializes a new instance of the Laser Strike with
   * predefined properties.
   */
  public LaserStrike() {
    // Call to the superclass (Skill) constructor with the specific attributes of the Laser Strike.
    super(
        "Laser Beam", // Name of the skill
        "Fires a powerful vertical laser beam that annihilates enemies in its path.", // Description
        // of the
        // skill
        0, // Initial X position (not used since position is generally set in gameplay logic)
        0, // Initial Y position (not used as above)
        DrawManager.SpriteType
            .EnemyShipE1, // Sprite type for the skill, assuming a vertical beam appearance
        Color.WHITE); // Color of the beam, set to white for visibility
  }
}
