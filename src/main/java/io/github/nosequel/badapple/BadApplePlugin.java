package io.github.nosequel.badapple;

import io.github.nosequel.badapple.command.BadAppleCommand;
import io.github.nosequel.badapple.video.Video;
import io.github.nosequel.badapple.video.playback.impl.SheepPlayback;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BadApplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getWorld("flat") == null) {
            Bukkit
                    .createWorld(new WorldCreator("flat")
                    .type(WorldType.FLAT));

        }

        final Video badApple = new Video(
                this.getDataFolder().getAbsolutePath() + File.separator + "video" + File.separator
        );

        this
                .getCommand("badapple")
                .setExecutor(new BadAppleCommand(badApple, new SheepPlayback()));
    }
}