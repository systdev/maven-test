# JDK 17与PowerMock兼容性解决方案

## 问题概述

PowerMock 2.0.9主要设计用于Java 8-11，在JDK 17上运行时遇到模块系统限制。尽管可以通过JVM参数部分解决，但仍有兼容性问题。

## 解决方案

### 方案一：配置JVM参数（基础方案）

修改`moduleD/pom.xml`，为Surefire插件添加JVM参数：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.2</version>
            <configuration>
                <argLine>
                    ${argLine}
                    --add-opens java.base/java.lang=ALL-UNNAMED
                    --add-opens java.base/java.lang.reflect=ALL-UNNAMED
                    --add-opens java.base/java.util=ALL-UNNAMED
                    --add-opens java.base/java.lang.ref=ALL-UNNAMED
                </argLine>
            </configuration>
        </plugin>
    </plugins>
</build>
```

**效果**: 只能解决部分问题，部分测试仍会失败。

### 方案二：升级到PowerMock 2.x最新版本

PowerMock 2.0.13+ 对JDK 17有更好支持：

```xml
<!-- 在根pom.xml中更新版本 -->
<properties>
    <powermock.version>2.0.13</powermock.version>
</properties>
```

**注意**: 需要同时升级相关依赖版本。

### 方案三：使用Mockito Inline Mock Maker（推荐）

Mockito 3.4+支持inline mock maker，可以部分替代PowerMock功能：

1. **移除PowerMock依赖**
2. **升级Mockito版本**
3. **配置mock maker**

```xml
<!-- 在moduleD/pom.xml中 -->
<dependencies>
    <!-- 移除PowerMock依赖 -->

    <!-- 升级到最新Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>

    <!-- 添加mockito-inline支持 -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-inline</artifactId>
        <version>5.2.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**支持的Mock功能**:
- ✅ Final类和方法
- ✅ 静态方法
- ✅ 私有方法（有限支持）
- ❌ 构造函数Mock（不支持）

### 方案四：创建兼容的测试（混合方案）

对于必须使用PowerMock的测试，在JDK 17下跳过或标记为不可运行：

```java
@Test
@Ignore("JDK 17兼容性问题")
public void testSystemClassMocking() {
    // 只能在JDK 8-11运行
}

@Test
public void testFinalMethodMocking() {
    // 在所有JDK版本都运行
    // 使用Mockito替代PowerMock
}
```

### 方案五：使用Testcontainers或Docker

运行测试时使用JDK 8 Docker容器：

```yaml
# docker-compose.yml
version: '3'
services:
  test:
    image: openjdk:8-jdk
    volumes:
      - .:/workspace
    working_dir: /workspace
    command: mvn test
```

### 方案六：使用JaCoCo排除测试

在报告中排除失败的测试：

```xml
<!-- 在根pom.xml中 -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <configuration>
        <excludes>
            <exclude>**/*PowerMock*Test</exclude>
        </excludes>
    </configuration>
</plugin>
```

## 推荐策略

### 短期解决方案
1. **保留PowerMock 2.0.9**，但跳过失败的测试
2. **使用@Ignore标记**Java 17不兼容的测试
3. **编写替代测试**使用纯Mockito

### 长期解决方案
1. **升级到Mockito 5.x + mockito-inline**
2. **逐步迁移PowerMock测试**到Mockito
3. **仅保留必要PowerMock测试**在JDK 8环境运行

## 示例：迁移测试代码

### PowerMock风格（Java 8-11）
```java
@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticMethodClass.class)
public class StaticMethodClassTest {
    @Test
    public void testStaticMethodMocking() {
        PowerMockito.mockStatic(StaticMethodClass.class);
        when(StaticMethodClass.getMessage()).thenReturn("Mocked");
        assertEquals("Mocked", StaticMethodClass.getMessage());
    }
}
```

### Mockito风格（Java 17）
```java
public class StaticMethodClassTest {
    @Test
    public void testStaticMethodMocking() {
        try (MockedStatic<StaticMethodClass> mocked = mockStatic(StaticMethodClass.class)) {
            mocked.when(StaticMethodClass::getMessage).thenReturn("Mocked");
            assertEquals("Mocked", StaticMethodClass.getMessage());
        }
    }
}
```

## 测试策略建议

1. **基础测试**: 使用JUnit + Mockito
2. **高级Mock**: 使用Mockito-inline
3. **特殊场景**: 使用PowerMock（仅JDK 8-11）
4. **CI/CD**: 使用多JDK版本测试矩阵

## 结论

**最佳实践**:
- 优先使用Mockito和mockito-inline
- PowerMock作为备选方案
- 在JDK 17上标记不兼容测试为@Ignore
- 在CI/CD中设置JDK 8环境运行PowerMock测试

这种混合策略可以在保持向后兼容的同时，逐步迁移到现代测试框架。
