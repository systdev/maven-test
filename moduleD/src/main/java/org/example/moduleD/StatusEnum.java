package org.example.moduleD;

/**
 * 简单的枚举类用于测试
 */
public enum StatusEnum {
    ACTIVE("活跃"),
    INACTIVE("非活跃"),
    PENDING("待处理");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
