package pl.b2b.net.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileUtils {
    private static final String RESOURCES_TMP_DIR = "webdriver-resources";
    private static final String TMP_DIR_PROPERTY = "java.io.tmpdir";

    public static String getResourceFilePath(String fileName) throws IOException {
        Path resourcePath = getTempResourcePath(fileName);
        if (Files.notExists(resourcePath)) {
            return unpackResourceFile(fileName).toString();
        }
        return resourcePath.toString();
    }

    static InputStream getResourceFile(String resource) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();

        return classLoader.getResourceAsStream(resource);
    }

    private static Path unpackResourceFile(String resource) throws IOException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(resource);

        if (!Files.exists(getResourcesTempDir())) {
            getResourcesTempDir().toFile().mkdirs();
        }

        Path targetPath = getTempResourcePath(resource);
        Files.copy(inputStream, targetPath);
        return targetPath;
    }

    public static boolean cleanExecutablesTmpDir() {
        try {
            Files.walk(getResourcesTempDir())
                    .sorted(Comparator.reverseOrder())
                    .forEach(FileUtils::delete);
            return true;
        } catch (IOException e) {
            // add logger
            return false;
        }
    }

    private static void delete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getTempResourcePath(String resource) {
        return Paths.get(getResourcesTempDir().toString(), resource);
    }

    private static Path getResourcesTempDir() {
        String tmpDirPath = System.getProperty(TMP_DIR_PROPERTY);
        return Paths.get(tmpDirPath, RESOURCES_TMP_DIR);
    }

    static String getStringFromInputStream(InputStream is, int LineNumber) {

        BufferedReader br = null;
        String FinalLine = "", line;
        try {

            br = new BufferedReader(new InputStreamReader(is));

            for (int i = 1; i <= LineNumber; i++) {
                if ((line = br.readLine()) != null) {
                    FinalLine = line;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return FinalLine;
    }
}