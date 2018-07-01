package com.imooc.common.enums;

/**
 * @author : chris
 * 2018-06-30
 */
public enum SexEnum {
    /**
     * 女
     */
    GIRL(0),
    /**
     * 男
     */
    BOY(1),
    /**
     * 保密
     */
    SECRET(2);

    public final int value;

    SexEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
