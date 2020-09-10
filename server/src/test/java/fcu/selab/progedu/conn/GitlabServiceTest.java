package fcu.selab.progedu.conn;

import org.junit.Test;

import static org.junit.Assert.*;

public class GitlabServiceTest {

    @Test
    public void cloneProject() {
        GitlabService gitlabService = GitlabService.getInstance();
        gitlabService.cloneProject("root", "1234");

    }
}