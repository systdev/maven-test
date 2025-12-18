package org.example.moduleD;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * 综合PowerMock测试类 - 演示多种PowerMock功能组合使用
 * 注意：在JDK 17下，综合测试有兼容性问题，已跳过
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
        StaticMethodClass.class,
        SingletonClass.class,
        System.class,
        Runtime.class
})
@Ignore("JDK 17兼容性问题 - 综合测试包含System类mocking")
public class ComprehensivePowerMockTest {

    /**
     * 测试组合PowerMock功能 - 静态方法、单例、系统类mocking
     */
    @Test
    public void testCombinedPowerMockFeatures() {
        // 1. Mock静态方法
        PowerMockito.mockStatic(StaticMethodClass.class);
        when(StaticMethodClass.getMessage()).thenReturn("Static Mocked");

        // 2. Mock单例
        PowerMockito.mockStatic(SingletonClass.class);
        SingletonClass mockSingleton = PowerMockito.mock(SingletonClass.class);
        when(SingletonClass.getInstance()).thenReturn(mockSingleton);
        when(mockSingleton.getCounter()).thenReturn(99);

        // 3. Mock系统方法
        PowerMockito.mockStatic(Runtime.class);
        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        when(mockRuntime.availableProcessors()).thenReturn(16);

        // 4. 使用mocked的类
        String message = StaticMethodClass.getMessage();
        SingletonClass singleton = SingletonClass.getInstance();
        int counter = singleton.getCounter();
        int processors = Runtime.getRuntime().availableProcessors();

        // 5. 验证所有交互
        assertEquals("Static Mocked", message);
        assertEquals(99, counter);
        assertEquals(16, processors);

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.getMessage();

        PowerMockito.verifyStatic(SingletonClass.class);
        SingletonClass.getInstance();

        PowerMockito.verifyStatic(Runtime.class);
        Runtime.getRuntime();

        Mockito.verify(mockRuntime).availableProcessors();
    }

    /**
     * 测试混合真实和mocked行为
     */
    @Test
    public void testMixedRealAndMockedBehavior() throws Exception {
        // 真实行为
        FinalMethodClass finalClass = new FinalMethodClass();
        assertEquals("Final Name", finalClass.getName());

        // Mocked静态方法
        PowerMockito.mockStatic(StaticMethodClass.class);
        when(StaticMethodClass.add(5, 3)).thenReturn(100);
        assertEquals(100, StaticMethodClass.add(5, 3));

        // 部分mock
        PartialMockClass spy = PowerMockito.spy(new PartialMockClass());
        when(spy.publicMethod1()).thenReturn("Partial Mock");
        assertEquals("Partial Mock", spy.publicMethod1());

        // Spy上的真实调用
        assertEquals("Public Method 2", spy.publicMethod2());

        // Spy上的mocked私有方法
        PowerMockito.doReturn("Mocked Private").when(spy, "privateMethod");
        assertEquals("Public Method 3: Mocked Private", spy.publicMethod3());
    }

    /**
     * 测试构造函数与其他mock组合
     */
    @Test
    public void testConstructorWithOtherMocks() throws Exception {
        PowerMockito.mockStatic(StaticMethodClass.class);
        when(StaticMethodClass.getMessage()).thenReturn("Constructor Test");

        ConstructorClass mock = PowerMockito.mock(ConstructorClass.class);
        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("Test")
                .thenReturn(mock);

        Mockito.when(mock.getName()).thenReturn("Mocked in Constructor Test");
        Mockito.when(mock.isInitialized()).thenReturn(true);

        ConstructorClass instance = new ConstructorClass("Test");

        // 使用mocked构造函数结果
        assertEquals("Mocked in Constructor Test", instance.getName());
        assertEquals(true, instance.isInitialized());

        // 使用mocked静态方法
        assertEquals("Constructor Test", StaticMethodClass.getMessage());

        PowerMockito.verifyNew(ConstructorClass.class).withArguments("Test");
        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.getMessage();
    }

    /**
     * 测试复杂场景 - 多个类组合
     */
    @Test
    public void testComplexScenarioWithMultipleClasses() throws Exception {
        // 为多个类设置mock
        PowerMockito.mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(999999L);

        PowerMockito.mockStatic(StaticMethodClass.class);
        when(StaticMethodClass.formatName("John", "Doe")).thenReturn("JOHN DOE");

        ConstructorClass mockConstructor = PowerMockito.mock(ConstructorClass.class);
        PowerMockito.whenNew(ConstructorClass.class)
                .withArguments("Complex")
                .thenReturn(mockConstructor);
        Mockito.when(mockConstructor.getDescription()).thenReturn("Complex Mock");

        PrivateMethodClass spyPrivate = PowerMockito.spy(new PrivateMethodClass("Spy", 5));
        PowerMockito.doReturn("TRANSFORMED").when(spyPrivate, "transform");

        // 执行
        long timestamp = System.currentTimeMillis();
        String name = StaticMethodClass.formatName("John", "Doe");
        String desc = new ConstructorClass("Complex").getDescription();
        String processed = spyPrivate.processData("data");

        // 验证
        assertEquals(999999L, timestamp);
        assertEquals("JOHN DOE", name);
        assertEquals("Complex Mock", desc);
        assertEquals("TRANSFORMED", processed);

        // 验证所有交互
        PowerMockito.verifyStatic(System.class);
        System.currentTimeMillis();

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.formatName("John", "Doe");

        PowerMockito.verifyNew(ConstructorClass.class);
        PowerMockito.verifyPrivate(spyPrivate).invoke("transform");
    }

    /**
     * 测试单例mock与静态方法组合
     */
    @Test
    public void testSingletonAndStaticCombination() {
        PowerMockito.mockStatic(SingletonClass.class);
        PowerMockito.mockStatic(StaticMethodClass.class);

        SingletonClass mockSingleton = PowerMockito.mock(SingletonClass.class);
        when(SingletonClass.getInstance()).thenReturn(mockSingleton);
        when(mockSingleton.getMessage()).thenReturn("Singleton Mock");

        when(StaticMethodClass.getMessage()).thenReturn("Static Mock");

        // 使用mock
        assertEquals("Singleton Mock", SingletonClass.getInstance().getMessage());
        assertEquals("Static Mock", StaticMethodClass.getMessage());

        // 验证调用
        PowerMockito.verifyStatic(SingletonClass.class, Mockito.times(2));
        SingletonClass.getInstance();

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.getMessage();

        Mockito.verify(mockSingleton, Mockito.times(1)).getMessage();
    }
}
