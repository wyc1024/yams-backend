package me.chn.yams.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chn.yams.module.system.entity.Menu;
import me.chn.yams.module.system.entity.MenuRole;
import me.chn.yams.module.system.entity.Role;
import me.chn.yams.module.system.mapper.MenuMapper;
import me.chn.yams.module.system.service.MenuRoleService;
import me.chn.yams.module.system.service.MenuService;
import me.chn.yams.module.system.service.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuRoleService menuRoleService;

    @Override
    public List<Menu> listAuthorized(Long userId) {
        List<Role> roles = roleService.listAuthorized(userId);
        if (CollectionUtils.isEmpty(roles)) return new ArrayList<>();
        LambdaQueryWrapper<MenuRole> menuRoleQ = new LambdaQueryWrapper<>();
        List<MenuRole> menuRoles = menuRoleService.list(menuRoleQ);
        if (CollectionUtils.isEmpty(menuRoles)) return new ArrayList<>();
        Set<Long> menuIds = menuRoles.stream()
                .map(MenuRole::getMenuId)
                .collect(Collectors.toSet());
        return this.listByIds(menuIds);
    }

}
