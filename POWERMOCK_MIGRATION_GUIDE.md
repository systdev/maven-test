# PowerMock到Mockito迁移指南

## 概述

由于PowerMock 2.0.9在JDK 17上的兼容性问题，建议将测试迁移到Mockito 5.x + mockito-inline。

## 迁移策略

### 1. 静态方法Mock

#### PowerMock方式（Java 8-11）
```java
@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassName.class)
public class TestClass {
    @Test
    public void testMethod() {
        PowerMockito.mockStatic(ClassName.class);
        when(ClassName.staticMethod()).thenReturn("mock");
        // 测试代码
    }
}
```

#### Mockito方式（Java 17+）
```java
@RunWith(MockitoJUnitRunner.class)
public class TestClass {
    @Test
    public void testMethod() {
        try (MockedStatic<ClassName> mocked = mockStatic(ClassName.class)) {
            when(ClassName.staticMethod()).thenReturn("mock");
            // 测试代码
        }
    }
}
```

**关键差异**:
- ✅ 无需`@RunWith(PowerMockRunner.class)`
- ✅ 无需`@PrepareForTest`
- ✅ 使用`try-with-resources`自动管理生命周期
- ✅ 更简洁的API

### 2. Final类和方法

#### PowerMock方式
```java
@Test
public void testFinalMethod() {
    FinalClass mock = PowerMockito.mock(FinalClass.class);
    when(mock.finalMethod()).thenReturn("mock");
}
```

#### Mockito方式（需要mockito-inline）
```xml
<!-- 添加依赖 -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-inline</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```

```java
@Test
public void testFinalMethod() {
    FinalClass mock = mock(FinalClass.class);
    when(mock.finalMethod()).thenReturn("mock");
}
```

**注意**: mockito-inline直接支持final方法和类，无需特殊配置。

### 3. 私有方法Mock

#### PowerMock方式
```java
@Test
public void testPrivateMethod() throws Exception {
    PrivateClass spy = PowerMockito.spy(new PrivateClass());
    when(spy, "privateMethod").thenReturn("mock");
}
```

#### Mockito方式（有限支持）
```java
@Test
public void testPrivateMethod() {
    PrivateClass spy = spy(new PrivateClass());
    doReturn("mock").when(spy).privateMethod();
}
```

**注意**: Mockito对私有方法支持有限，复杂场景仍需PowerMock。

### 4. 构造函数Mock

**PowerMock支持**: ✅ 完全支持
```java
@Test
public void testConstructor() throws Exception {
    whenNew(MockClass.class).withAnyArguments().thenReturn(mock);
}
```

**Mockito支持**: ❌ 不支持
构造函数Mock只能使用PowerMock。

## JDK 17兼容的测试配置

### 在moduleD/pom.xml中添加mockito-inline依赖

```xml
<dependencies>
    <!-- 保留PowerMock用于JDK 8-11 -->
    <dependency>
        <groupId>org.powermock</groupId>
        <artifactId>powermock-core</artifactId>
        <version>${powermock.version}</version>
        <scope>test</scope>
    </dependency>

    <!-- 添加Mockito-inline用于JDK 17+ -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-inline</artifactId>
        <version>5.2.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 测试标记策略

### 对于JDK 17不兼容的测试

#### 方法1：使用@Ignore
```java
@Test
@Ignore("JDK 17兼容性问题")
public void testSystemClassMocking() {
    // PowerMock System类mocking
}
```

#### 方法2：基于JDK版本的条件执行
```java
@Test
public void testMethod() {
    String jdkVersion = System.getProperty("java.version");
    if (jdkVersion.startsWith("17") || jdkVersion.startsWith("18") || jdkVersion.startsWith("19")) {
        // 跳过或使用替代测试
        return;
    }
    // PowerMock测试
}
```

#### 方法3：使用Assume
```java
import org.junit.Assume;
import org.junit.Before;

@Test
public void testMethod() {
    Assume.assumeFalse(System.getProperty("java.version").startsWith("17"));
    // PowerMock测试
}
```

## 推荐的多版本测试结构

```
moduleD/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── org/example/moduleD/
│   │   │   │   ├── StaticMethodClassTest.java        # PowerMock版本（@Ignore for JDK 17）
│   │   │   │   ├── StaticMethodClassMockitoTest.java # Mockito版本（JDK 17+）
│   │   │   │   ├── FinalMethodClassTest.java         # PowerMock版本（@Ignore for JDK 17）
│   │   │   │   └── ...
```

## 迁移步骤

### 第一阶段：基础静态方法
1. ✅ 创建Mockito版本的静态方法测试
2. ✅ 验证功能一致性
3. 🔄 标记PowerMock版本为@Ignore

### 第二阶段：Final类和私有方法
1. 🔄 添加mockito-inline依赖
2. 🔄 重写final方法测试
3. 🔄 评估私有方法测试迁移必要性

### 第三阶段：构造函数和复杂场景
1. ⚠️ 保留PowerMock测试用于JDK 8-11
2. ⚠️ 使用@Ignore标记JDK 17
3. ⚠️ 考虑重构避免构造函数Mock

## 最佳实践

1. **优先使用Mockito**
   - 更轻量级
   - 更好的JDK 17支持
   - 更简洁的API

2. **保留PowerMock用于特殊场景**
   - 构造函数Mock
   - 复杂私有方法测试

3. **多版本测试策略**
   - CI/CD中运行JDK 8和JDK 17测试
   - 明确标记不兼容测试

4. **文档化**
   - 记录哪些测试需要特定JDK版本
   - 提供替代方案

## 结论

通过逐步迁移，可以实现：
- ✅ JDK 17完全支持
- ✅ 向后兼容JDK 8-11
- ✅ 更现代的测试框架
- ✅ 更好的可维护性

建议优先迁移静态方法测试，然后是final类测试，最后处理复杂场景。
