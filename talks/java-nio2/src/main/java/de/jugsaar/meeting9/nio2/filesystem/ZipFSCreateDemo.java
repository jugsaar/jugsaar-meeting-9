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

package de.jugsaar.meeting9.nio2.filesystem;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class ZipFSCreateDemo {

    public static void main(String [] args) throws Throwable {
        // explanation of "Zip File System" properties
        // http://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemproviderprops.html
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        // locate file system by using the syntax
        // defined in java.net.JarURLConnection
        Path path = Paths.get("tmp/zipfsdemo-create.zip");
        URI uri = URI.create("jar:file:" + path.toAbsolutePath().toString());

       try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path externalFile = Paths.get("resources/lorem-ipsum.txt");
            Path pathInZipFile = zipfs.getPath("/SomeTextFile.txt");

            // copy an external file into the zip archive
            Files.copy(externalFile, pathInZipFile, REPLACE_EXISTING);
        }
    }
}
