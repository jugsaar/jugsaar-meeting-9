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

package de.jugsaar.meeting9.nio2.filestore;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;

/**
 * 
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class FileStoreDemo {

    static final ArrayList<Class> attrViews = new ArrayList<>();

    // initialise all implementations of FileAttributeView
    static {
        attrViews.add(AclFileAttributeView.class);
        attrViews.add(BasicFileAttributeView.class);
        attrViews.add(DosFileAttributeView.class);
        attrViews.add(FileOwnerAttributeView.class);
        attrViews.add(PosixFileAttributeView.class);
        attrViews.add(UserDefinedFileAttributeView.class);
    }

    public static void main(String[] args) {

        System.out.printf("### show file stores of default file system");
        String outFormat = "filestore: %s%n";
        FileStore fileStore;
        for (FileStore fs : FileSystems.getDefault().getFileStores()) {
            System.out.printf(outFormat, fs);
        }

        System.out.printf("%n### show file store informations for a given path%n");
        Path path = Paths.get("resources/");
        try {
            fileStore = Files.getFileStore(path);

            outFormat = "%-20s: %s%n";
            System.out.printf(outFormat, "implementation class", fileStore.getClass().getName());
            System.out.printf(outFormat, "name (device)", fileStore.name());
            System.out.printf(outFormat, "type (file system)", fileStore.type());
            // there is no method implemented to get the mount point directly
            // beside using reflection to make method 'UnixMountEntry findMountEntry()' accessible
            // it can be cutted from 'toString()' fileStore.toString().replaceAll(" \\(.*\\)$", "")
            System.out.printf(outFormat, "toString", fileStore.toString());
            System.out.printf(outFormat, "isReadOnly", fileStore.isReadOnly());

            outFormat = "%-20s: %,d%n";
            System.out.printf(outFormat, "getTotalSpace", fileStore.getTotalSpace());
            System.out.printf(outFormat, "getUnallocatedSpace", fileStore.getUnallocatedSpace());
            System.out.printf(outFormat, "getUsableSpace", fileStore.getUsableSpace());

            System.out.printf("%n### supported FileAttributeViews%n");
            outFormat = "%-52s: supported %s%n";
            for (Class attrView : attrViews) {
                boolean supported = fileStore.supportsFileAttributeView(attrView);
                System.err.printf(outFormat, attrView.getName(), supported);
            }
            boolean supported = fileStore.supportsFileAttributeView("posix");
            System.out.println(fileStore.name() + " ---" + supported);

        } catch (IOException ex) {
            System.err.printf("failed to get file store of: %s - %s%n", path, ex);
        }
    }
}
