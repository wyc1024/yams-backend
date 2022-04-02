package me.chn.yams.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.chn.yams.module.system.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<Menu> listAuthorized(Long userId);

}
