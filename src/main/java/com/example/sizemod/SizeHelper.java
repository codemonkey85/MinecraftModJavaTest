package com.example.sizemod;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

/**
 * Shared constants and logic for adjusting player scale.
 */
public final class SizeHelper {

    public static final float MIN_SIZE = 0.1f;
    public static final float MAX_SIZE = 10.0f;
    public static final float STEP = 0.25f;

    private SizeHelper() {}

    /**
     * Adjusts the player's scale by {@code delta}, clamped to [MIN_SIZE, MAX_SIZE].
     *
     * @return the new scale value, or -1 if the attribute is unavailable
     */
    public static float adjustSize(Player player, float delta) {
        AttributeInstance scaleAttr = player.getAttribute(Attributes.SCALE);
        if (scaleAttr == null) return -1;

        float newSize = Math.clamp((float) scaleAttr.getBaseValue() + delta, MIN_SIZE, MAX_SIZE);
        scaleAttr.setBaseValue(newSize);
        return newSize;
    }

    /**
     * Sets the player's scale to an exact value, clamped to [MIN_SIZE, MAX_SIZE].
     *
     * @return the new scale value, or -1 if the attribute is unavailable
     */
    public static float setSize(Player player, float size) {
        AttributeInstance scaleAttr = player.getAttribute(Attributes.SCALE);
        if (scaleAttr == null) return -1;

        float newSize = Math.clamp(size, MIN_SIZE, MAX_SIZE);
        scaleAttr.setBaseValue(newSize);
        return newSize;
    }

    /**
     * Returns the player's current effective scale, or -1 if unavailable.
     */
    public static float getSize(Player player) {
        AttributeInstance scaleAttr = player.getAttribute(Attributes.SCALE);
        return scaleAttr != null ? (float) scaleAttr.getValue() : -1;
    }
}
