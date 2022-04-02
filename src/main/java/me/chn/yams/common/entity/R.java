package me.chn.yams.common.entity;

import cn.hutool.json.JSONUtil;

import java.util.HashMap;

/**
 * HTTP 返回实体
 * @author chn
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R msg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public R data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
