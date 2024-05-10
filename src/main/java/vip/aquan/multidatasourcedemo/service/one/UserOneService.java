package vip.aquan.multidatasourcedemo.service.one;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.aquan.multidatasourcedemo.entity.User;

import java.util.List;

/**
 * @author Chupei Wang
 * @since 2021/11/19
 */
public interface UserOneService extends IService<User> {

    List<User> list();

    boolean save(User user);
}
