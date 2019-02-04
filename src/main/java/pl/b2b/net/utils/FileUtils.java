package pl.b2b.net.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

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

        String filePassString = resourcePath.toString();
        System.out.println("Looking for a file " + filePassString);

        if (Files.notExists(resourcePath)) {
            System.out.println(String.format("File %s wasn't found. Starting copying file", filePassString));

            return unpackResourceFile(fileName).toString();
        }

        System.out.println(String.format("File %s was found. No actions performed for creating it", filePassString));

        return filePassString;
    }

    static InputStream getResourceFile(String resource) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();

        return classLoader.getResourceAsStream(resource);
    }

    private static Path unpackResourceFile(String resource) throws IOException {
        System.out.println("Starting unpacking resource file " + resource);
        ClassLoader classLoader = FileUtils.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(resource);

        Path resourcesTempDirPath = getResourcesTempDir();

        System.out.println(resourcesTempDirPath + " - checking whether temp directory exists");

        if (!Files.exists(resourcesTempDirPath)) {
            System.out.println(String.format("Directory %s wasn't found. Starting creation", resourcesTempDirPath));

            resourcesTempDirPath.toFile().mkdirs();
        }

        System.out.println(resourcesTempDirPath + " - temp directory was found.");

        Path targetPath = getTempResourcePath(resource);

        System.out.println(String.format("Starting copying resource file %s to directory %s", resource, targetPath));
        Files.copy(inputStream, targetPath);

        System.out.println(String.format("File %s was copied to directory %s", resource, targetPath));

        return targetPath;
    }

    public static boolean cleanExecutablesTmpDir() {
        try {
            Path resourcesTempDir = getResourcesTempDir();
            System.out.println("Starting cleaning folder " + resourcesTempDir);
            if (Files.exists(resourcesTempDir)) {
                System.out.println(String.format("Path %s exists. Starting cleanup", resourcesTempDir));
                Files.walk(resourcesTempDir)
                        .sorted(Comparator.reverseOrder())
                        .forEach(FileUtils::delete);
            }
            System.out.println("Folder cleanup finished : " + resourcesTempDir);
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred during resource temp dir clean up " + e.getMessage());
            return false;
        }
    }

    private static void delete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println("Something went wrong during file deletion. Execution would be continued ignoring exception :" + ExceptionUtils.getStackTrace(e));
        }
    }

    private static Path getTempResourcePath(String resource) {
        return Paths.get(getResourcesTempDir().toString(), resource);
    }

    private static Path getResourcesTempDir() {
        String tmpDirPath = System.getProperty(TMP_DIR_PROPERTY);

        System.out.println("Actual system temp dir pass = " + tmpDirPath);

//        //TODO temporary solution
//        tmpDirPath = "c:\\tf\\tmp\\";

        System.out.println("Used temp dir pass = " + tmpDirPath);

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
            System.out.println("Something went wrong during reading file" + ExceptionUtils.getStackTrace(e));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Something went wrong during closing file" + ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return FinalLine;
    }
}