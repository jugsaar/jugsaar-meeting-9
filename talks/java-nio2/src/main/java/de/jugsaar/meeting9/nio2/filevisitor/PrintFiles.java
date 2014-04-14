// example origin
// URL: http://docs.oracle.com/javase/tutorial/essential/io/walk.html

package de.jugsaar.meeting9.nio2.filevisitor;

/**
 *
 * @author Frank Dietrich <Frank.Dietrich@gmx.li>
 */
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PrintFiles
        extends SimpleFileVisitor<Path> {

    // Print information about each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
            BasicFileAttributes attr) {
        String outFormat = "%-14s: %s";
        if (attr.isSymbolicLink()) {
            System.out.printf(outFormat, "Symbolic link", file);
        } else if (attr.isRegularFile()) {
            System.out.printf(outFormat, "Regular file", file);
        } else {
            System.out.printf(outFormat, "Other", file);
        }
        System.out.printf(" (%,d bytes)%n", attr.size());
        return CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
        String outFormat = "%-14s: %s%n";
        System.out.printf(outFormat, "Directory", dir);
        return CONTINUE;
    }

    // If there is some error accessing the file, let the user know.
    // If you don't override this method and an error occurs, an IOException
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}
