package me.chn.yams.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.chn.yams.module.system.entity.User;

public interface UserService extends IService<User> {

    User getByUsername(String username);

}
