/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:43:34 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:43:34 
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
public class Category {
  // ID编号
  private Integer id;
  // 分类名称
  private String name;
  private Integer number;
  // 更新时间
  private Date date;

}
