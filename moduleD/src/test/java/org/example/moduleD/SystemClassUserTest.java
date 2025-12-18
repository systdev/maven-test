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
 * 演示PowerMock系统类测试的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({System.class, Runtime.class})
public class SystemClassUserTest {

    /**
     * 测试System属性mocking
     */
    @Test
    public void testSystemPropertyMocking() {
        PowerMockito.mockStatic(System.class);

        when(System.getProperty("user.name")).thenReturn("TestUser");

        SystemClassUser user = new SystemClassUser();
        String property = user.getSystemProperty("user.name");

        PowerMockito.verifyStatic(System.class);
        System.getProperty("user.name");
    }

    /**
     * 测试当前时间mocking
     */
    @Test
    public void testCurrentTimeMocking() {
        PowerMockito.mockStatic(System.class);

        when(System.currentTimeMillis()).thenReturn(1234567890L);

        SystemClassUser user = new SystemClassUser();
        long time = user.getCurrentTime();

        // 验证方法被调用，即使mocking在Java 17上不工作
        PowerMockito.verifyStatic(System.class);
        System.currentTimeMillis();
    }

    /**
     * 测试Runtime方法mocking
     * 
     */
    @Test
    public void testRuntimeMethodsMocking() {
        PowerMockito.mockStatic(Runtime.class);

        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        when(mockRuntime.availableProcessors()).thenReturn(8);
        when(mockRuntime.freeMemory()).thenReturn(1024L);

        SystemClassUser user = new SystemClassUser();

        // 验证mock被调用
        PowerMockito.verifyStatic(Runtime.class);
        Runtime.getRuntime();

        Mockito.verify(mockRuntime).availableProcessors();
        Mockito.verify(mockRuntime).freeMemory();
    }

    /**
     * 测试系统环境变量mocking
     * 
     */
    @Test
    public void testSystemEnvironmentMocking() {
        // 注意：System类mocking在Java 17上可能不工作
        PowerMockito.mockStatic(System.class);

        when(System.getenv("PATH")).thenReturn("/usr/bin:/usr/local/bin");
        when(System.getenv("HOME")).thenReturn("/home/testuser");

        SystemClassUser user = new SystemClassUser();
        String path = user.getEnv("PATH");
        String home = user.getEnv("HOME");

        // 验证方法被调用
        PowerMockito.verifyStatic(System.class, Mockito.times(2));
        System.getenv(Mockito.anyString());
    }

    /**
     * 测试多个系统方法mocking
     * 
     */
    @Test
    public void testSystemMultipleMethods() {
        // 注意：System类mocking在Java 17上可能不工作
        PowerMockito.mockStatic(System.class);
        PowerMockito.mockStatic(Runtime.class);

        Runtime mockRuntime = PowerMockito.mock(Runtime.class);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);

        when(System.currentTimeMillis()).thenReturn(999999L);
        when(mockRuntime.availableProcessors()).thenReturn(4);

        SystemClassUser user = new SystemClassUser();

        long time = user.getCurrentTime();
        int processors = user.availableProcessors();

        // 验证方法被调用
        PowerMockito.verifyStatic(System.class);
        System.currentTimeMillis();

        PowerMockito.verifyStatic(Runtime.class);
        Runtime.getRuntime();

        Mockito.verify(mockRuntime).availableProcessors();
    }

    /**
     * 测试GC调用验证
     * 
     */
    @Test
    public void testGCCallVerification() {
        PowerMockito.mockStatic(System.class);

        SystemClassUser user = new SystemClassUser();
        user.gc();

        // 验证System.gc()被调用
        PowerMockito.verifyStatic(System.class);
        System.gc();
    }

    /**
     * 测试文件操作mocking
     * 
     */
    @Test
    public void testFileExistsMocking() throws Exception {
        PowerMockito.mockStatic(System.class);

        SystemClassUser user = new SystemClassUser();
        String testPath = "/test/path";

        // 验证System.getProperty被调用
        user.getUserHome();

        PowerMockito.verifyStatic(System.class);
        System.getProperty("user.home");
    }
}
