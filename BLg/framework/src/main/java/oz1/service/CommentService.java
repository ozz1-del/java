/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:44:02 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:52:05
 * @Version: 2.0
 */
package oz1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oz1.domain.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
  List<Comment> getCommentsByBlogId(Integer blogId);

  Integer saveComment(Comment comment);

  List<Comment> getCommentsByPage(Integer pageNum, Integer pageSize);

  Integer getTotal();

  Integer deleteCommentById(Integer commentId);
}
