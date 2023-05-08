/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:36:25 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:36:25 
 * @Version: 2.0
 */
package oz1.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oz1.domain.entity.Category;
import oz1.service.BlogService;
import oz1.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/")
public class CategoryViewController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private BlogService blogService;

  @GetMapping("/categories")
  public String categories(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "7") Integer pageSize,
      @RequestParam(defaultValue = "0") Integer categoryId,
      Model model) {
    List<Category> allCategories = categoryService.getCategories();

    model.addAttribute("categories", allCategories);
    model.addAttribute("blogs", blogService.getBlogsByCategoryId(pageNum, pageSize, categoryId));
    model.addAttribute("blogsTotal", blogService.getTotal());
    model.addAttribute("currentId", categoryId);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("categoriesTotal", allCategories.size());
    return "categories";
  }
}
