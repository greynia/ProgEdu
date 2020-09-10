package fcu.selab.progedu.conn;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TomcatServiceTest {

    @Test
    public void deleteDirectory() { // also test createNewDirectory()
        TomcatService tomcatService = TomcatService.getInstance();

        Path target = Paths.get(System.getProperty("java.io.tmpdir"), "test-deleteDirectory-for-unit-test");
//      If in Windows: System.getProperty("java.io.tmpdir") is C:\\Users\\users\\AppData\\Local\\Temp

        File willDeleteDir = target.toFile();
        tomcatService.createNewDirectory(willDeleteDir);
        tomcatService.createNewFile(Paths.get(willDeleteDir.getPath(), "file_1").toFile());

        assertTrue(tomcatService.deleteDirectory(willDeleteDir));
    }

    @Test
    public void forceDeleteInDirFile() {

        TomcatService tomcatService = TomcatService.getInstance();

        Path target = Paths.get(System.getProperty("java.io.tmpdir"), "test-forceDeleteInDirFile-for-unit-test");
//      If in Windows: System.getProperty("java.io.tmpdir") is C:\\Users\\users\\AppData\\Local\\Temp

        File willDeleteDir = target.toFile();
        tomcatService.createNewDirectory(willDeleteDir);
        tomcatService.createNewFile(Paths.get(willDeleteDir.getPath(), "file_1").toFile());

        assertTrue(tomcatService.deleteFileInDirectory(willDeleteDir));

    }

}