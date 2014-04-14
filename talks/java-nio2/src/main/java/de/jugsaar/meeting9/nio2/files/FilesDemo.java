/*
 * Copyright 2014 Frank Dietrich <Frank.Dietrich@gmx.li>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.jugsaar.meeting9.nio2.files;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class FilesDemo {

    public static void main(String[] args) {
        try {
            // create a directory including not existing parent directories
            // cleanup
            System.out.printf("### create directory including non existing parent directories%n");
            Path subDirs = Paths.get("tmp/subdir1/subdir2");
            removeSubDirectories(subDirs);
            // the given file attributes are set for each created directory
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
            Files.createDirectories(subDirs, PosixFilePermissions.asFileAttribute(perms));
            Files.isDirectory(subDirs, LinkOption.NOFOLLOW_LINKS);

            // creare a temporary file with random name
            System.out.printf("%n### create temporary file%n");
            Path pathTempDir = Paths.get("tmp/");
            String filePrefix = "jugsaar_";
            String fileSuffix = ".tmp";
            Path pathTempFile = null;
            try {
                // file name will be
                // "tmp/jugsaar_" + Math.abs(secureRandom.nextLong()) + ".tmp"
                // see non public class: java.nio.file.TempFileHelper
                pathTempFile = Files.createTempFile(pathTempDir, filePrefix, fileSuffix);
                System.out.printf("temporary file: %s%n", pathTempFile);
            } catch (IOException ex) {
                System.err.println("failed to create temporary file in: " + pathTempDir + " - " + ex);
            }

            // unbuffered OutputStream
            // default OpenOption: CREATEE, TRUNCATE_EXISTING, WRITE
            System.out.printf("%n### write to temporary file%n");
            Charset charsetUTF8 = Charset.forName("UTF8");
            try (OutputStream os = Files.newOutputStream(pathTempFile, StandardOpenOption.TRUNCATE_EXISTING)) {
                os.write("Hello world of NIO.2\n".getBytes(charsetUTF8));
                os.write("JUG Saar meeting 9\n".getBytes(charsetUTF8));
            }

            System.out.printf("%n### read all lines from file%n");
            List<String> allLines = Files.readAllLines(pathTempFile, charsetUTF8);
            for (String line : allLines) {
                System.out.println(line);
            }

            Files.deleteIfExists(pathTempFile);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private static Path removeSubDirectories(Path subDirs) throws IOException {
        while (subDirs.getNameCount() > 1) {
            // directory must be empty
            System.out.printf("remove directory: %s%n", subDirs);
            Files.deleteIfExists(subDirs);
            subDirs = subDirs.getParent();
        }
        return subDirs;
    }
}
