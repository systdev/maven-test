package org.example.moduleD;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Class for demonstrating partial mocking with PowerMock
 * 包含内部类、静态内部类、私有内部类等高级特性
 */
public class PartialMockClass {

    public String publicMethod1() {
        return "Public Method 1";
    }

    public String publicMethod2() {
        return "Public Method 2";
    }

    public String publicMethod3() {
        return "Public Method 3: " + privateMethod();
    }

    private String privateMethod() {
        return "Private Method";
    }

    public int calculate(int a, int b) {
        return multiply(a, b) + add(a, b);
    }

    private int multiply(int a, int b) {
        return a * b;
    }

    private int add(int a, int b) {
        return a + b;
    }

    // ========== 内部类 ==========

    /**
     * 私有内部类
     */
    private class PrivateInnerClass {
        private String value = "Private Value";

        public String getValue() {
            return "Private Inner Value";
        }

        public String process(String data, int count) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(data);
            }
            return sb.toString();
        }
    }

    /**
     * 静态内部类
     */
    public static class StaticInnerClass {
        private static String staticName = "Static Inner";

        public String getName() {
            return "Static Inner";
        }

        public static String getStaticName() {
            return staticName;
        }
    }

    /**
     * 公共内部类
     */
    public class PublicInnerClass {
        private String name = "Public Inner";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // ========== Lambda和函数式接口 ==========

    /**
     * 使用Lambda表达式的方法
     */
    public String processWithLambda(String input, Function<String, String> processor) {
        return processor.apply(input);
    }

    /**
     * 使用Predicate的方法
     */
    public boolean testWithPredicate(String input, Predicate<String> tester) {
        return tester.test(input);
    }
}
