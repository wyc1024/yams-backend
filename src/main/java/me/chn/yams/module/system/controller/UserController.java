package me.chn.yams.module.system.controller;

import cn.hutool.jwt.JWTUtil;
import me.chn.yams.common.controller.BaseController;
import me.chn.yams.common.entity.R;
import me.chn.yams.module.system.param.LoginParam;
import me.chn.yams.module.system.entity.Menu;
import me.chn.yams.module.system.entity.Role;
import me.chn.yams.module.system.entity.User;
import me.chn.yams.module.system.service.MenuService;
import me.chn.yams.module.system.service.RoleService;
import me.chn.yams.module.system.service.UserService;
import me.chn.yams.security.constant.SecurityConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String permissionPrefix() {
        return "user";
    }

    /**
     * 把角色和权限标识放token了，以后优化吧
     * @param loginParam
     * @return
     */
    @PostMapping("/token")
    public ResponseEntity login(@RequestBody @Valid LoginParam loginParam) {
        String username = loginParam.getUsername();
        User user = userService.getByUsername(username);
        if (user != null && passwordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("expiresAt", System.currentTimeMillis() + SecurityConstant.EXPIRES_TIME);
            payload.put("authorities", this.buildAuthorities(user.getId()));
            String token = JWTUtil.createToken(payload, SecurityConstant.JWT_SECRET);
            return ResponseEntity.status(HttpStatus.CREATED).body(new R().msg("登录成功").data(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new R().msg("登陆失败"));
        }
    }

    private String buildAuthorities(Long userId) {
        StringBuilder authorities = new StringBuilder();
        List<Role> roles = roleService.listAuthorized(userId);
        if (CollectionUtils.isNotEmpty(roles)) {
            String rolePermissions = roles.stream()
                    .map(Role::getPermission)
                    .filter(StringUtils::isNotBlank)
                    .map(p -> SecurityConstant.ROLE_PREFIX + p)
                    .collect(Collectors.joining(","));
            authorities.append(rolePermissions);
        }
        List<Menu> menus = menuService.listAuthorized(userId);
        if (CollectionUtils.isNotEmpty(menus)) {
            String menuPermissions = menus.stream()
                    .map(Menu::getPermission)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining(","));
            authorities.append(menuPermissions);
        }
        return authorities.toString();
    }

    @PostMapping
    @Override
    public ResponseEntity<R> create(@RequestBody @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.create(user);
    }

}
