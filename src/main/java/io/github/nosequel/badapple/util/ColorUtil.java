package io.github.nosequel.badapple.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ColorUtil {

    private final Map<Integer, DyeColor> CACHED_COLORS = new HashMap<>();

    public DyeColor getDyeColorByRgb(int rgb) {
        if (CACHED_COLORS.containsKey(rgb)) {
            return CACHED_COLORS.get(rgb);
        }

        int distance = Integer.MAX_VALUE;

        final Color color = Color.fromRGB(rgb);

        final int red = color.getRed();
        final int green = color.getGreen();
        final int blue = color.getBlue();

        DyeColor closest = DyeColor.WHITE;

        for (DyeColor value : DyeColor.values()) {
            final Color color1 = value.getColor();
            final int dist = Math.abs(color1.getRed() - red) +
                    Math.abs(color1.getGreen() - green) +
                    Math.abs(color1.getBlue() - blue);

            if (dist < distance) {
                distance = dist;
                closest = value;
            }
        }

        CACHED_COLORS.put(rgb, closest);

        return closest;
    }
}