/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:37:05 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:37:05 
 * @Version: 2.0
 */

package oz1.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import oz1.domain.entity.Comment;
import oz1.domain.entity.User;
import oz1.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class CommentViewController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/comments/{blogId}")
  public String comments(@PathVariable Integer blogId,
      Model model) {

    List<Comment> comments = commentService.getCommentsByBlogId(blogId);
    model.addAttribute("comments", comments);
    return "blog::commentList";
  }

  @PostMapping("/comments")
  public String post(Comment comment, HttpSession session) {
    comment.setDate(new Date());
    User user = (User) session.getAttribute("user");
    if (user != null) {
      comment.setIsadmin(1);
    }
    commentService.saveComment(comment);
    return "redirect:/comments/" + comment.getBlog();
  }

}
