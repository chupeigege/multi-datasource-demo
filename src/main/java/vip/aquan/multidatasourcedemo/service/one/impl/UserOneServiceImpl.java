package vip.aquan.multidatasourcedemo.service.one.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.aquan.multidatasourcedemo.entity.User;
import vip.aquan.multidatasourcedemo.mapper.one.UserOneMapper;
import vip.aquan.multidatasourcedemo.service.one.UserOneService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Chupei Wang
 * @since 2021/11/19
 */
@Service
@Slf4j
public class UserOneServiceImpl extends ServiceImpl<UserOneMapper, User> implements UserOneService {

    @Resource
    private UserOneMapper userOneMapper;

    @Override
    public List<User> list() {
        return userOneMapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Transactional
    public boolean save(User user) {
        userOneMapper.insert(user);
        return true;
    }
}
