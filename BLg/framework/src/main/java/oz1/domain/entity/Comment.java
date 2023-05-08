/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:43:44 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:43:44 
 * @Version: 2.0
 */

package oz1.domain.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
  // ID编号
  private Integer id;
  // 发表评论的用户名称
  private String name;
  // 邮箱地址
  private String email;
  @Builder.Default
  private String avatar = "/image/avator.png";
  // 博客ID编号
  private Integer blog;
  // 博客名
  @TableField(exist = false)
  private String blogTitle;
  // 发布时间
  private Date date;
  // 是否是管理员
  private Integer isadmin;
  // 评论内容
  private String content;
  // 父评论的id
  private Integer parent;

  // 评论
  @TableField(exist = false)
  private List<Comment> replyComments;
  @TableField(exist = false)
  private Comment parentComment;

}
