package me.chn.yams.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chn.yams.module.system.entity.MenuRole;
import me.chn.yams.module.system.mapper.MenuRoleMapper;
import me.chn.yams.module.system.service.MenuRoleService;
import org.springframework.stereotype.Service;

@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {
}
