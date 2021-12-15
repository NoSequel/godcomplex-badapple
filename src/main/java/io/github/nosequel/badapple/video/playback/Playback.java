package io.github.nosequel.badapple.video.playback;

import io.github.nosequel.badapple.video.Video;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Playback {

    private boolean running = false;

    /**
     * Play the frames of a {@link Video} object.
     * <p>
     * This method generally sets the {@link Playback#running} field to `true`,
     * and sets it to `false` whenever the video playback is over.
     *
     * @param video the video to play the frames of
     */
    public abstract void playFrames(Video video);

}
