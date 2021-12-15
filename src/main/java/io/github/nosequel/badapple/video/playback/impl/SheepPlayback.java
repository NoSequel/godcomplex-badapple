package io.github.nosequel.badapple.video.playback.impl;

import io.github.nosequel.badapple.BadAppleConstants;
import io.github.nosequel.badapple.BadApplePlugin;
import io.github.nosequel.badapple.util.ColorUtil;
import io.github.nosequel.badapple.video.Frame;
import io.github.nosequel.badapple.video.PixelLocation;
import io.github.nosequel.badapple.video.Video;
import io.github.nosequel.badapple.video.playback.Playback;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SheepPlayback extends Playback {
    @Override
    public void playFrames(Video video) {
        final World world = Bukkit.getWorld("flat");
        final Map<PixelLocation, Sheep> sheepMap = new HashMap<>();

        for (int x = 0; x < BadAppleConstants.VIDEO_WIDTH; x++) {
            for (int z = 0; z < BadAppleConstants.VIDEO_HEIGHT; z++) { // we're using Z instead of Y because we want to display it on the ground.
                final Location location = new Location(world, x, 4, z);
                final Sheep sheep = (Sheep) world.spawnEntity(location, EntityType.SHEEP);

                sheep.setAI(false);

                sheepMap.put(
                        new PixelLocation(x, z),
                        sheep
                );
            }
        }

        new Thread(() -> {
            this.setRunning(true);

            for (Frame frame : video.getFrames()) {
                for (Map.Entry<PixelLocation, Color> entry : frame.pixels().entrySet()) {
                    final Sheep entity = sheepMap.get(entry.getKey());

                    final DyeColor color = ColorUtil.getDyeColorByRgb(
                            org.bukkit.Color.fromRGB(
                                    entry.getValue().getRed(),
                                    entry.getValue().getGreen(),
                                    entry.getValue().getBlue()
                            ).asRGB()
                    );

                    Bukkit.getScheduler().runTask(
                            BadApplePlugin.getPlugin(BadApplePlugin.class),
                            () -> entity.setColor(color)
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