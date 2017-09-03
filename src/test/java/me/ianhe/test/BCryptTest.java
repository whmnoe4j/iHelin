package me.ianhe.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author iHelin
 * @since 2017/9/1 17:43
 */
public class BCryptTest {

    @Test
    public void testPass() {
        String pass = "246260";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(pass);
        System.out.println(hashPass);
    }

}
