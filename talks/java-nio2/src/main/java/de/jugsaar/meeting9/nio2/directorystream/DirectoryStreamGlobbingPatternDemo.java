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
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class DirectoryStreamGlobbingPatternDemo {

    public static void main(String[] args) {

        // since 1.2
        System.out.printf("### using simple file name filtering%n");
        File dir = new File("resources/");
        for (File entry : dir.listFiles()) {
            // should not be used that way
            if (entry.getName().endsWith(".zip") || entry.getName().endsWith(".txt")) {
                System.out.printf("%-5s: %s%n", "entry", entry.getName());
            }
        }

        System.out.printf("%n### using a FileFilter%n");
        FileFilter globbingFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".zip") || pathname.getName().endsWith(".txt");
            }
        };
        if (dir != null) {
            for (File entry : dir.listFiles(globbingFilter)) {
                System.out.printf("%-5s: %s%n", "entry", entry.getName());
            }
        }

        // since 1.7
        System.out.printf("%n### using DirectoryStream with globbing pattern%n");
        Path path = Paths.get("resources/");
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path, "*.{zip,txt}")) {
            for (Path entry : dirStream) {
                System.out.printf("%-5s: %s%n", "entry", entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
