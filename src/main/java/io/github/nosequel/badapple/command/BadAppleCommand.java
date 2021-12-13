package io.github.nosequel.badapple.command;

import io.github.nosequel.badapple.BadAppleConstants;
import io.github.nosequel.badapple.video.PixelLocation;
import io.github.nosequel.badapple.video.Video;
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
    private boolean running;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Map<PixelLocation, Sheep> sheepMap = new HashMap<>();
        final World world = Bukkit.getWorld("flat");

        if (!this.running) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }

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

            video.displayOnSheep(sheepMap);
            this.running = true;
        }

        if (sender instanceof Player player) {
            player.teleport(new Location(world, 0, 35, 0));
        }

        return false;
    }
}