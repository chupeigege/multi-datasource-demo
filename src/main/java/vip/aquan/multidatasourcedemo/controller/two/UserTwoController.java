package vip.aquan.multidatasourcedemo.controller.two;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.aquan.multidatasourcedemo.entity.User;
import vip.aquan.multidatasourcedemo.service.two.UserTwoService;

import java.util.List;

/**
 * @author Chupei Wang
 * @since 2021/11/19
 */
@RestController
@RequestMapping(value = "/user/two")
public class UserTwoController {
    @Autowired
    private UserTwoService userTwoService;

    @GetMapping(value = "/list")
    public List<User> list() {
        return userTwoService.list();
    }

    @PostMapping(value = "/save")
    public Object save(@RequestBody User user) {
        userTwoService.save(user);
        return "succeed";
    }

}
