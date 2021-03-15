package com.meli.desafiospring;

import com.meli.desafiospring.utils.EmailValidatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailValidatorTest {

    @Test
    public void testValidEmail(){
        Assertions.assertTrue(EmailValidatorUtil.isValidEmailAddress("matias@gmail.com"));
    }

    @Test
    public void testInvalidEmail(){
        Assertions.assertFalse(EmailValidatorUtil.isValidEmailAddress("matiasgmail.com"));
    }

    @Test
    public void testNullEmail(){
        Assertions.assertFalse(EmailValidatorUtil.isValidEmailAddress(null));
    }

}
