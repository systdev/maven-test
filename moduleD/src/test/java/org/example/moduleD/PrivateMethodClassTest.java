package org.example.moduleD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * 演示PowerMock私有方法测试的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PrivateMethodClass.class)
public class PrivateMethodClassTest {

    /**
     * 测试私有方法mocking - 使用spy的doReturn语法
     */
    @Test
    public void testPrivateMethodMocking() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        // 使用doReturn().when()语法mock私有方法
        PowerMockito.doReturn("MOCKED_TRANSFORM").when(spy, "transform");
        PowerMockito.doReturn("VALIDATED").when(spy, "validate");

        String result = spy.processData("input");

        assertEquals("VALIDATED", result);

        PowerMockito.verifyPrivate(spy).invoke("transform");
        PowerMockito.verifyPrivate(spy).invoke("validate");
    }

    /**
     * 测试带参数的私有方法mocking
     */
    @Test
    public void testPrivateMethodWithArguments() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        // Mock私有方法并验证调用
        PowerMockito.doReturn(100).when(spy, "calculate", Mockito.anyInt(), Mockito.anyInt());

        // 验证私有方法被正确调用
        PowerMockito.verifyPrivate(spy).invoke("calculate", Mockito.anyInt(), Mockito.anyInt());
    }

    /**
     * 测试私有方法真实行为 - 部分mock
     */
    @Test
    public void testPrivateMethodCallRealBehavior() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        // 只mock一个私有方法，其他使用真实实现
        PowerMockito.doReturn("CUSTOM").when(spy, "transform");

        String result = spy.processData("hello");

        // "HELLO_TRANSFORMED" -> 验证后截取前10个字符
        assertEquals("HELLO_TRAN", result);
    }

    /**
     * 测试多个私有方法mocking
     */
    @Test
    public void testMultiplePrivateMethods() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        PowerMockito.doReturn("TRANSFORMED").when(spy, "transform");
        PowerMockito.doReturn("VALIDATED_RESULT").when(spy, "validate");

        String result = spy.processData("data");

        assertEquals("VALIDATED_RESULT", result);

        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("transform");
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("validate");
    }

    /**
     * 测试私有方法抛出异常
     */
    @Test
    public void testPrivateMethodThrowsException() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        PowerMockito.doThrow(new RuntimeException("Custom exception")).when(spy, "transform");

        try {
            spy.processData("input");
        } catch (RuntimeException e) {
            assertEquals("Custom exception", e.getMessage());
        }

        PowerMockito.verifyPrivate(spy).invoke("transform");
    }

    /**
     * 测试私有方法直接调用 - 演示如何调用私有方法
     */
    @Test
    public void testPrivateMethodDirectCall() throws Exception {
        PrivateMethodClass spy = PowerMockito.spy(new PrivateMethodClass("test", 10));

        PowerMockito.doReturn(50).when(spy, "calculate", 5, 3);

        // 使用反射直接调用私有方法
        java.lang.reflect.Method calculateMethod = PrivateMethodClass.class.getDeclaredMethod("calculate", int.class, int.class);
        calculateMethod.setAccessible(true);
        int result = (Integer) calculateMethod.invoke(spy, 5, 3);

        assertEquals(50, result);
    }
}
