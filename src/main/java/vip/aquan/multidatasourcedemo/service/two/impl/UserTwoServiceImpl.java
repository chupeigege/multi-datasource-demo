package vip.aquan.multidatasourcedemo.service.two.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.aquan.multidatasourcedemo.entity.User;
import vip.aquan.multidatasourcedemo.mapper.one.UserOneMapper;
import vip.aquan.multidatasourcedemo.mapper.two.UserTwoMapper;
import vip.aquan.multidatasourcedemo.service.two.UserTwoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Chupei Wang
 * @since 2021/11/19
 */
@Service
@Slf4j
public class UserTwoServiceImpl extends ServiceImpl<UserTwoMapper, User> implements UserTwoService {

    @Resource
    private UserTwoMapper userTwoMapper;

    @Override
    public List<User> list() {
        return userTwoMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public boolean save(User user) {
        userTwoMapper.insert(user);
        return true;
    }
}
