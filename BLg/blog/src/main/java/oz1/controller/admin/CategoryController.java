/*
 * @Author: ykk 
 * @Date: 2023-04-29 22:25:56 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-04 20:47:59
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
import oz1.domain.entity.Category;
import oz1.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  // 分类列表
  @GetMapping("/categories")
  public String categories(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize,
      Model model) {
    List<Category> categories = categoryService.getCategoriesByPage(pageNum, pageSize);
    model.addAttribute("categories", categories);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageTotal", categoryService.getTotal());
    return "admin/categories";
  }

  // 删除分类
  @GetMapping("/categories/deleteCategory")
  public String deleteCategory(@RequestParam Integer id,
      RedirectAttributes attributes) {
    if (categoryService.deleteCategoryById(id) == 1) {
      attributes.addFlashAttribute("message", "删除分类成功");
    } else {
      attributes.addFlashAttribute("message", "删除分类失败");
    }
    return "redirect:/admin/categories";
  }

  // 新增分类页面
  @GetMapping("/categories/add")
  public String add(Model model) {
    model.addAttribute("category", new Category());
    return "admin/categories-edit";
  }

  // 编辑分类页面
  @GetMapping("/categories/edit")
  public String edit(@RequestParam Integer id, Model model, RedirectAttributes attributes) {
    if (categoryService.getCategoryById(id) == null) {
      attributes.addFlashAttribute("message", "该分类不存在!");
      return "redirect:/admin/categories";
    } else {
      model.addAttribute("category", categoryService.getCategoryById(id));
    }
    return "admin/categories-edit";
  }

  // 编辑分类提交
  @PostMapping("/categories/editCategory")
  public String editCategory(Category category,
      RedirectAttributes attributes) {
    if (categoryService.updateCategoryById(category) == 1) {
      attributes.addFlashAttribute("message", "保存完成");
    } else {
      attributes.addFlashAttribute("message", "保存失败");
    }
    return "redirect:/admin/categories";
  }
}
