package vip.aquan.multidatasourcedemo.controller.one;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.aquan.multidatasourcedemo.entity.User;
import vip.aquan.multidatasourcedemo.service.one.UserOneService;

import java.util.List;

/**
 * @author Chupei Wang
 * @since 2021/11/19
 */
@RestController
@RequestMapping(value = "/user/one")
public class UserOneController {
    @Autowired
    private UserOneService userOneService;

    @GetMapping(value = "/list")
    public List<User> list() {
        return userOneService.list();
    }

    @PostMapping(value = "/save")
    public Object save(@RequestBody User user) {
        userOneService.save(user);
        return "succeed";
    }

}
