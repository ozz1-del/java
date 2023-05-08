/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:43:38 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-04 20:56:35
 * @Version: 2.0
 */
package oz1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import oz1.service.CommentService;

@Controller
@RequestMapping("/admin")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/comments")
  public String comments(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
    model.addAttribute("comments", commentService.getCommentsByPage(pageNum, pageSize));
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageTotal", commentService.getTotal());
    return "admin/comments";
  }

  @GetMapping("/comments/deleteComment")
  public String deleteComment(@RequestParam Integer id, RedirectAttributes attributes) {
    if (commentService.deleteCommentById(id) == 1) {
      attributes.addFlashAttribute("message", "评论删除成功");
    } else {
      attributes.addFlashAttribute("message", "评论删除失败");
    }
    return "redirect:/admin/comments";
  }
}
