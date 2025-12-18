package org.example.moduleA;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Validator类的单元测试
 */
class ValidatorTest {

    private Validator validator = new Validator();

    /**
     * 测试邮箱验证
     */
    @Test
    void testIsValidEmail() {
        assertEquals(true, validator.isValidEmail("test@example.com"));
        assertEquals(true, validator.isValidEmail("user.name@domain.co.uk"));
        assertEquals(false, validator.isValidEmail("invalid"));
        assertEquals(false, validator.isValidEmail(null));
        assertEquals(false, validator.isValidEmail(""));
    }

    /**
     * 测试年龄验证
     */
    @Test
    void testIsValidAge() {
        assertEquals(true, validator.isValidAge(25));
        assertEquals(true, validator.isValidAge(0));
        assertEquals(true, validator.isValidAge(100));
        assertEquals(true, validator.isValidAge(150));
        assertEquals(false, validator.isValidAge(-1));
        assertEquals(false, validator.isValidAge(151));
    }

    /**
     * 测试手机号验证
     */
    @Test
    void testIsValidPhoneNumber() {
        assertEquals(true, validator.isValidPhoneNumber("1234567890"));
        assertEquals(false, validator.isValidPhoneNumber("13800138000"));
        assertEquals(false, validator.isValidPhoneNumber("010-12345678"));
        assertEquals(false, validator.isValidPhoneNumber("123"));
        assertEquals(false, validator.isValidPhoneNumber(null));
    }

    /**
     * 测试非空非空验证
     */
    @Test
    void testIsNotNullOrEmpty() {
        assertEquals(false, validator.isNotNullOrEmpty(null));
        assertEquals(false, validator.isNotNullOrEmpty(""));
        assertEquals(true, validator.isNotNullOrEmpty("  "));
        assertEquals(true, validator.isNotNullOrEmpty("test"));
    }

    /**
     * 测试正数验证
     */
    @Test
    void testIsPositive() {
        assertEquals(true, validator.isPositive(1));
        assertEquals(true, validator.isPositive(100));
        assertEquals(false, validator.isPositive(0));
        assertEquals(false, validator.isPositive(-1));
    }
}
