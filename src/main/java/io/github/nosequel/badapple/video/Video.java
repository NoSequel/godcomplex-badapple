package io.github.nosequel.badapple.video;

import io.github.nosequel.badapple.BadAppleConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.*;

@Setter
@Getter
public class Video {

    private final List<Frame> frames;
    private Thread thread;

    @SneakyThrows
    public Video(String frameDirectory) {
        final File videoDirectory = new File(frameDirectory);

        if (!videoDirectory.exists() || !videoDirectory.isDirectory()) {
            throw new RuntimeException(
                    "Video does not exist or is not a directory. Provided path is " + frameDirectory
            );
        }

        final List<Frame> frames = new ArrayList<>();
        final List<File> files = new ArrayList<>(
                List.of(videoDirectory.listFiles())
        );

        files.sort(
                Comparator.comparing(File::getName)
        );

        // this can be recoded using a framebuffer and just splitting the images like that,
        // but that would suck to scale, therefore i'm looping through the pre-split frames and
        // just resizing them using the resizeImage method within the Video class.
        // the video was split using ffmpeg, hopefully this is allowed! if not, I can recode this using the
        // before mentioned framebuffer method.
        for (File file : files) {
            final Map<PixelLocation, Color> pixels = new HashMap<>();

            if (file.getName().endsWith(".png")) {
                final BufferedImage bufferedImage = this.resizeImage(
                        ImageIO.read(file)
                );

                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        pixels.put(
                                new PixelLocation(x, y),
                                new Color(bufferedImage.getRGB(x, y))
                        );
                    }
                }


                frames.add(new Frame(pixels));
            }
        }

        this.frames = Collections.unmodifiableList(frames);
    }

    @SneakyThrows
    private BufferedImage resizeImage(BufferedImage originalImage) {
        final BufferedImage resizedImage = new BufferedImage(
                BadAppleConstants.VIDEO_WIDTH, BadAppleConstants.VIDEO_HEIGHT, BufferedImage.TYPE_INT_RGB
        );

        final Graphics2D graphics2D = resizedImage.createGraphics();

        graphics2D.drawImage(
                originalImage,
                0,
                0,
                BadAppleConstants.VIDEO_WIDTH,
                BadAppleConstants.VIDEO_HEIGHT,
                null
        );

        graphics2D.dispose();

        return resizedImage;
    }
}