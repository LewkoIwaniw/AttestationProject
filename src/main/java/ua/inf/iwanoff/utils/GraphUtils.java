package ua.inf.iwanoff.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GraphUtils {
    public static Image getImage(String resourceName) {
        return new Image(Objects.requireNonNull(GraphUtils.class.getResourceAsStream(resourceName)));
    }

    public static ImageView getImageView(String resourceName) {
        return new ImageView(getImage(resourceName));
    }

}
