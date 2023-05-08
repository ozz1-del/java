/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:46:14 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:45:28
 * @Version: 2.0
 */
package oz1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oz1.domain.entity.User;

public interface UserService extends IService<User> {
  User login(String username, String password);
}
