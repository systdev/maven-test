#!/bin/bash

# JDK 8 兼容性测试脚本
# 此脚本用于在 Java 8 环境下验证项目兼容性

echo "========================================"
echo "JDK 8 兼容性测试"
echo "========================================"
echo ""

# 检查当前 Maven 配置
echo "1. 检查 Maven 配置..."
echo "   源代码版本: $(mvn help:evaluate -Dexpression=maven.compiler.source -q -DforceStdout)"
echo "   目标版本:   $(mvn help:evaluate -Dexpression=maven.compiler.target -q -DforceStdout)"
echo ""

# 检查当前运行环境
echo "2. 检查运行环境..."
java -version
echo ""

# 编译项目
echo "3. 编译 moduleD..."
cd moduleD
mvn clean compile

if [ $? -eq 0 ]; then
    echo "   ✅ 编译成功"
else
    echo "   ❌ 编译失败"
    exit 1
fi
echo ""

# 检查编译输出
echo "4. 验证 class 文件版本..."
CLASS_FILE="target/classes/org/example/moduleD/StaticMethodClass.class"
if [ -f "$CLASS_FILE" ]; then
    MAJOR_VERSION=$(javap -verbose "$CLASS_FILE" 2>/dev/null | grep "major version" | awk '{print $3}')
    echo "   Class 文件 major version: $MAJOR_VERSION"
    if [ "$MAJOR_VERSION" = "52" ]; then
        echo "   ✅ 确认是 Java 8 格式"
    else
        echo "   ⚠️  预期 Java 8 (52)，实际: $MAJOR_VERSION"
    fi
else
    echo "   ❌ 未找到 class 文件"
fi
echo ""

# 运行测试
echo "5. 运行单元测试..."
mvn test

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "✅ 所有测试通过！"
    echo "========================================"
else
    echo ""
    echo "========================================"
    echo "⚠️  部分测试失败"
    echo "========================================"
    echo ""
    echo "注意：如果您在 Java 8 环境下运行，部分测试可能仍会失败"
    echo "这是由于 PowerMock 和 Java 8 的特定交互造成的"
fi
