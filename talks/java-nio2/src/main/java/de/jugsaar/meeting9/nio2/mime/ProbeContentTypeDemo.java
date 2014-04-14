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
package de.jugsaar.meeting9.nio2.mime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
public class ProbeContentTypeDemo {

    public static void main(String[] args) {
        String[] pathNames = new String[]{"resources/zipfsdemo.zip",
            "target/classes/de/jugsaar/meeting9/nio2/mime/ProbeContentTypeDemo.class",
            "resources/NonClass.class"
        };

        for (String pathName : pathNames) {
            try {
                // As the content type is determind by the platform's default file type detector,
                // it might be fooled.
                // For example, if the detector determines a file's content type to be
                // 'application/x-java' only based on the '.class' extension.
                String mimeType = Files.probeContentType(Paths.get(pathName));
                System.out.printf("%s: %s%n", pathName, mimeType);
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
