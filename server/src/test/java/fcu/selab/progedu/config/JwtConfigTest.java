package fcu.selab.progedu.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class JwtConfigTest {

    @Test
    public void generateToken() {
        JwtConfig jwt = JwtConfig.getInstance();
        String token = jwt.generateToken("student", "username", "name");
        System.out.println(token);
        System.out.println(System.getProperty("java.io.tmpdir"));

    }

    @Test
    public void validateToken() {
    }

    @Test
    public void decodeToken() {
    }
}