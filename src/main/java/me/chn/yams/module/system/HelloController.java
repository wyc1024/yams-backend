package me.chn.yams.module.system;

import me.chn.yams.module.system.entity.User;
import me.chn.yams.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/hello")
    public Object hello() {
        return userService.list();
    }

    @PostMapping("/x")
    public Object x(@RequestBody User user, HttpServletRequest request) {
        userService.save(user);
        return user;
    }

    @GetMapping("/z")
    public Object z() {
        return new HashMap<String, String>() {
            {
                put("name", "z");
                put("age", "18");
            }
        };
    }

}
