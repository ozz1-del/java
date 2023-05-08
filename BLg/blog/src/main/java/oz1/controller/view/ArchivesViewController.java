/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:32:05 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:33:24
 * @Version: 2.0
 */
package oz1.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import oz1.service.BlogService;

@Controller
@RequestMapping("/")
public class ArchivesViewController {

  @Autowired
  private BlogService blogService;

  @GetMapping("/archives")
  public String archives(Model model) {
    model.addAttribute("archives", blogService.archiveBlog());
    model.addAttribute("blogsTotal", blogService.getTotal());
    return "archives";
  }
}
