package me.chn.yams.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum YN {

    F(0, "否"),
    S(1, "是");

    YN(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

}
