package org.example.moduleD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * 演示PowerMock部分模拟测试的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PartialMockClass.class)
public class PartialMockClassTest {

    /**
     * 测试部分模拟与Spy - 只mock一个方法，保留其他真实方法
     */
    @Test
    public void testPartialMockWithSpy() {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // 只mock一个公共方法，其他使用真实实现
        when(spy.publicMethod1()).thenReturn("MOCKED 1");

        assertEquals("MOCKED 1", spy.publicMethod1());
        assertEquals("Public Method 2", spy.publicMethod2()); // 真实方法
        assertEquals("Public Method 3: Private Method", spy.publicMethod3()); // 真实方法
    }

    /**
     * 测试部分模拟中的私有方法mocking
     */
    @Test
    public void testPartialMockPrivateMethods() throws Exception {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // Mock私有方法
        PowerMockito.doReturn("MOCKED PRIVATE").when(spy, "privateMethod");

        assertEquals("Public Method 3: MOCKED PRIVATE", spy.publicMethod3());

        PowerMockito.verifyPrivate(spy).invoke("privateMethod");
    }

    /**
     * 测试特定公共方法mocking
     */
    @Test
    public void testPartialMockSpecificPublicMethods() {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // Mock特定的公共方法
        when(spy.publicMethod1()).thenReturn("Mock 1");
        when(spy.publicMethod2()).thenReturn("Mock 2");
        // 保留publicMethod3为真实实现

        assertEquals("Mock 1", spy.publicMethod1());
        assertEquals("Mock 2", spy.publicMethod2());
        assertEquals("Public Method 3: Private Method", spy.publicMethod3());
    }

    /**
     * 测试部分模拟与私有方法交互
     */
    @Test
    public void testPartialMockCalculateWithPrivateMethods() throws Exception {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // Mock私有方法'add'，让'multiply'运行真实逻辑
        PowerMockito.doReturn(100).when(spy, "add", 5, 3);

        // calculate = multiply(5, 3) + add(5, 3) = 15 + 100 = 115
        assertEquals(115, spy.calculate(5, 3));

        PowerMockito.verifyPrivate(spy).invoke("multiply", 5, 3);
        PowerMockito.verifyPrivate(spy).invoke("add", 5, 3);
    }

    /**
     * 测试多个私有方法mocking
     */
    @Test
    public void testPartialMockMultiplePrivateMethods() throws Exception {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // Mock两个私有方法
        PowerMockito.doReturn(10).when(spy, "multiply", 4, 2);
        PowerMockito.doReturn(5).when(spy, "add", 4, 2);

        // calculate = 10 + 5 = 15
        assertEquals(15, spy.calculate(4, 2));

        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("multiply", 4, 2);
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("add", 4, 2);
    }

    /**
     * 测试真实方法与mock方法对比
     */
    @Test
    public void testPartialMockRealVsMockComparison() {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // 真实实现
        assertEquals("Public Method 1", spy.publicMethod1());

        // 现在mock它
        when(spy.publicMethod1()).thenReturn("MOCKED");

        assertEquals("MOCKED", spy.publicMethod1());
    }

    /**
     * 测试部分模拟与参数匹配器
     */
    @Test
    public void testPartialMockWithArgumentMatchers() throws Exception {
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());

        // 对私有方法使用参数匹配器
        PowerMockito.doReturn(50).when(spy, "multiply", Mockito.anyInt(), Mockito.anyInt());
        PowerMockito.doReturn(100).when(spy, "add", Mockito.eq(10), Mockito.eq(20));

        assertEquals(150, spy.calculate(10, 20)); // 50 + 100 = 150

        PowerMockito.verifyPrivate(spy).invoke("multiply", 10, 20);
        PowerMockito.verifyPrivate(spy).invoke("add", 10, 20);
    }

    /**
     * 测试spy的初始化行为
     */
    @Test
    public void testSpyInitialization() {
        // 使用spy监控真实对象
        PartialMockClass realObject = new PartialMockClass();
        PartialMockClass spy = PowerMockito.spy(realObject);

        // spy默认会调用真实方法
        assertEquals("Public Method 1", spy.publicMethod1());

        // 但可以单独mock特定方法
        when(spy.publicMethod2()).thenReturn("Mocked");
        assertEquals("Mocked", spy.publicMethod2());
    }
}
