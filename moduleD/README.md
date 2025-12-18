# ModuleD - PowerMock 演示模块

## 概述
本模块展示了 PowerMock 2.0.9 的各种用法和功能，包括静态方法、私有方法、最终方法、单例模式、构造函数和系统类的测试。

## 依赖版本
- JUnit: 4.12
- PowerMock: 2.0.9
- Mockito: 1.10.19

## 包含的 PowerMock 功能演示

### 1. 静态方法测试 (StaticMethodClassTest)
- 使用 `@PrepareForTest` 注解准备类
- `PowerMockito.mockStatic()` 模拟静态方法
- `when().thenReturn()` 设置模拟返回值
- `PowerMockito.verifyStatic()` 验证静态方法调用
- 支持参数匹配和方法重载
- 支持多次调用验证

### 2. 私有方法测试 (PrivateMethodClassTest)
- 使用 `PowerMockito.spy()` 创建部分模拟对象
- `when(spy, "methodName")` 模拟私有方法
- `PowerMockito.invokeMethod()` 调用私有方法
- 支持带参数的私有方法
- 支持异常模拟
- 验证私有方法调用

### 3. 最终方法测试 (FinalMethodClassTest)
- 模拟 final 方法
- 使用 spy 监控 final 方法
- 部分模拟 final 类
- 验证 final 方法调用次数

### 4. 单例模式测试 (SingletonClassTest)
- 模拟 `getInstance()` 静态方法
- 返回自定义模拟对象
- 验证单例方法调用
- 使用 spy 监控单例行为

### 5. 构造函数测试 (ConstructorClassTest)
- `PowerMockito.whenNew().thenReturn()` 模拟构造函数
- 支持不同参数的构造函数
- 支持无参数构造函数
- 验证构造函数调用
- 支持构造函数异常模拟

### 6. 系统类测试 (SystemClassUserTest)
- 模拟 `System.getProperty()`
- 模拟 `System.currentTimeMillis()`
- 模拟 `Runtime.getRuntime()` 方法
- 模拟 `System.getenv()`
- 模拟 `File` 类和文件操作
- 验证系统类方法调用

### 7. 部分模拟测试 (PartialMockClassTest)
- 使用 spy 进行部分模拟
- 混合真实方法和模拟方法
- 模拟私有方法在部分模拟中的使用
- 参数匹配器在部分模拟中的应用
- 在部分模拟中验证方法调用

### 8. 综合测试 (ComprehensivePowerMockTest)
- 组合多种 PowerMock 功能
- 同时模拟静态方法、单例、系统类
- 混合真实和模拟行为
- 复杂场景下的测试

## 运行测试

```bash
# 在 moduleD 目录下运行
mvn test

# 运行特定测试类
mvn test -Dtest=StaticMethodClassTest
mvn test -Dtest=PrivateMethodClassTest
mvn test -Dtest=FinalMethodClassTest

# 运行所有测试
mvn clean test
```

## 关键注解

- `@RunWith(PowerMockRunner.class)`: 指定使用 PowerMock 运行器
- `@PrepareForTest(ClassName.class)`: 准备需要修改字节码的类

## 常用方法

### 静态方法模拟
```java
PowerMockito.mockStatic(ClassName.class);
when(ClassName.staticMethod()).thenReturn(value);
PowerMockito.verifyStatic(ClassName.class);
ClassName.staticMethod();
```

### 私有方法模拟
```java
ClassName spy = PowerMockito.spy(new ClassName());
when(spy, "privateMethod").thenReturn(value);
PowerMockito.verifyPrivate(spy).invoke("privateMethod");
```

### 构造函数模拟
```java
ClassName mock = mock(ClassName.class);
PowerMockito.whenNew(ClassName.class)
    .withArguments(arg)
    .thenReturn(mock);
```

## 注意事项

1. 使用 `@PrepareForTest` 注解需要包含所有需要模拟的类
2. 静态方法模拟需要在 `@PrepareForTest` 中声明类
3. PowerMock 修改字节码，某些 IDE 可能需要配置才能正确编译
4. 使用 spy 时，真实方法仍然会执行，需谨慎使用
5. 构造函数模拟需要导入类：`import static org.mockito.Mockito.mock;`

## 项目结构

```
moduleD/
├── pom.xml
├── README.md
└── src/
    ├── main/java/org/example/moduleD/
    │   ├── StaticMethodClass.java
    │   ├── PrivateMethodClass.java
    │   ├── FinalMethodClass.java
    │   ├── SingletonClass.java
    │   ├── ConstructorClass.java
    │   ├── SystemClassUser.java
    │   └── PartialMockClass.java
    └── test/java/org/example/moduleD/
        ├── StaticMethodClassTest.java
        ├── PrivateMethodClassTest.java
        ├── FinalMethodClassTest.java
        ├── SingletonClassTest.java
        ├── ConstructorClassTest.java
        ├── SystemClassUserTest.java
        ├── PartialMockClassTest.java
        └── ComprehensivePowerMockTest.java
```
