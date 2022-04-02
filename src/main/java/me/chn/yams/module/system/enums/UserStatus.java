package me.chn.yams.module.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {

    LOCK(0, "锁定"),
    VALID(1, "有效");

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

}
