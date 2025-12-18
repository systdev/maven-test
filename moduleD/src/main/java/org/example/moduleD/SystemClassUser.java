package org.example.moduleD;

import java.io.File;

/**
 * Class that interacts with system classes for PowerMock testing
 */
public class SystemClassUser {

    public String getSystemProperty(String key) {
        return System.getProperty(key);
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public void gc() {
        System.gc();
    }

    public String getUserHome() {
        return System.getProperty("user.home");
    }

    public boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public String getEnv(String variable) {
        return System.getenv(variable);
    }
}
