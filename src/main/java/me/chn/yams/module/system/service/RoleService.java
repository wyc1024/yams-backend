package me.chn.yams.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.chn.yams.module.system.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> listAuthorized(Long userId);

}
