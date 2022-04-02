package me.chn.yams.common.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.Data;
import me.chn.yams.common.exception.BizException;
import me.chn.yams.common.kit.JacksonKit;
import me.chn.yams.common.kit.ValidationKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

@Data
public class BatchParam {
    private ArrayNode create;
    private ObjectNode update;
    private ArrayNode delete;
    private ArrayNode view;

    public <Entity> List<Entity> getCreateEntities(Class<Entity> entityClass) {
        List<Entity> createList = new ArrayList<>();
        if (this.create == null || this.create.size() == 0) return createList;
        for (JsonNode jsonObject : this.create) {
            if (jsonObject == null || jsonObject.isArray() || jsonObject.size() == 0) continue;
            Entity entityCreate = JacksonKit.toBean(jsonObject.toString(), entityClass);
            ValidationKit.check(entityCreate);
            createList.add(entityCreate);
        }
        return createList;
    }

    public Map<Long, JsonMergePatch> getUpdatePatchers() {
        Map<Long, JsonMergePatch> patchers = new HashMap<>();
        if (this.update == null || this.update.size() == 0) return patchers;
        this.update.fields().forEachRemaining(field -> {
            String id = field.getKey();
            JsonNode value = field.getValue();
            if (StringUtils.isBlank(id) || value == null || value.size() == 0) return;
            try {
                patchers.put(Long.valueOf(id), JsonMergePatch.fromJson(value));
            } catch (JsonPatchException e) {
                throw new BizException("JsonMergePatch 解析异常", e);
            }
        });
        return patchers;
    }

    public Set<Long> getDeleteIds() {
        Set<Long> idsDelete = new HashSet<>();
        if (this.delete == null || this.delete.size() == 0) return idsDelete;
        for (JsonNode idNode : this.delete) {
            if (idNode == null) continue;
            String idStr = idNode.asText();
            if (!NumberUtils.isCreatable(idStr)) continue;
            idsDelete.add(Long.valueOf(idStr));
        }
        return idsDelete;
    }

    public Set<Long> getViewIds() {
        Set<Long> idsView = new HashSet<>();
        if (this.view == null || this.view.size() == 0) return idsView;
        for (JsonNode idNode : this.view) {
            if (idNode == null) continue;
            String idStr = idNode.asText();
            if (!NumberUtils.isCreatable(idStr)) continue;
            idsView.add(Long.valueOf(idStr));
        }
        return idsView;
    }

}
