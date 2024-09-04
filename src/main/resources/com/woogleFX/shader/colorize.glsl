#version 330 core

in vec4 colorize;

in texture2D image;

out texture2D coloredImage;

void main() {

    for (int x = 0; x < image.width; x++) {
        for (int y = 0; y < image.height; y++) {
            coloredImage.set(x, y, 0);
        }
    }

    fragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
