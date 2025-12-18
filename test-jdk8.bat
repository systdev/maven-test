@echo off
REM JDK 8 兼容性测试脚本 - Windows 版本
REM 此脚本用于在 Java 8 环境下验证项目兼容性

echo ========================================
echo JDK 8 兼容性测试
echo ========================================
echo.

REM 检查当前 Maven 配置
echo 1. 检查 Maven 配置...
for /f "tokens=*" %%i in ('mvn help:evaluate -Dexpression=maven.compiler.source -q -DforceStdout') do set SOURCE_VER=%%i
for /f "tokens=*" %%i in ('mvn help:evaluate -Dexpression=maven.compiler.target -q -DforceStdout') do set TARGET_VER=%%i
echo    源代码版本: %SOURCE_VER%
echo    目标版本:   %TARGET_VER%
echo.

REM 检查当前运行环境
echo 2. 检查运行环境...
java -version
echo.

REM 编译项目
echo 3. 编译 moduleD...
cd moduleD
call mvn clean compile

if errorlevel 1 (
    echo    X 编译失败
    exit /b 1
) else (
    echo    √ 编译成功
)
echo.

REM 检查编译输出
echo 4. 验证 class 文件版本...
set CLASS_FILE=target\classes\org\example\moduleD\StaticMethodClass.class
if exist "%CLASS_FILE%" (
    for /f "tokens=3" %%i in ('javap -verbose "%CLASS_FILE%" 2^>nul ^| findstr "major version"') do set MAJOR_VERSION=%%i
    echo    Class 文件 major version: !MAJOR_VERSION!
    if "!MAJOR_VERSION!"=="52" (
        echo    √ 确认是 Java 8 格式
    ) else (
        echo    ! 预期 Java 8 (52)，实际: !MAJOR_VERSION!
    )
) else (
    echo    X 未找到 class 文件
)
echo.

REM 运行测试
echo 5. 运行单元测试...
call mvn test

if errorlevel 1 (
    echo.
    echo ========================================
    echo ! 部分测试失败
    echo ========================================
    echo.
    echo 注意：如果您在 Java 8 环境下运行，部分测试可能仍会失败
    echo 这是由于 PowerMock 和 Java 8 的特定交互造成的
) else (
    echo.
    echo ========================================
    echo √ 所有测试通过！
    echo ========================================
)

cd ..
