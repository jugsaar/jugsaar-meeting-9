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

package de.jugsaar.meeting9.nio2.directorystream;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class DirectoryStreamNoFilterDemo {

    public static void main(String[] args) {

        // since 1.2
        System.out.printf("### iterate using File class%n");
        File dir = new File("resources/");
        // possible NPE in case the directory does not exist
        if (dir != null) {
            for (File entry : dir.listFiles()) {
                if (entry.isDirectory()) {
                    System.out.printf("%-4s: %s%n", "dir", entry.getName());
                } else if (entry.isFile()) {
                    System.out.printf("%-4s: %s%n", "file", entry.getName());
                } else {
                    System.out.printf("%-4s: %s%n", "xxxx", entry.getName());
                }
            }
        }

        // since 1.7
        System.out.printf("%n### iterate using DirectoryStream%n");
        Path path = Paths.get("resources/");
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
            for (Path entry : dirStream) {
                if (Files.isDirectory(entry, NOFOLLOW_LINKS)) {
                    System.out.printf("%-4s: %s%n", "dir", entry.getFileName());
                } else if (Files.isRegularFile(entry, NOFOLLOW_LINKS)) {
                    System.out.printf("%-4s: %s%n", "file", entry.getFileName());
                } else if (Files.isSymbolicLink(entry)) {
                    System.out.printf("%-4s: %s%n", "link", entry.getFileName());
                } else {
                    System.out.printf("%-4s: %s%n", "xxxx", entry.getFileName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
