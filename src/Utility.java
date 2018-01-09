import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utility {

    public static void createFolder(String link) {
        try {
            Path path = Paths.get(link);
            System.out.println(Files.exists(path));
            if (!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFile(String link) {
        File file = new File(link);
        return file;
    }
}
