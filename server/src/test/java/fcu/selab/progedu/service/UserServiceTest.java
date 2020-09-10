package fcu.selab.progedu.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void getStudents() {
        UserService userService = new UserService();
        System.out.println(userService.getStudents());
    }
}