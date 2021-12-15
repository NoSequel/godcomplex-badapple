package io.github.nosequel.badapple.video.playback.impl;

import io.github.nosequel.badapple.BadAppleConstants;
import io.github.nosequel.badapple.BadApplePlugin;
import io.github.nosequel.badapple.util.ColorUtil;
import io.github.nosequel.badapple.video.Frame;
import io.github.nosequel.badapple.video.PixelLocation;
import io.github.nosequel.badapple.video.Video;
import io.github.nosequel.badapple.video.playback.Playback;
import org.bukkit.*;
import org.bukkit.material.Wool;

import java.awt.Color;
import java.util.Map;

public class BlockPlayback extends Playback {
    @Override
    public void playFrames(Video video) {
        final World world = Bukkit.getWorld("flat");

        for (int x = 0; x < BadAppleConstants.VIDEO_WIDTH; x++) {
            for (int z = 0; z < BadAppleConstants.VIDEO_HEIGHT; z++) { // we're using Z instead of Y because we want to display it on the ground.
                final Location location = new Location(
                        world, x, 4, z
                );

                location.getBlock().setType(Material.LEGACY_WOOL);
            }
        }

        new Thread(() -> {
            this.setRunning(true);

            for (Frame frame : video.getFrames()) {
                for (Map.Entry<PixelLocation, Color> entry : frame.pixels().entrySet()) {
                    final PixelLocation pixelLocation = entry.getKey();
                    final Location location = new Location(world, pixelLocation.x(), 4, pixelLocation.y());
                    final DyeColor color = ColorUtil.getDyeColorByRgb(
                            org.bukkit.Color.fromRGB(
                                    entry.getValue().getRed(),
                                    entry.getValue().getGreen(),
                                    entry.getValue().getBlue()
                            ).asRGB()
                    );

                    Bukkit.getScheduler().runTask(
                            BadApplePlugin.getPlugin(BadApplePlugin.class),
                            () -> {
                                if (!location.getBlock().getType().equals(Material.LEGACY_WOOL)) {
                                    location.getBlock().setType(Material.LEGACY_WOOL);
                                }

                                final Wool wool = (Wool) location.getBlock();

                                wool.setColor(
                                        color
                                );
                            }
                    );
                }

                try {
                    Thread.sleep(20L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.setRunning(false);
        }).start();
    }
}