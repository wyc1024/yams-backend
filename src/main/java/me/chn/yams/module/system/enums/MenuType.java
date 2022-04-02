package me.chn.yams.module.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuType {

    MENU(0, "菜单"),
    BUTTON(1, "按钮");

    MenuType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

}
