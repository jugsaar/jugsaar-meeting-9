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

package de.jugsaar.meeting9.nio2.path;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class PathDemo {

    private static File file;
    private static Path path;

    private static void show(String section) {
        System.out.printf("%n### %s ###%n", section);
        System.out.printf("file: %s%n", file);
        System.out.printf("path: %s%n", path);
    }

    public static void main(String[] args) throws IOException {

        path = FileSystems.getDefault().getPath("resources/dir1/../dir3", "file3");
        System.out.println(path);
        System.out.println(path.getParent());
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toRealPath());
        System.out.println(path.toRealPath(LinkOption.NOFOLLOW_LINKS));

        String absolutePath = "/tmp/jug-saarland";
        file = new File(absolutePath);
        path = Paths.get(absolutePath);
        show("absolute path");

        String shortcutPath = "/tmp/meetup/../jug-saarland";
        file = new File(shortcutPath);
        path = Paths.get(shortcutPath);
        show("shortcut path");

        file = file.getCanonicalFile();
        path = path.normalize();
        show("normalized path");

        System.out.println(path.getFileName());
        System.out.println(path.getFileSystem());
        System.out.println(path.getName(0));
        System.out.println(path.getNameCount());
        System.out.println(path.getParent());
        System.out.println(path.getRoot());
    }
}
