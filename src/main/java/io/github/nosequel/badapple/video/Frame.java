package io.github.nosequel.badapple.video;

import java.awt.*;
import java.util.Map;

public record Frame(
        Map<PixelLocation, Color> pixels
) {
}
