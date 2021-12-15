package io.github.nosequel.badapple.command;

import io.github.nosequel.badapple.video.Video;
import io.github.nosequel.badapple.video.playback.Playback;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public record BadAppleCommand(
        Video video,
        Playback playback
) implements CommandExecutor {

    // really wish I could just pass a pointer to a reference in memory into a method so I could just
    // change this running value within the playFrames method, and have it declared in here. I simply do not need to check
    // it anywhere else, but it's whatever I guess.
    // private boolean running;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final World world = Bukkit.getWorld("flat");

        if (!playback.isRunning()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }

            this.playback.playFrames(video);
        }

        if (sender instanceof Player player) {
            player.teleport(new Location(world, 0, 35, 0));
        }

        return false;
    }
}