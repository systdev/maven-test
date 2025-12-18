package org.example.moduleA;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator = new Validator();

    @Test
    public void testIsValidEmail() {
        assertTrue(validator.isValidEmail("test@example.com"));
        assertTrue(validator.isValidEmail("user@test.org"));
        assertFalse(validator.isValidEmail("test"));
        assertFalse(validator.isValidEmail("test@"));
        assertFalse(validator.isValidEmail("@example.com"));
        assertFalse(validator.isValidEmail(null));
    }

    @Test
    public void testIsValidAge() {
        assertTrue(validator.isValidAge(25));
        assertTrue(validator.isValidAge(0));
        assertTrue(validator.isValidAge(150));
        assertFalse(validator.isValidAge(-1));
        assertFalse(validator.isValidAge(151));
    }

    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(validator.isValidPhoneNumber("1234567890"));
        assertFalse(validator.isValidPhoneNumber("123456789"));
        assertFalse(validator.isValidPhoneNumber("12345678901"));
        assertFalse(validator.isValidPhoneNumber("abc1234567"));
        assertFalse(validator.isValidPhoneNumber(null));
    }

    @Test
    public void testIsNotNullOrEmpty() {
        assertTrue(validator.isNotNullOrEmpty("hello"));
        assertTrue(validator.isNotNullOrEmpty(" "));
        assertFalse(validator.isNotNullOrEmpty(""));
        assertFalse(validator.isNotNullOrEmpty(null));
    }

    @Test
    public void testIsPositive() {
        assertTrue(validator.isPositive(1));
        assertTrue(validator.isPositive(100));
        assertFalse(validator.isPositive(0));
        assertFalse(validator.isPositive(-1));
    }
}
