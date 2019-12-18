package rbit.util;

import java.io.File;
import java.nio.file.Paths;

public class DataPath {
    public static String getPath(File file) {
        return Paths.get(new File("").getAbsolutePath()).relativize(Paths.get(file.getAbsolutePath())).toString();
    }
}