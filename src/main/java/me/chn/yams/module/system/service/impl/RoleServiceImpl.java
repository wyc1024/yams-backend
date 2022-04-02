package me.chn.yams.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chn.yams.module.system.entity.Role;
import me.chn.yams.module.system.entity.UserRole;
import me.chn.yams.module.system.mapper.RoleMapper;
import me.chn.yams.module.system.service.RoleService;
import me.chn.yams.module.system.service.UserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<Role> listAuthorized(Long userId) {
        LambdaQueryWrapper<UserRole> userRoleQ = new LambdaQueryWrapper<>();
        userRoleQ.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleService.list(userRoleQ);
        if (CollectionUtils.isEmpty(userRoles)) return new ArrayList<>();
        Set<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());
        LambdaQueryWrapper<Role> roleQ = new LambdaQueryWrapper<>();
        roleQ.in(Role::getId, roleIds);
        return this.list(roleQ);
    }

}
