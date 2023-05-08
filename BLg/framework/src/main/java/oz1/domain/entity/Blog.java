/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:43:17 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:43:17 
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
public class Blog {
  // ID编号
  private Integer id;
  // 博客标题
  private String title;
  // 博客摘要
  private String summary;
  // 博客内容
  private String content;

  private String username;
  // 发布时间
  private Date createtime;
  // 更新时间
  private Date updatetime;
  // 分类
  private Integer category;
  // 标签
  @Builder.Default
  private String tag = "0";
  // 浏览量
  private Integer views;
  // 评论
  private Integer comments;
  // 首图地址
  private String pictureurl;
  // 是否开启评论
  private Integer iscomment;
  // 0不置顶，1置顶
  private Integer istop;
  // 1.原创；2.转载；3.翻译
  private Integer property;
  private String propertyname;
  // 状态：0.草稿；1.已发布
  private Integer status;
  // 删除标志 0未删除 1删除
  private Integer delFlag;

  @TableField(exist = false)
  private Category cate;

  @TableField(exist = false)
  private List<Tag> ta;
}
