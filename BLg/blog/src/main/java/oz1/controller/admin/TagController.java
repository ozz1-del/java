/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:30:57 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:30:57 
 * @Version: 2.0
 */
package oz1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import oz1.domain.entity.Tag;
import oz1.service.TagService;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

  @Autowired
  private TagService tagService;

  // 标签列表
  @GetMapping("/tags")
  public String tags(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize,
      Model model) {
    List<Tag> tagByPage = tagService.getTagsByPage(pageNum, pageSize);
    tagByPage.forEach(System.out::println);
    model.addAttribute("tags", tagByPage);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageTotal", tagService.getTotal());
    return "admin/tags";
  }

  // 删除标签
  @GetMapping("/tags/deleteTag")
  public String deleteTag(@RequestParam Integer id, RedirectAttributes attributes) {
    if (tagService.deleteTagById(id) == 1) {
      attributes.addFlashAttribute("message", "删除标签成功");
    } else {
      attributes.addFlashAttribute("message", "删除标签失败");
    }
    return "redirect:/admin/tags";
  }

  // 新增标签页面
  @GetMapping("/tags/add")
  public String add(Model model) {
    model.addAttribute("tag", new Tag());
    return "admin/tags-edit";
  }

  // 编辑标签页面
  @GetMapping("/tags/edit")
  public String edit(@RequestParam Integer id, Model model, RedirectAttributes attributes) {
    if (tagService.getTagById(id) == null) {
      attributes.addFlashAttribute("message", "分类不存在");
      return "redirect:/admin/tags";
    } else {
      model.addAttribute("tag", tagService.getTagById(id));
    }
    return "admin/tags-edit";
  }

  // 编辑/新增标签
  @PostMapping("/tags/editTag")
  public String editTag(Tag tag,
      RedirectAttributes attributes) {
    if (tagService.updateTagById(tag) == 1) {
      attributes.addFlashAttribute("message", "保存完成");
    } else {
      attributes.addFlashAttribute("message", "保存失败");
    }
    return "redirect:/admin/tags";
  }

}
