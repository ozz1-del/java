/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:44:11 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:44:11 
 * @Version: 2.0
 */
package oz1.domain.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
  // 标签id编号
  private Integer id;
  // 标签名称
  private String name;
  // 更新时间
  private Date date;

}
