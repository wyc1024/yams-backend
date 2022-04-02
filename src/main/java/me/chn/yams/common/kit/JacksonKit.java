package me.chn.yams.common.kit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.chn.yams.common.exception.BizException;

public class JacksonKit {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        try {
            return mapper.readValue(jsonString, beanClass);
        } catch (JsonProcessingException e) {
            throw new BizException("JSON 转换异常", e);
        }
    }

    public static String toJsonStr(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BizException("JSON 转换异常", e);
        }
    }

    public static JsonNode toJsonNode(Object object) {
        try {
            return mapper.valueToTree(object);
        } catch (IllegalArgumentException e) {
            throw new BizException("JSON 转换异常", e);
        }
    }

    public static <T> T toBean(JsonNode jsonNode, Class<T> beanClass) {
        try {
            return mapper.treeToValue(jsonNode, beanClass);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new BizException("JSON 转换异常", e);
        }
    }

}
