/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:45:45 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:45:34
 * @Version: 2.0
 */
package oz1.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oz1.domain.entity.User;
import oz1.mapper.UserMapper;
import oz1.util.MD5util;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired(required = false)
  private UserMapper userMapper;

  @Override
  public User login(String username, String password) {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getName, username);
    queryWrapper.eq(User::getPassword, MD5util.code(password));
    User user = userMapper.selectOne(queryWrapper);
    return user;
  }
}
