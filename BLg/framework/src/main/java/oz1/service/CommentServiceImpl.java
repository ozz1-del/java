/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:44:34 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:55:49
 * @Version: 2.0
 */

package oz1.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oz1.common.MailHelper;
import oz1.domain.entity.Blog;
import oz1.domain.entity.Comment;
import oz1.mapper.BlogMapper;
import oz1.mapper.CommentMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

  @Autowired(required = false)
  private CommentMapper commentMapper;

  @Autowired(required = false)
  private BlogMapper blogMapper;

  @Override
  public List<Comment> getCommentsByBlogId(Integer blogId) {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getBlog, blogId);
    queryWrapper.eq(Comment::getParent, -1);
    queryWrapper.select().orderByAsc(Comment::getDate);
    List<Comment> comments = commentMapper.selectList(queryWrapper);
    return eachComment(comments);
  }

  @Autowired(required = false)
  private MailHelper helper;

  @Override
  public Integer saveComment(Comment comment) {
    helper.sendToMe(comment);
    return commentMapper.insert(comment);
  }

  public List<Comment> getChildById(Integer commentId) {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getParent, commentId);
    List<Comment> commentList = commentMapper.selectList(queryWrapper);
    for (Comment comment : commentList) {
      if (comment.getParent() != -1) {
        comment.setParentComment(commentMapper.selectById(comment.getParent()));
      }
    }
    return commentList;
  }

  private List<Comment> tempReplys = new ArrayList<>();

  private List<Comment> eachComment(List<Comment> comments) {
    List<Comment> commentList = combineChildren(comments);
    return commentList;
  }

  private List<Comment> combineChildren(List<Comment> comments) {
    List<Comment> res = new ArrayList<>();
    for (Comment comment : comments) {
      List<Comment> replys = getChildById(comment.getId());
      for (Comment reply : replys) {
        tempReplys.add(reply);
        recursively(reply);
      }
      comment.setReplyComments(tempReplys);
      res.add(comment);
      tempReplys = new ArrayList<>();
    }
    return res;
  }

  private void recursively(Comment comment) {
    if (getChildById(comment.getId()).size() > 0) {
      List<Comment> replys = getChildById(comment.getId());
      for (Comment reply : replys) {
        tempReplys.add(reply);
        if (getChildById(reply.getId()).size() > 0) {
          recursively(reply);
        }
      }
    }
  }

  @Override
  public List<Comment> getCommentsByPage(Integer pageNum, Integer pageSize) {
    if (pageNum < 1) {
      pageNum = 1;
    }
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(Comment::getDate);
    Page<Comment> page = new Page<>(pageNum, pageSize);
    List<Comment> comments = commentMapper.selectPage(page, queryWrapper).getRecords();
    for (Comment comment : comments) {
      Blog blog = blogMapper.selectById(comment.getBlog());
      if (blog == null)
        continue;
      comment.setBlogTitle(blog.getTitle());
    }
    return comments;
  }

  @Override
  public Integer getTotal() {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    return commentMapper.selectList(queryWrapper).size();
  }

  @Override
  public Integer deleteCommentById(Integer commentId) {
    return commentMapper.deleteById(commentId);
  }
}
