package com.imooc.common.enums;

/**
 * @author : chris
 * 2018-06-30
 */
public enum YesOrNo {
    /**
     * 是	有错误
     */
    YES(1),
    /**
     * 否	无错误
     */
    NO(0);

    public final int value;

    YesOrNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
