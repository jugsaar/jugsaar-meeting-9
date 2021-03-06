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

package de.jugsaar.meeting9.nio2.filevisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class WalkFileTreeDemo {

    public static void main(String[] args) {
        Path startingDir = Paths.get("resources/");
        PrintFiles pf = new PrintFiles();
        try {
            // initiate the walking process
            // don't follow symbolic links
            Files.walkFileTree(startingDir, pf);

            // follow symbolic links
//            EnumSet<FileVisitOption> opts = EnumSet.of(FOLLOW_LINKS);
//            Files.walkFileTree(startingDir, opts, Integer.MAX_VALUE, pf);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
