package ua.inf.iwanoff.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
    public static String createTempFromJar(JarResources jar, String fileName) {
        try {
            File tempFile = File.createTempFile(fileName, "");
            tempFile.deleteOnExit();
            byte[] buffer = jar.getResource(fileName);
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
}
