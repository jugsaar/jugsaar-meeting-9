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

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class ZipFSCopyOutDemo {

    public static void main(String[] args) throws IOException {
        // explanation of "Zip File System" properties
        // http://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemproviderprops.html
        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        // locate file system with java.net.JarURLConnection
        Path path = Paths.get("resources/zipfsdemo.zip");
        URI uri = URI.create("jar:file:" + path.toAbsolutePath().toString());

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path pathInZipFile = zipfs.getPath("/Thinking.jpg");
            Path externalFile = Paths.get("tmp/Duke.jpg");


            // copy a file from inside the archive to an external location
            Files.copy(pathInZipFile, externalFile);
        }
    }
}