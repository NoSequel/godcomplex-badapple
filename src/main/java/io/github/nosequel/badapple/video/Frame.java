package io.github.nosequel.badapple.video;

import java.util.Map;

public record Frame(
        Map<PixelLocation, Integer> pixels
) {
}
