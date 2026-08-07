package ua.inf.iwanoff.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Utility class for handling graphical assets and JavaFX images[cite: 9].
 */
public class GraphUtils {
    
    /**
     * Loads an Image from the specified resource name[cite: 9].
     * 
     * @param resourceName the name of the resource file[cite: 9]
     * @return the loaded Image object[cite: 9]
     */
    public static Image getImage(String resourceName) {
        return new Image(Objects.requireNonNull(GraphUtils.class.getResourceAsStream(resourceName)));
    }

    /**
     * Creates an ImageView containing the image loaded from the specified resource name[cite: 9].
     * 
     * @param resourceName the name of the resource file[cite: 9]
     * @return the ImageView object containing the image[cite: 9]
     */
    public static ImageView getImageView(String resourceName) {
        return new ImageView(getImage(resourceName));
    }

}