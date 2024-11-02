package entity.skill;

import entity.Entity;
import engine.DrawManager.SpriteType;

import java.awt.*;

/**
 * Abstract class representing a general game skill.
 * Defines the framework for skill creation and behavior in the game.
 *
 * @author Roberto Izquierdo Amo
 */
public abstract class Skill extends Entity {
    /** Name of the skill. */
    private final String name;
    /** Description of what the skill does. */
    private final String description;
    /** Type of sprite to represent the skill visually. */
    private final SpriteType baseSprite;
    /** Color of the skill's sprite, used for visual effects. */
    private final Color color;

    /**
     * Constructor, sets the basic properties of the skill.
     *
     * @param name          Name of the skill, displayed or used in game logic.
     * @param description   Text describing the skill's effects and uses.
     * @param positionX     Initial X position of the skill. Since it's abstract, typically set to 0.
     * @param positionY     Initial Y position of the skill. Since it's abstract, typically set to 0.
     * @param spriteType    Enum specifying which sprite to use for this skill.
     * @param color         Color of the sprite, could dictate rendering details.
     */
    protected Skill(final String name, final String description,
                    final int positionX, final int positionY,
                    final SpriteType spriteType,
                    final Color color) {
        super(0, 0, 0, 0, color);  // Calls Entity constructor with initial position and color.
        this.name = name;
        this.description = description;
        this.baseSprite = spriteType;  // Defines the sprite used for the skill.
        this.spriteType = spriteType;  // Can be adjusted for animation or effects.
        this.color = color;  // Color of the sprite, affects visual representation.
    }

    /**
     * Retrieves the name of the skill.
     *
     * @return The name of the skill.
     */
    public final String getName() {
        return name;
    }

    /**
     * Retrieves the description of the skill.
     *
     * @return The description of the skill.
     */
    public final String getDescription() {
        return description;
    }
}