# Instructions
* Drop the plugin into the /plugins folder
* Create a `GodComplex-BadApple/video` folder, inside of there put the contents of the `split-zip.zip` and `split-zip-2.zip` file on the repository.

# NOTE
* There are approximately 6000 `.png` files within the `split-zip.zip` and `split-zip-2.zip` archives combined, these are split using ffmpeg.
* There are 2 `split-zip` archives to bypass github's 100 MB file limit (LFS didn't work, guess github hates me.)

This plugin supports more than just the Bad Apple music clip. It should support most videos, I personally recommend using a 360p video, since that's the native resolution. I've made a method to get the nearest color (by checking using the RGB values of the DyeColor and compare it with the provided RGB color which got taken from the frame's pixel). This method is not very lightweight (O(n)), so I decided to cache the colors in a Map<Integer, DyeColor>, with the key being the HEX value of the color. 