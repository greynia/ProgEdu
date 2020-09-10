package fcu.selab.progedu.project;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JavacAssignmentTest {

    @Test
    public void searchJavaFile() {
        JavacAssignment javacAssignment = new JavacAssignment();
        Path target = Paths.get(System.getProperty("java.io.tmpdir"), "test-searchJavaFile-test");
        javacAssignment.searchJavaFile(target.toString()); // just show the command txt
    }

}