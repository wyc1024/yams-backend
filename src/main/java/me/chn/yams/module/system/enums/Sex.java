package me.chn.yams.module.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Sex {

    UNKNOWN(0, "保密"),
    MALE(1, "男"),
    FEMALE(2, "女");

    Sex(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

}
