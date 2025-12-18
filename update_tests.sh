#!/bin/bash

echo "为所有模块的测试文件添加中文注释..."

# 为moduleD的StaticMethodClassTest添加中文注释
cat > moduleD/src/test/java/org/example/moduleD/StaticMethodClassTest.java << 'EOF'
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
 * 演示PowerMock静态方法测试的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticMethodClass.class)
public class StaticMethodClassTest {

    /**
     * 测试静态方法mocking
     */
    @Test
    public void testStaticMethodMocking() {
        // Mock静态方法
        PowerMockito.mockStatic(StaticMethodClass.class);

        when(StaticMethodClass.getMessage()).thenReturn("Mocked Message");
        when(StaticMethodClass.add(5, 3)).thenReturn(100);

        assertEquals("Mocked Message", StaticMethodClass.getMessage());
        assertEquals(100, StaticMethodClass.add(5, 3));

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.getMessage();

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.add(5, 3);
    }

    /**
     * 测试带参数的静态方法mocking
     */
    @Test
    public void testStaticMethodWithArguments() {
        PowerMockito.mockStatic(StaticMethodClass.class);

        when(StaticMethodClass.formatName("John", "Doe")).thenReturn("JOHN DOE");
        when(StaticMethodClass.formatName(null, "Doe")).thenReturn(null);

        assertEquals("JOHN DOE", StaticMethodClass.formatName("John", "Doe"));
        assertEquals(null, StaticMethodClass.formatName(null, "Doe"));

        PowerMockito.verifyStatic(StaticMethodClass.class);
        StaticMethodClass.formatName("John", "Doe");
    }

    /**
     * 测试静态方法真实行为（未mocking）
     */
    @Test
    public void testStaticMethodRealBehavior() {
        // 测试真实行为而不mocking
        assertEquals("Hello from static method", StaticMethodClass.getMessage());
        assertEquals(8, StaticMethodClass.add(5, 3));
    }

    /**
     * 测试静态方法多次调用
     */
    @Test
    public void testStaticMethodMultipleCalls() {
        PowerMockito.mockStatic(StaticMethodClass.class);

        when(StaticMethodClass.generateRandom()).thenReturn(42);

        int result1 = StaticMethodClass.generateRandom();
        int result2 = StaticMethodClass.generateRandom();

        assertEquals(42, result1);
        assertEquals(42, result2);

        PowerMockito.verifyStatic(StaticMethodClass.class, Mockito.times(2));
        StaticMethodClass.generateRandom();
    }
}
EOF

echo "完成！"
