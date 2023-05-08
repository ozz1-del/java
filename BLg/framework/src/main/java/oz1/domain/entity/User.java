/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:44:22 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:44:22 
 * @version: 2.0
 */
package oz1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  // 管理员账号
  private String name;
  // 管理员密码
  private String password;
  private String email;
  private String phone;

}
