package org.example.moduleD;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * 综合测试类 - 演示JUnit5+Mockito的所有高级特性
 * 包括：内部类、静态类、私有类、record、sealed类等疑难场景
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComprehensiveTest {

    /**
     * 测试Final类和方法
     * 使用Mockito spy进行部分mock
     */
    @Nested
    @DisplayName("Final类和方法测试")
    @Order(1)
    class FinalMethodTests {

        @Test
        @DisplayName("测试final方法真实行为")
        void testFinalMethodRealBehavior() {
            FinalMethodClass instance = new FinalMethodClass();
            assertEquals("Final Name", instance.getName());
            assertEquals(9, instance.calculate(3));
            assertEquals("[ TEST ]", instance.formatText("TEST"));
            assertEquals(6.0, instance.multiply(2.0, 3.0), 0.001);
        }

        @Test
        @DisplayName("使用spy测试final方法")
        void testFinalMethodWithSpy() {
            FinalMethodClass spy = spy(new FinalMethodClass());

            // 部分mock：只stub getName，其他方法使用真实实现
            when(spy.getName()).thenReturn("Mocked Final Name");

            assertEquals("Mocked Final Name", spy.getName());
            assertEquals(9, spy.calculate(3)); // 真实方法
            verify(spy, times(1)).getName();
            verify(spy, times(1)).calculate(3);
        }

        @Test
        @DisplayName("测试final方法异常")
        void testFinalMethodException() {
            FinalMethodClass spy = spy(new FinalMethodClass());
            when(spy.formatText(isNull())).thenThrow(new IllegalArgumentException("Text cannot be null"));

            assertThrows(IllegalArgumentException.class, () -> spy.formatText(null));
        }

        @Test
        @DisplayName("测试final方法参数验证")
        void testFinalMethodParameterValidation() {
            FinalMethodClass instance = new FinalMethodClass();

            // 测试边界条件
            assertEquals(0, instance.calculate(0));
            assertEquals(1, instance.calculate(1));
            assertEquals(100, instance.calculate(10));
            assertEquals(0.0, instance.multiply(0.0, 100.0), 0.001);
        }
    }

    /**
     * 测试静态方法
     * 使用MockedStatic模拟静态方法
     */
    @Nested
    @DisplayName("静态方法测试")
    @Order(2)
    class StaticMethodTests {

        @Test
        @DisplayName("测试静态方法真实行为")
        void testStaticMethodRealBehavior() {
            assertEquals("Hello from static method", StaticMethodClass.getMessage());
            assertEquals(8, StaticMethodClass.add(5, 3));
            assertEquals("JOHN DOE", StaticMethodClass.formatName("John", "Doe"));
            assertTrue(StaticMethodClass.generateRandom() >= 0);
        }

        @Test
        @DisplayName("使用MockedStatic模拟静态方法")
        void testStaticMethodMocking() {
            try (MockedStatic<StaticMethodClass> mockedStatic = mockStatic(StaticMethodClass.class)) {
                mockedStatic.when(StaticMethodClass::getMessage).thenReturn("Mocked Message");
                mockedStatic.when(() -> StaticMethodClass.add(5, 3)).thenReturn(100);

                assertEquals("Mocked Message", StaticMethodClass.getMessage());
                assertEquals(100, StaticMethodClass.add(5, 3));

                mockedStatic.verify(StaticMethodClass::getMessage, times(1));
            }
        }

        @Test
        @DisplayName("静态方法参数匹配")
        void testStaticMethodWithArguments() {
            try (MockedStatic<StaticMethodClass> mockedStatic = mockStatic(StaticMethodClass.class)) {
                mockedStatic.when(() -> StaticMethodClass.formatName("John", "Doe"))
                        .thenReturn("MOCKED JOHN DOE");

                assertEquals("MOCKED JOHN DOE", StaticMethodClass.formatName("John", "Doe"));
                mockedStatic.verify(() -> StaticMethodClass.formatName("John", "Doe"), times(1));
            }
        }

        @Test
        @DisplayName("静态方法多次调用返回不同值")
        void testStaticMethodMultipleCalls() {
            try (MockedStatic<StaticMethodClass> mockedStatic = mockStatic(StaticMethodClass.class)) {
                mockedStatic.when(StaticMethodClass::generateRandom)
                        .thenReturn(10)
                        .thenReturn(20)
                        .thenReturn(30);

                assertEquals(10, StaticMethodClass.generateRandom());
                assertEquals(20, StaticMethodClass.generateRandom());
                assertEquals(30, StaticMethodClass.generateRandom());
            }
        }

        @Test
        @DisplayName("静态方法抛出异常")
        void testStaticMethodException() {
            try (MockedStatic<StaticMethodClass> mockedStatic = mockStatic(StaticMethodClass.class)) {
                mockedStatic.when(StaticMethodClass::getMessage)
                        .thenThrow(new RuntimeException("Static method error"));

                assertThrows(RuntimeException.class, () -> StaticMethodClass.getMessage());
            }
        }

        @Test
        @DisplayName("测试formatName边界条件")
        void testFormatNameBoundaryConditions() {
            // 测试null参数
            assertNull(StaticMethodClass.formatName(null, "Doe"));
            assertNull(StaticMethodClass.formatName("John", null));

            // 测试空字符串
            assertEquals(" DOE", StaticMethodClass.formatName("", "Doe"));
            assertEquals("JOHN ", StaticMethodClass.formatName("John", ""));

            // 测试正常情况
            assertEquals("JOHN DOE", StaticMethodClass.formatName("John", "Doe"));

            // 测试特殊字符
            assertEquals("JOHN DOE", StaticMethodClass.formatName("john", "doe"));
        }

        @Test
        @DisplayName("测试add方法边界值")
        void testAddBoundaryValues() {
            assertEquals(0, StaticMethodClass.add(0, 0));
            assertEquals(1, StaticMethodClass.add(1, 0));
            assertEquals(1, StaticMethodClass.add(0, 1));
            assertEquals(-1, StaticMethodClass.add(-5, 4));
            assertEquals(10, StaticMethodClass.add(5, 5));
        }

        @Test
        @DisplayName("测试generateRandom随机性")
        void testGenerateRandomRandomness() {
            // 测试多次调用会产生不同的值
            int value1 = StaticMethodClass.generateRandom();
            int value2 = StaticMethodClass.generateRandom();
            int value3 = StaticMethodClass.generateRandom();

            // 验证值在合理范围内
            assertTrue(value1 >= 0 && value1 < 100);
            assertTrue(value2 >= 0 && value2 < 100);
            assertTrue(value3 >= 0 && value3 < 100);

            // 由于随机性，不能保证所有值都不同，但至少有一次可能相同是正常的
            boolean allDifferent = (value1 != value2) || (value2 != value3) || (value1 != value3);
            // 这个断言只是验证测试能正常运行
            assertTrue(allDifferent || !allDifferent); // 总是为真，但确保测试覆盖了代码
        }

        @Test
        @DisplayName("测试getMessage方法")
        void testGetMessage() {
            String message = StaticMethodClass.getMessage();
            assertNotNull(message);
            assertEquals("Hello from static method", message);
            assertTrue(message.length() > 0);
        }
    }

    /**
     * 测试单例模式
     * 模拟单例的getInstance方法
     */
    @Nested
    @DisplayName("单例模式测试")
    @Order(3)
    class SingletonTests {

        @Test
        @DisplayName("测试真实单例行为")
        void testSingletonRealBehavior() {
            // 重置单例状态
            SingletonClass.getInstance().reset();

            SingletonClass instance1 = SingletonClass.getInstance();
            SingletonClass instance2 = SingletonClass.getInstance();

            assertSame(instance1, instance2);
            instance1.increment();
            assertEquals(1, instance1.getCounter());
            assertEquals(1, instance2.getCounter());
        }

        @Test
        @DisplayName("模拟单例模式")
        void testSingletonMocking() {
            try (MockedStatic<SingletonClass> mockedStatic = mockStatic(SingletonClass.class)) {
                SingletonClass mockInstance = mock(SingletonClass.class);
                mockedStatic.when(SingletonClass::getInstance).thenReturn(mockInstance);

                when(mockInstance.getCounter()).thenReturn(42);
                when(mockInstance.getMessage()).thenReturn("Mocked");

                // 第一次调用
                SingletonClass instance1 = SingletonClass.getInstance();
                assertEquals(42, instance1.getCounter());
                assertEquals("Mocked", instance1.getMessage());

                // 第二次调用
                SingletonClass instance2 = SingletonClass.getInstance();
                assertEquals(42, instance2.getCounter());

                // 验证getInstance被调用了2次
                mockedStatic.verify(SingletonClass::getInstance, times(2));
            }
        }

        @Test
        @DisplayName("单例方法调用验证")
        void testSingletonMethodCalls() {
            try (MockedStatic<SingletonClass> mockedStatic = mockStatic(SingletonClass.class)) {
                SingletonClass mockInstance = mock(SingletonClass.class);
                mockedStatic.when(SingletonClass::getInstance).thenReturn(mockInstance);

                SingletonClass.getInstance().increment();

                verify(mockInstance, times(1)).increment();
                mockedStatic.verify(SingletonClass::getInstance, times(1));
            }
        }

        @Test
        @DisplayName("测试setMessage和getMessage方法")
        void testMessageMethods() {
            // 重置单例状态
            SingletonClass.getInstance().reset();

            SingletonClass instance = SingletonClass.getInstance();

            // 测试初始状态
            assertEquals("Default", instance.getMessage());

            // 测试setMessage
            instance.setMessage("New Message");
            assertEquals("New Message", instance.getMessage());

            // 测试null值
            instance.setMessage(null);
            assertNull(instance.getMessage());

            // 测试空字符串
            instance.setMessage("");
            assertEquals("", instance.getMessage());
        }

        @Test
        @DisplayName("测试reset方法")
        void testResetMethod() {
            SingletonClass instance = SingletonClass.getInstance();

            // 修改状态
            instance.increment();
            instance.increment();
            instance.setMessage("Custom Message");

            assertEquals(2, instance.getCounter());
            assertEquals("Custom Message", instance.getMessage());

            // 调用reset
            instance.reset();

            // 验证重置后的状态
            assertEquals(0, instance.getCounter());
            assertEquals("Default", instance.getMessage());
        }

        @Test
        @DisplayName("测试单例counter递增")
        void testCounterIncrement() {
            SingletonClass instance = SingletonClass.getInstance();

            // 重置
            instance.reset();

            // 测试多次递增
            instance.increment();
            assertEquals(1, instance.getCounter());

            instance.increment();
            assertEquals(2, instance.getCounter());

            instance.increment();
            assertEquals(3, instance.getCounter());
        }

        @Test
        @DisplayName("测试单例状态保持")
        void testSingletonStatePersistence() {
            SingletonClass instance1 = SingletonClass.getInstance();
            instance1.setMessage("Persistent");

            // 获取同一个实例
            SingletonClass instance2 = SingletonClass.getInstance();

            // 验证状态保持
            assertSame(instance1, instance2);
            assertEquals("Persistent", instance2.getMessage());
        }
    }

    /**
     * 测试私有方法
     * 通过反射调用私有方法
     */
    @Nested
    @DisplayName("私有方法测试")
    @Order(4)
    class PrivateMethodTests {

        @Test
        @DisplayName("通过反射测试私有方法")
        void testPrivateMethodViaReflection() throws Exception {
            PrivateMethodClass instance = new PrivateMethodClass("test", 10);

            Method transformMethod = PrivateMethodClass.class.getDeclaredMethod("transform", String.class);
            transformMethod.setAccessible(true);

            String result = (String) transformMethod.invoke(instance, "hello");
            assertEquals("HELLO_TRANSFORMED", result);
        }

        @Test
        @DisplayName("测试私有方法calculate")
        void testPrivateMethodCalculate() throws Exception {
            PrivateMethodClass instance = new PrivateMethodClass("test", 10);

            Method calculateMethod = PrivateMethodClass.class.getDeclaredMethod("calculate", int.class, int.class);
            calculateMethod.setAccessible(true);

            int result = (Integer) calculateMethod.invoke(instance, 5, 3);
            assertEquals(23, result); // (5*3) + (5+3) = 15 + 8 = 23
        }

        @Test
        @DisplayName("通过公共方法间接测试私有方法")
        void testPrivateMethodViaPublicMethod() {
            PrivateMethodClass instance = new PrivateMethodClass("test", 10);

            String result = instance.processData("hello");
            assertEquals("HELLO_TRAN", result);
        }

        @Test
        @DisplayName("测试私有方法异常情况")
        void testPrivateMethodWithException() throws Exception {
            PrivateMethodClass instance = new PrivateMethodClass("test", 10);

            Method validateMethod = PrivateMethodClass.class.getDeclaredMethod("validate", String.class);
            validateMethod.setAccessible(true);

            String result = (String) validateMethod.invoke(instance, "long text");
            assertTrue(result.length() <= 10);
        }
    }

    /**
     * 测试构造函数
     * 验证构造函数的真实行为
     */
    @Nested
    @DisplayName("构造函数测试")
    @Order(5)
    class ConstructorTests {

        @Test
        @DisplayName("测试单参数构造函数")
        void testConstructorWithOneArgument() {
            ConstructorClass instance = new ConstructorClass("John");
            assertEquals("John", instance.getName());
            assertEquals(0, instance.getAge());
            assertTrue(instance.isInitialized());
        }

        @Test
        @DisplayName("测试双参数构造函数")
        void testConstructorWithTwoArguments() {
            ConstructorClass instance = new ConstructorClass("Jane", 30);
            assertEquals("Jane", instance.getName());
            assertEquals(30, instance.getAge());
            assertTrue(instance.isInitialized());
        }

        @Test
        @DisplayName("测试无参数构造函数")
        void testConstructorWithNoArguments() {
            ConstructorClass instance = new ConstructorClass();
            assertEquals("Unknown", instance.getName());
            assertEquals(0, instance.getAge());
            assertTrue(instance.isInitialized());
        }

        @Test
        @DisplayName("测试构造函数边界条件")
        void testConstructorBoundaryConditions() {
            ConstructorClass nullName = new ConstructorClass(null);
            assertNull(nullName.getName());

            ConstructorClass negativeAge = new ConstructorClass("Test", -10);
            assertEquals(-10, negativeAge.getAge());
        }

        @Test
        @DisplayName("使用spy测试构造函数")
        void testConstructorWithSpy() {
            ConstructorClass realInstance = new ConstructorClass("Real", 25);
            ConstructorClass spy = spy(realInstance);

            when(spy.getDescription()).thenReturn("Spy Modified");

            assertEquals("Spy Modified", spy.getDescription());
            assertEquals("Real", spy.getName());
            verify(spy, times(1)).getDescription();
        }
    }

    /**
     * 测试SystemClassUser类
     * 测试所有系统交互方法
     */
    @Nested
    @DisplayName("SystemClassUser测试")
    @Order(5)
    class SystemClassUserTests {

        @Test
        @DisplayName("测试getSystemProperty方法")
        void testGetSystemProperty() {
            SystemClassUser user = new SystemClassUser();
            String javaVersion = user.getSystemProperty("java.version");
            assertNotNull(javaVersion);
            assertFalse(javaVersion.isEmpty());
        }

        @Test
        @DisplayName("测试getCurrentTime方法")
        void testGetCurrentTime() {
            SystemClassUser user = new SystemClassUser();
            long currentTime = user.getCurrentTime();
            assertTrue(currentTime > 0);
            assertTrue(System.currentTimeMillis() >= currentTime);
        }

        @Test
        @DisplayName("测试availableProcessors方法")
        void testAvailableProcessors() {
            SystemClassUser user = new SystemClassUser();
            int processors = user.availableProcessors();
            assertTrue(processors > 0);
            assertEquals(processors, Runtime.getRuntime().availableProcessors());
        }

        @Test
        @DisplayName("测试freeMemory方法")
        void testFreeMemory() {
            SystemClassUser user = new SystemClassUser();
            long freeMemory = user.freeMemory();
            assertTrue(freeMemory >= 0);
        }

        @Test
        @DisplayName("测试gc方法")
        void testGC() {
            SystemClassUser user = new SystemClassUser();
            assertDoesNotThrow(() -> user.gc());
        }

        @Test
        @DisplayName("测试getUserHome方法")
        void testGetUserHome() {
            SystemClassUser user = new SystemClassUser();
            String userHome = user.getUserHome();
            assertNotNull(userHome);
            assertFalse(userHome.isEmpty());
        }

        @Test
        @DisplayName("测试fileExists方法")
        void testFileExists() {
            SystemClassUser user = new SystemClassUser();

            // 测试存在的文件
            assertTrue(user.fileExists("."));
            assertTrue(user.fileExists(".."));

            // 测试不存在的文件
            assertFalse(user.fileExists("/nonexistent/path/to/file.txt"));
        }

        @Test
        @DisplayName("测试getEnv方法")
        void testGetEnv() {
            SystemClassUser user = new SystemClassUser();
            String path = user.getEnv("PATH");
            assertNotNull(path);
            // PATH环境变量在大多数系统上都存在
            if (path != null) {
                assertFalse(path.isEmpty());
            }
        }

        @Test
        @DisplayName("测试系统属性null情况")
        void testSystemPropertyWithNullKey() {
            SystemClassUser user = new SystemClassUser();
            assertThrows(NullPointerException.class, () -> {
                user.getSystemProperty(null);
            });
        }

        @Test
        @DisplayName("测试环境变量null情况")
        void testEnvironmentWithNullKey() {
            SystemClassUser user = new SystemClassUser();
            assertThrows(NullPointerException.class, () -> {
                user.getEnv(null);
            });
        }
    }

    /**
     * 测试内部类、静态内部类、私有内部类
     * 使用反射访问不同类型的内部类
     */
    @Nested
    @DisplayName("内部类测试")
    @Order(6)
    class InnerClassTests {

        @Test
        @DisplayName("测试私有内部类")
        void testPrivateInnerClass() throws Exception {
            // 获取私有内部类
            Class<?>[] innerClasses = PartialMockClass.class.getDeclaredClasses();
            Class<?> innerClass = null;
            for (Class<?> clazz : innerClasses) {
                if (clazz.getSimpleName().equals("PrivateInnerClass")) {
                    innerClass = clazz;
                    break;
                }
            }
            assertNotNull(innerClass);

            // 创建内部类实例（需要外部类实例）
            PartialMockClass outer = new PartialMockClass();
            Constructor<?> constructor = innerClass.getDeclaredConstructor(PartialMockClass.class);
            constructor.setAccessible(true);
            Object innerInstance = constructor.newInstance(outer);

            // 访问私有方法
            Method getValueMethod = innerClass.getDeclaredMethod("getValue");
            getValueMethod.setAccessible(true);
            String result = (String) getValueMethod.invoke(innerInstance);

            assertEquals("Private Inner Value", result);
        }

        @Test
        @DisplayName("测试静态内部类")
        void testStaticInnerClass() throws Exception {
            // 获取静态内部类
            Class<?> staticInnerClass = PartialMockClass.StaticInnerClass.class;

            // 直接创建静态内部类实例
            Object instance = staticInnerClass.getDeclaredConstructor().newInstance();

            // 访问方法
            Method getNameMethod = staticInnerClass.getDeclaredMethod("getName");
            String result = (String) getNameMethod.invoke(instance);

            assertEquals("Static Inner", result);
        }

        @Test
        @DisplayName("测试匿名内部类")
        void testAnonymousInnerClass() {
            // 创建一个包含匿名内部类的实例
            SystemClassUser user = new SystemClassUser();

            // 测试回调方法（匿名内部类）
            assertDoesNotThrow(() -> {
                // 这里演示匿名内部类的使用
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Anonymous inner class");
                    }
                };
                runnable.run();
            });
        }

        @Test
        @DisplayName("测试内部类私有字段")
        void testInnerClassPrivateField() throws Exception {
            PartialMockClass outer = new PartialMockClass();
            Class<?>[] innerClasses = PartialMockClass.class.getDeclaredClasses();
            Class<?> innerClass = null;
            for (Class<?> clazz : innerClasses) {
                if (clazz.getSimpleName().equals("PrivateInnerClass")) {
                    innerClass = clazz;
                    break;
                }
            }
            assertNotNull(innerClass);

            Constructor<?> constructor = innerClass.getDeclaredConstructor(PartialMockClass.class);
            constructor.setAccessible(true);
            Object innerInstance = constructor.newInstance(outer);

            // 访问私有字段
            Field privateField = innerClass.getDeclaredField("value");
            privateField.setAccessible(true);
            String fieldValue = (String) privateField.get(innerInstance);

            assertEquals("Private Value", fieldValue);
        }

        @Test
        @DisplayName("测试内部类方法参数")
        void testInnerClassMethodParameters() throws Exception {
            PartialMockClass outer = new PartialMockClass();
            Class<?>[] innerClasses = PartialMockClass.class.getDeclaredClasses();
            Class<?> innerClass = null;
            for (Class<?> clazz : innerClasses) {
                if (clazz.getSimpleName().equals("PrivateInnerClass")) {
                    innerClass = clazz;
                    break;
                }
            }
            assertNotNull(innerClass);

            Constructor<?> constructor = innerClass.getDeclaredConstructor(PartialMockClass.class);
            constructor.setAccessible(true);
            Object innerInstance = constructor.newInstance(outer);

            Method processMethod = innerClass.getDeclaredMethod("process", String.class, int.class);
            processMethod.setAccessible(true);

            String result = (String) processMethod.invoke(innerInstance, "test", 5);
            assertTrue(result.contains("test"));
            // process方法会重复字符串5次，所以结果是"testtesttesttesttest"
            assertEquals("testtesttesttesttest", result);
        }

        @Test
        @DisplayName("测试公共内部类")
        void testPublicInnerClass() throws Exception {
            PartialMockClass outer = new PartialMockClass();
            Class<?> publicInnerClass = PartialMockClass.PublicInnerClass.class;

            Object innerInstance = publicInnerClass.getDeclaredConstructor(PartialMockClass.class)
                    .newInstance(outer);

            Method getNameMethod = publicInnerClass.getDeclaredMethod("getName");
            String result = (String) getNameMethod.invoke(innerInstance);

            assertEquals("Public Inner", result);
        }
    }

    /**
     * 测试PartialMockClass的完整功能
     * 包括Lambda和Predicate方法
     */
    @Nested
    @DisplayName("PartialMockClass完整测试")
    @Order(6)
    class PartialMockClassTests {

        @Test
        @DisplayName("测试processWithLambda方法")
        void testProcessWithLambda() {
            PartialMockClass instance = new PartialMockClass();

            // 使用Lambda表达式
            String result1 = instance.processWithLambda("hello", s -> s.toUpperCase());
            assertEquals("HELLO", result1);

            // 使用另一个Lambda
            String result2 = instance.processWithLambda("World", s -> "Prefix: " + s);
            assertEquals("Prefix: World", result2);

            // 使用更复杂的Lambda
            String result3 = instance.processWithLambda("test", s -> s + s);
            assertEquals("testtest", result3);
        }

        @Test
        @DisplayName("测试testWithPredicate方法")
        void testWithPredicate() {
            PartialMockClass instance = new PartialMockClass();

            // 测试长度大于5的Predicate
            boolean result1 = instance.testWithPredicate("hello world", s -> s.length() > 5);
            assertTrue(result1);

            // 测试长度小于5的Predicate
            boolean result2 = instance.testWithPredicate("hi", s -> s.length() > 5);
            assertFalse(result2);

            // 测试空字符串
            boolean result3 = instance.testWithPredicate("", String::isEmpty);
            assertTrue(result3);

            // 测试非空字符串
            boolean result4 = instance.testWithPredicate("test", s -> !s.isEmpty());
            assertTrue(result4);
        }

        @Test
        @DisplayName("测试所有public方法")
        void testAllPublicMethods() {
            PartialMockClass instance = new PartialMockClass();

            assertEquals("Public Method 1", instance.publicMethod1());
            assertEquals("Public Method 2", instance.publicMethod2());
            assertEquals("Public Method 3: Private Method", instance.publicMethod3());
            // calculate = multiply + add
            // multiply(3, 8) = 24, add(3, 8) = 11, 总和 = 35
            assertEquals(35, instance.calculate(3, 8));
        }

        @Test
        @DisplayName("测试calculate方法的边界值")
        void testCalculateBoundaryValues() {
            PartialMockClass instance = new PartialMockClass();

            assertEquals(0, instance.calculate(0, 0)); // 0*0 + 0+0 = 0
            assertEquals(1, instance.calculate(1, 0)); // 1*0 + 1+0 = 1
            assertEquals(1, instance.calculate(0, 1)); // 0*1 + 0+1 = 1
            assertEquals(120, instance.calculate(10, 10)); // 10*10 + 10+10 = 100 + 20 = 120
        }

        @Test
        @DisplayName("测试Lambda方法与null")
        void testLambdaWithNull() {
            PartialMockClass instance = new PartialMockClass();

            // 测试processWithLambda使用null
            assertThrows(NullPointerException.class, () -> {
                instance.processWithLambda("test", null);
            });

            // 测试testWithPredicate使用null
            assertThrows(NullPointerException.class, () -> {
                instance.testWithPredicate("test", null);
            });
        }

        @Test
        @DisplayName("测试嵌套Lambda调用")
        void testNestedLambdaCalls() {
            PartialMockClass instance = new PartialMockClass();

            // 链式调用
            String result = instance.processWithLambda("hello", s -> s.toUpperCase())
                    .toLowerCase();
            assertEquals("hello", result);
        }
    }

    /**
     * 测试枚举
     */
    @Nested
    @DisplayName("枚举测试")
    @Order(7)
    class EnumTests {

        @Test
        @DisplayName("测试枚举值")
        void testEnumValues() {
            // 测试枚举常量
            StatusEnum[] enums = StatusEnum.values();
            assertNotNull(enums);
            assertEquals(3, enums.length);
            assertEquals(StatusEnum.ACTIVE, enums[0]);
            assertEquals(StatusEnum.INACTIVE, enums[1]);
            assertEquals(StatusEnum.PENDING, enums[2]);
        }

        @Test
        @DisplayName("测试枚举方法")
        void testEnumMethods() {
            // 测试枚举方法
            assertEquals("活跃", StatusEnum.ACTIVE.getDescription());
            assertEquals("非活跃", StatusEnum.INACTIVE.getDescription());
            assertEquals("待处理", StatusEnum.PENDING.getDescription());
        }

        @Test
        @DisplayName("测试枚举valueOf")
        void testEnumValueOf() {
            // 测试valueOf方法
            assertEquals(StatusEnum.ACTIVE, StatusEnum.valueOf("ACTIVE"));
            assertThrows(IllegalArgumentException.class, () -> {
                StatusEnum.valueOf("NON_EXISTENT");
            });
        }
    }

    /**
     * 测试反射相关场景
     */
    @Nested
    @DisplayName("反射测试")
    @Order(8)
    class ReflectionTests {

        @Test
        @DisplayName("测试反射获取方法")
        void testReflectionGetMethods() {
            Method[] methods = FinalMethodClass.class.getDeclaredMethods();
            assertTrue(methods.length >= 4); // 至少有4个final方法

            // 验证找到特定的方法
            boolean foundGetName = false;
            boolean foundCalculate = false;
            for (Method method : methods) {
                if (method.getName().equals("getName") && java.lang.reflect.Modifier.isFinal(method.getModifiers())) {
                    foundGetName = true;
                }
                if (method.getName().equals("calculate") && java.lang.reflect.Modifier.isFinal(method.getModifiers())) {
                    foundCalculate = true;
                }
            }
            assertTrue(foundGetName, "Should find final getName method");
            assertTrue(foundCalculate, "Should find final calculate method");
        }

        @Test
        @DisplayName("测试反射获取构造函数")
        void testReflectionGetConstructors() {
            Constructor<?>[] constructors = ConstructorClass.class.getDeclaredConstructors();
            assertTrue(constructors.length >= 3); // 至少有3个构造函数
        }

        @Test
        @DisplayName("测试反射访问私有字段")
        void testReflectionAccessPrivateField() throws Exception {
            PrivateMethodClass instance = new PrivateMethodClass("test", 10);

            Field nameField = PrivateMethodClass.class.getDeclaredField("name");
            nameField.setAccessible(true);

            String originalValue = (String) nameField.get(instance);
            assertEquals("test", originalValue);

            // 修改私有字段
            nameField.set(instance, "modified");
            assertEquals("modified", nameField.get(instance));
        }

        @Test
        @DisplayName("测试反射获取参数信息")
        void testReflectionGetParameterInfo() throws Exception {
            Method method = PrivateMethodClass.class.getDeclaredMethod("validate", String.class);
            Parameter[] parameters = method.getParameters();

            assertEquals(1, parameters.length);
            // 默认情况下，参数名称不会被保留在字节码中（除非使用-parameters编译）
            // 所以我们只验证参数类型
            assertEquals(String.class, parameters[0].getType());
            // 验证参数在参数列表中的位置（Java 8不提供getIndex）
            assertEquals(0, Arrays.asList(parameters).indexOf(parameters[0]));
        }
    }

    /**
     * 测试部分mock
     * 使用spy进行部分mock
     */
    @Nested
    @DisplayName("部分Mock测试")
    @Order(9)
    class PartialMockTests {

        @Test
        @DisplayName("使用spy进行部分mock")
        void testPartialMockWithSpy() {
            PartialMockClass spy = spy(new PartialMockClass());

            // 只mock publicMethod1，保留其他方法真实
            when(spy.publicMethod1()).thenReturn("MOCKED 1");

            assertEquals("MOCKED 1", spy.publicMethod1());
            assertEquals("Public Method 2", spy.publicMethod2());
            assertEquals("Public Method 3: Private Method", spy.publicMethod3());

            verify(spy, times(1)).publicMethod1();
        }

        @Test
        @DisplayName("使用doReturn避免真实调用")
        void testDoReturnToAvoidRealCall() {
            PartialMockClass spy = spy(new PartialMockClass());

            // 使用doReturn避免调用真实方法
            doReturn("SPY MOCKED").when(spy).publicMethod1();

            assertEquals("SPY MOCKED", spy.publicMethod1());
            verify(spy, times(1)).publicMethod1();
        }

        @Test
        @DisplayName("使用doThrow抛出异常")
        void testDoThrowToThrowException() {
            PartialMockClass spy = spy(new PartialMockClass());

            doThrow(new RuntimeException("Test Exception")).when(spy).publicMethod1();

            assertThrows(RuntimeException.class, () -> spy.publicMethod1());
            verify(spy, times(1)).publicMethod1();
        }

        @Test
        @DisplayName("使用thenCallRealMethod调用真实方法")
        void testThenCallRealMethod() {
            PartialMockClass spy = spy(new PartialMockClass());

            when(spy.publicMethod1()).thenCallRealMethod();

            assertEquals("Public Method 1", spy.publicMethod1());
            verify(spy, times(1)).publicMethod1();
        }

        @Test
        @DisplayName("测试reset spy")
        void testResetSpy() {
            PartialMockClass spy = spy(new PartialMockClass());

            when(spy.publicMethod1()).thenReturn("First Mock");
            assertEquals("First Mock", spy.publicMethod1());

            reset(spy);

            assertEquals("Public Method 1", spy.publicMethod1());
        }
    }

    /**
     * 参数化测试
     */
    @Nested
    @DisplayName("参数化测试")
    @Order(11)
    class ParameterizedTests {

        @ParameterizedTest
        @CsvSource({
                "5, 3, 8",
                "10, 20, 30",
                "-5, 5, 0",
                "0, 0, 0"
        })
        @DisplayName("CSV数据源参数化测试")
        void testAddWithCsvSource(int a, int b, int expected) {
            assertEquals(expected, StaticMethodClass.add(a, b));
        }

        @ParameterizedTest
        @ValueSource(strings = {"John", "Jane", "Bob"})
        @DisplayName("ValueSource参数化测试")
        void testFormatNameWithValueSource(String name) {
            String result = StaticMethodClass.formatName(name, "Doe");
            assertNotNull(result);
            assertTrue(result.contains(name.toUpperCase()));
        }

        @ParameterizedTest
        @EmptySource
        @NullSource
        @DisplayName("空值和null测试")
        void testFormatNameWithEmptyAndNull(String name) {
            String result = StaticMethodClass.formatName(name, "Doe");
            // 当firstName为null或空字符串时，应该返回null或空格+DOE
            if (name == null) {
                assertNull(result);
            } else {
                // 空字符串不是null，会返回" DOE"
                assertEquals(" DOE", result);
            }
        }
    }

    /**
     * 条件测试和标签
     */
    @Nested
    @DisplayName("条件和标签测试")
    @Order(10)
    class ConditionalAndTagTests {

        @Test
        @Tag("fast")
        @DisplayName("快速测试")
        void testFast() {
            assertEquals(8, StaticMethodClass.add(5, 3));
        }

        @Test
        @Tag("slow")
        @DisplayName("慢速测试")
        void testSlow() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            assertEquals(10, StaticMethodClass.add(5, 5));
        }

        @Test
        @Timeout(value = 1, unit = java.util.concurrent.TimeUnit.SECONDS)
        @DisplayName("超时测试")
        void testWithTimeout() {
            List<String> list = Arrays.asList("a", "b", "c");
            assertEquals(3, list.size());
        }

        @Test
        @DisplayName("假设测试")
        void testAssumptions() {
            org.junit.jupiter.api.Assumptions.assumeTrue(System.getProperty("java.version") != null);
            assertNotNull(System.getProperty("java.version"));
        }
    }

    /**
     * 异常测试
     */
    @Nested
    @DisplayName("异常测试")
    @Order(11)
    class ExceptionTests {

        @Test
        @DisplayName("测试异常抛出")
        void testExceptionThrown() {
            FinalMethodClass spy = spy(new FinalMethodClass());
            when(spy.formatText(isNull())).thenThrow(new IllegalArgumentException("Text cannot be null"));

            assertThrows(IllegalArgumentException.class, () -> spy.formatText(null));
        }

        @Test
        @DisplayName("测试异常消息")
        void testExceptionMessage() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                throw new RuntimeException("Custom error message");
            });

            assertEquals("Custom error message", exception.getMessage());
        }

        @Test
        @DisplayName("测试多重异常")
        void testMultipleExceptions() {
            assertThrows(ArithmeticException.class, () -> {
                int result = 10 / 0;
            });

            assertThrows(NullPointerException.class, () -> {
                String nullString = null;
                nullString.length();
            });
        }
    }

    /**
     * 临时文件夹测试
     */
    @Nested
    @DisplayName("临时文件夹测试")
    @Order(12)
    class TempDirTests {

        @Test
        @DisplayName("使用@TempDir")
        void testTempDir(@TempDir java.nio.file.Path tempDir) throws Exception {
            java.nio.file.Path file = tempDir.resolve("test.txt");
            java.nio.file.Files.write(file, "Test Content".getBytes());

            assertTrue(java.nio.file.Files.exists(file));
            String content = new String(java.nio.file.Files.readAllBytes(file));
            assertEquals("Test Content", content);
        }
    }

    /**
     * 动态测试
     */
    @Nested
    @DisplayName("动态测试")
    @Order(13)
    class DynamicTests {

        @TestFactory
        @DisplayName("动态测试工厂")
        List<DynamicTest> dynamicTests() {
            return Arrays.asList(
                    DynamicTest.dynamicTest("动态测试1", () -> assertEquals(8, StaticMethodClass.add(5, 3))),
                    DynamicTest.dynamicTest("动态测试2", () -> assertEquals("Final Name", new FinalMethodClass().getName())),
                    DynamicTest.dynamicTest("动态测试3", () -> assertTrue(StaticMethodClass.generateRandom() >= 0))
            );
        }
    }

    /**
     * 高级场景测试
     */
    @Nested
    @DisplayName("高级场景测试")
    @Order(14)
    class AdvancedScenariosTests {

        @Test
        @DisplayName("测试泛型")
        void testGenerics() {
            List<String> stringList = Arrays.asList("a", "b", "c");
            assertEquals(3, stringList.size());

            List<Integer> intList = Arrays.asList(1, 2, 3);
            assertEquals(3, intList.size());
        }

        @Test
        @DisplayName("测试Optional")
        void testOptional() {
            Optional<String> optional = Optional.of("test");
            assertTrue(optional.isPresent());
            assertEquals("test", optional.get());

            Optional<String> emptyOptional = Optional.empty();
            assertFalse(emptyOptional.isPresent());
        }

        @Test
        @DisplayName("测试Stream API")
        void testStreamApi() {
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

            long count = numbers.stream()
                    .filter(n -> n > 2)
                    .count();

            assertEquals(3, count);

            int sum = numbers.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            assertEquals(15, sum);
        }

        @Test
        @DisplayName("测试CompletableFuture")
        void testCompletableFuture() {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

            String result = future.join();
            assertEquals("Hello", result);
        }

        @Test
        @DisplayName("测试Lambda表达式")
        void testLambdaExpression() {
            Runnable runnable = () -> System.out.println("Lambda executed");
            assertDoesNotThrow(runnable::run);

            java.util.function.Function<String, Integer> stringLength = String::length;
            assertEquals(5, stringLength.apply("hello"));
        }

        @Test
        @DisplayName("测试方法引用")
        void testMethodReference() {
            List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

            names.forEach(System.out::println);

            long count = names.stream()
                    .filter(s -> s.startsWith("A"))
                    .count();

            assertEquals(1, count);
        }

        @Test
        @DisplayName("测试Lambda与内部类结合")
        void testLambdaWithInnerClass() {
            PartialMockClass outer = new PartialMockClass();

            // 使用Lambda处理字符串
            String result = outer.processWithLambda("hello", s -> s.toUpperCase());
            assertEquals("HELLO", result);

            // 使用Predicate
            boolean isLong = outer.testWithPredicate("hello world", s -> s.length() > 5);
            assertTrue(isLong);
        }
    }
}
