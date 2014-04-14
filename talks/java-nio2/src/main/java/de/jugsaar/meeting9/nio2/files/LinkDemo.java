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
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class LinkDemo {

    public static void main(String[] args) {
        Path target = Paths.get("resources/lorem-ipsum.txt");
        Path hardLink = Paths.get("tmp/lorem-ipsum.hardlink");
        Path symLink = Paths.get("tmp/lorem-ipsum.symlink");
        try {
            Files.deleteIfExists(hardLink);
            Files.deleteIfExists(symLink);

            // the equivalent UNIX command to create
            // a hard link 'tmp/lorem-ipsum.softlink'
            // pointing to target 'resources/lorem-ipsum.txt'
            // would be
            // 'ln resources/lorem-ipsum.txt tmp/lorem-ipsum.softlink'
            Files.createLink(hardLink, target);
            System.out.printf("### hardlink%n");
            showPathInformation(hardLink);
            System.out.printf("%s%n", Files.isSameFile(target, hardLink));

            System.out.printf("%n### symbolic link%n");
            // following create the symlink not as expected
            // the created symlink 'tmp/lorem-ipsum.symlink' would point to
            // target 'resources/lorem-ipsum.txt' relative to the link directory
            // i.e. to 'tmp/resources/lorem-ipsum.txt'
            // Files.createSymbolicLink(symLink, target);

            // solution: using an absolute path
            // assuming the current directory is be '/home/user' the following
            // creates the symlink '/home/user/foo/tmp/lorem-ipsum.symlink' pointing to
            // the target using the absolute path '/home/user/foo/resources/lorem-ipsum.txt'
            //
            // this has the downside that the current directory would not anymore be relocatable:
            // moving '/home/user/foo' to '/home/user/bar' lead in a broken link
            // '/home/user/bar/tmp/lorem-ipsum.symlink' as the symlink would still point to target
            // '/home/user/foo/resources/lorem-ipsum.txt'
            // Files.createSymbolicLink(symLink, target.toAbsolutePath());


            // solution: using a relative path to the target
            // following creates a symlink pointing with a relative path to the target
            // the equivalent UNIX command to create a symlink 'tmp/lorem-ipsum.symlink'
            // pointing to target 'resources/lorem-ipsum.txt' is
            // 'ln -s ../resources/lorem-ipsum.txt tmp/lorem-ipsum.softlink'
            Path relPath = symLink.getParent().relativize(target);
            System.out.printf("%n### relative path to the symbolic link target%n");
            showPathInformation(relPath);
            Files.createSymbolicLink(symLink, relPath);
            showPathInformation(symLink);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private static void showPathInformation(Path path) throws IOException {
        String outFormat = "%-30s: %s%n";
        System.out.printf(outFormat, "path", path);
        System.out.printf(outFormat, "toAbsolutePath", path.toAbsolutePath());
        if (Files.exists(path)) {
            // toRealPath() works only on existing files
            System.out.printf(outFormat, "toRealPath", path.toRealPath());
            System.out.printf(outFormat, "toRealPath (NOFOLLOW_LINKS)", path.toRealPath(NOFOLLOW_LINKS));
        }
        System.out.printf(outFormat, "isSymbolicLink", Files.isSymbolicLink(path));
        System.out.printf(outFormat, "isRegularFile", Files.isRegularFile(path));
        System.out.printf(outFormat, "isRegularFile (NOFOLLOW_LINKS)", Files.isRegularFile(path, NOFOLLOW_LINKS));
    }
}
