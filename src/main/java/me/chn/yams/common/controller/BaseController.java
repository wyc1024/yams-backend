package me.chn.yams.common.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import me.chn.yams.common.entity.BatchParam;
import me.chn.yams.common.entity.PageParam;
import me.chn.yams.common.entity.R;
import me.chn.yams.common.entity.SuperEntity;
import me.chn.yams.common.exception.BizException;
import me.chn.yams.common.kit.JacksonKit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@RestController
@SuppressWarnings("all")
public abstract class BaseController<Service extends IService<Entity>, Entity extends SuperEntity> {

    public final static String permissionSeparator = ":";
    public final String searchPermission = this.permissionPrefix() + permissionSeparator + "search";
    public final String viewPermission = this.permissionPrefix() + permissionSeparator + "view";
    public final String createPermission = this.permissionPrefix() + permissionSeparator + "create";
    public final String deletePermission = this.permissionPrefix() + permissionSeparator + "delete";
    public final String updatePermission = this.permissionPrefix() + permissionSeparator + "update";
    public final String batchPermission = this.permissionPrefix() + permissionSeparator + "batch";

    private Class<Entity> entityClass = null;
    @Autowired
    protected Service baseService;

    /**
     * TODO: ????????????????????????
     * @return ??????????????????
     */
    public String permissionPrefix() {
        return "";
    }

    public Class<Entity> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }

    @PreAuthorize("hasAuthority(getThis().searchPermission)")
    @GetMapping
    public ResponseEntity<R> search(@RequestParam(defaultValue = "{}") String q) {
//        SecurityKit.checkAuthority(this.searchPermission, "??????????????????");
        PageParam<Entity> param;
        try {
            param = JSONUtil.toBean(q, PageParam.class);
        } catch (Exception e) {
            throw new BizException("????????????", e);
        }
        Page<Entity> page = param.toPage();
        QueryWrapper<Entity> wrapper = param.toQueryWrapper();
        Page<Entity> result = baseService.page(page, wrapper);
        return ResponseEntity.ok(new R().msg("????????????").data(result));
    }

    @PreAuthorize("hasAuthority(getThis().viewPermission)")
    @GetMapping("/{id}")
    public ResponseEntity<R> view(@PathVariable Long id) {
//        SecurityKit.checkAuthority(this.viewPermission, "??????????????????");
        return ResponseEntity.ok(new R().msg("????????????").data(baseService.getById(id)));
    }

    @PreAuthorize("hasAuthority(getThis().createPermission)")
    @PostMapping
    public ResponseEntity<R> create(@RequestBody @Valid Entity entity) {
//        SecurityKit.checkAuthority(this.createPermission, "??????????????????");
        if (entity == null) {
            throw new BizException("????????????");
        }
        baseService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new R().msg("????????????").data(entity));
    }

    @PreAuthorize("hasAuthority(getThis().deletePermission)")
    @DeleteMapping("/{id}")
    public ResponseEntity<R> delete(@PathVariable Long id) {
//        SecurityKit.checkAuthority(this.deletePermission, "??????????????????");
        baseService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority(getThis().updatePermission)")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<R> fullUpdate(@PathVariable Long id, @RequestBody @Valid Entity entity) {
//        SecurityKit.checkAuthority(this.updatePermission, "??????????????????");
        Entity entityUpdate = baseService.getById(id);
        if (entityUpdate == null) throw new BizException("????????????????????????");
        BeanUtils.copyProperties(entity, entityUpdate, "id");
        baseService.updateById(entityUpdate);
        return ResponseEntity.ok(new R().msg("????????????").data(entityUpdate));
    }

    @PreAuthorize("hasAuthority(getThis().updatePermission)")
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<R> patchUpdate(@PathVariable Long id, @RequestBody JsonMergePatch patch) {
//        SecurityKit.checkAuthority(this.updatePermission, "??????????????????");
        if (patch == null) throw new BizException("????????????");
        Entity entity = baseService.getById(id);
        if (entity == null) throw new BizException("????????????????????????");
        try {
            JsonNode patched = patch.apply(JacksonKit.toJsonNode(entity));
            Entity entityUpdate = JacksonKit.toBean(patched, getEntityClass());
            baseService.updateById(entityUpdate);
            return ResponseEntity.ok(new R().msg("????????????").data(entityUpdate));
        } catch (JsonPatchException e) {
            throw new BizException("JsonMergePatch ????????????", e);
        }
    }

    @PreAuthorize("hasAuthority(getThis().batchPermission)")
    @PostMapping("/batch")
    @Transactional
    public ResponseEntity<R> batch(@RequestBody BatchParam param) {
//        SecurityKit.checkAuthority(this.batchPermission, "????????????????????????");
        Map<String, Object> result = new HashMap<>();
        // ??????
        List<Entity> createEntities = param.getCreateEntities(this.getEntityClass());
        if (CollectionUtils.isNotEmpty(createEntities)) {
            baseService.saveBatch(createEntities);
            result.put("create", createEntities);
        }
        // ??????
        Map<Long, JsonMergePatch> updatePatchers = param.getUpdatePatchers();
        if (MapUtils.isNotEmpty(updatePatchers)) {
            List<Entity> patchedEntities = new ArrayList<>();
            for (Map.Entry<Long, JsonMergePatch> entry : updatePatchers.entrySet()) {
                Long id = entry.getKey();
                JsonMergePatch jsonMergePatch = entry.getValue();
                Entity entity = baseService.getById(id);
                if (entity == null) throw new BizException("????????????????????????");
                JsonNode jsonNode = JacksonKit.toJsonNode(entity);
                JsonNode patchedJsonNode = null;
                try {
                    patchedJsonNode = jsonMergePatch.apply(jsonNode);
                } catch (JsonPatchException e) {
                    throw new BizException("JsonMergePatch ????????????", e);
                }
                Entity patchedEntity = JacksonKit.toBean(patchedJsonNode, getEntityClass());
                patchedEntities.add(patchedEntity);
            }
            baseService.updateBatchById(patchedEntities);
            result.put("update", patchedEntities);
        }
        // ??????
        Set<Long> deleteIds = param.getDeleteIds();
        if (CollectionUtils.isNotEmpty(deleteIds)) {
            result.put("delete", baseService.removeByIds(deleteIds));
        }
        // ??????
        Set<Long> viewIds = param.getViewIds();
        if (CollectionUtils.isNotEmpty(viewIds)) {
            List<Entity> views = baseService.listByIds(viewIds);
            result.put("view", views);
        }
        return ResponseEntity.ok(new R().msg("??????????????????").data(result));
    }

}
