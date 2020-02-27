/*
 * Copyright (c) 2020.
 */

package rs.alexradul.haiku.springinitializr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

public class Zip {

    public static void extractAll(InputStream inputStream, File destDir) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry = zin.getNextEntry();
            while (zipEntry != null) {
                processEntry(destDir, zin, zipEntry);
                zipEntry = zin.getNextEntry();
            }
        }
    }

    private static void processEntry(File destDir, ZipInputStream zin, ZipEntry zipEntry) throws IOException {
        if (zipEntry.isDirectory()) {
            createDirectory(destDir, zipEntry);
        } else {
            unpack(zin, newFile(destDir, zipEntry));
        }
    }

    private static void createDirectory(File destDir, ZipEntry zipEntry) throws IOException {
        Files.createDirectories(Path.of(destDir.getCanonicalPath(), zipEntry.getName()));
    }

    private static File newFile(File destDir, ZipEntry zipEntry) {
        return new File(destDir, zipEntry.getName());
    }

    private static void unpack(ZipInputStream zin, File file) throws IOException {
        writeByteArrayToFile(file, zin.readAllBytes());
    }
}
