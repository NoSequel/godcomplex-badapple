package io.github.nosequel.badapple.command;

import io.github.nosequel.badapple.BadAppleConstants;
import io.github.nosequel.badapple.video.PixelLocation;
import io.github.nosequel.badapple.video.Video;
import io.github.nosequel.badapple.video.playback.Playback;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BadAppleCommand implements CommandExecutor {

    private final Video video;
    private final Playback playback;

    private boolean running;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final World world = Bukkit.getWorld("flat");

        if (!this.running) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }

            this.playback.playFrames(video);
            this.running = true;
        }

        if (sender instanceof Player player) {
            player.teleport(new Location(world, 0, 35, 0));
        }

        return false;
    }
}