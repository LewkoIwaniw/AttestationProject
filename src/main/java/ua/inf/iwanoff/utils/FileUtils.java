package ua.inf.iwanoff.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

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

    public static byte[] getBytes(String resourceName)  {
        // 1. Отримуємо потік з теки resources
        // Початковий "/" означає "шукати від кореня теки resources"
        try (var is = GraphUtils.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new RuntimeException("Файл не знайдено в ресурсах!");
            }

            // 2. Читаємо всі байти в масив
            return is.readAllBytes();
        }
        catch (Exception _) {
            throw new RuntimeException();
        }
    }
}
