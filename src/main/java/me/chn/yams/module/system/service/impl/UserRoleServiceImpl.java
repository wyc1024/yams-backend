package me.chn.yams.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chn.yams.module.system.entity.UserRole;
import me.chn.yams.module.system.mapper.UserRoleMapper;
import me.chn.yams.module.system.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
