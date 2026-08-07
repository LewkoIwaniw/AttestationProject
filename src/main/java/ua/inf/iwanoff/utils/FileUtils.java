package ua.inf.iwanoff.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Utility class for file-related operations such as resource handling and temporary file creation[cite: 8].
 */
public class FileUtils {

    /**
     * Creates a temporary file from resource contents[cite: 8].
     * 
     * @param fileName the name of the resource file[cite: 8]
     * @return the absolute path of the temporary file, or null if an error occurs[cite: 8]
     */
    public static String createTempFromResources(String fileName) {
        try {
            File tempFile = File.createTempFile(fileName, "");
            tempFile.deleteOnExit();
            byte[] buffer = getBytes(fileName);
            try (OutputStream output = new FileOutputStream(tempFile)) {
                output.write(buffer, 0, buffer.length);
            }
            catch (IOException e) {
                return  null;
            }
            return tempFile.getAbsolutePath();
        }
        catch (IOException e) {
            return null;
        }
    }

    /**
     * Reads all bytes from a given resource name[cite: 8].
     * 
     * @param resourceName the name of the resource[cite: 8]
     * @return a byte array containing the resource data[cite: 8]
     */
    public static byte[] getBytes(String resourceName)  {
        // 1. Get the stream from the resources folder
        // The leading "/" means "search from the root of the resources folder"
        try (var is = GraphUtils.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new RuntimeException("File not found in resources!");
            }

            // 2. Read all bytes into an array
            return is.readAllBytes();
        }
        catch (Exception _) {
            throw new RuntimeException();
        }
    }
}