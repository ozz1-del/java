/*
 * @Author: ykk 
 * @Date: 2023-04-29 22:23:35 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 12:06:56
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
import oz1.domain.entity.Blog;
import oz1.service.BlogService;
import oz1.service.CategoryService;
import oz1.service.TagService;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

  @Autowired
  private BlogService blogService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private TagService tagService;

  // 博客列表
  @RequestMapping("/blogs")
  public String blogs(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "", required = false) String title,
      @RequestParam(defaultValue = "0", required = false) Integer categoryid,
      @RequestParam(defaultValue = "0", required = false) Integer istop,
      Model model) {
    model.addAttribute("blogs", getBlogs(pageNum, pageSize, title, categoryid, istop));
    model.addAttribute("categories", categoryService.getCategories());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("pageTotal", blogService.getTotal());
    model.addAttribute("searchTitle", title);
    model.addAttribute("searchCategory", categoryid);
    model.addAttribute("searchIstop", istop);
    return "admin/blogs";
  }

  // 获取带有参数的博客列表
  private List<Blog> getBlogs(Integer pageNum, Integer pageSize, String title, Integer categoryid, Integer istop) {

    List<Blog> blogs;
    if (Objects.equals(title, "") && categoryid == 0 && istop == 0) {
      blogs = blogService.getBlogsByPage(pageNum, pageSize);
      for (Blog blog : blogs) {
        blog.setCate(categoryService.getCategoryById(blog.getCategory()));
      }
    } else {
      blogs = blogService.getBlogsByTerms(title, categoryid, istop);
      for (Blog blog : blogs) {
        blog.setCate(categoryService.getCategoryById(blog.getCategory()));
      }
    }
    return blogs;
  }

  // 博客编辑页面
  @GetMapping("/blogs/edit")
  public String edit(@RequestParam Integer id, RedirectAttributes attributes, Model model) {
    if (blogService.getBlogById(id) == null) {
      attributes.addFlashAttribute("message", "文章不存在");
      return "redirect:/admin/blogs";
    } else {
      model.addAttribute("blog", blogService.getBlogById(id));
      model.addAttribute("categories", categoryService.getCategories());
      model.addAttribute("tags", tagService.getTags());
      return "admin/blogs-edit";
    }
  }

  // 博客编辑提交
  @PostMapping("/blogs/editBlog")
  public String editBlog(Blog blog,
      RedirectAttributes attributes,
      HttpSession session) {
    if (blog.getStatus() == null) {
      blog.setStatus(1);
    }
    if (blog.getIstop() == null) {
      blog.setIstop(0);
    }
    if (blog.getIscomment() == null) {
      blog.setIscomment(0);
    }
    if (blogService.updateBlogById(blog, session) == 1) {
      attributes.addFlashAttribute("message", "文章：" + blog.getTitle() + " 保存成功");
    } else {
      attributes.addFlashAttribute("message", "文章：" + blog.getTitle() + " 保存失败");
    }
    return "redirect:/admin/blogs";
  }

  // 博客新增页面
  @GetMapping("/blogs/add")
  public String add(Model model) {
    model.addAttribute("blog", new Blog());
    model.addAttribute("categories", categoryService.getCategories());
    model.addAttribute("tags", tagService.getTags());
    return "admin/blogs-edit";
  }

  // 博客删除
  @GetMapping("/blogs/deleteBlog")
  public String deleteBlog(@RequestParam Integer id,
      RedirectAttributes attributes) {
    if (blogService.deleteBlogById(id) == 1) {
      attributes.addFlashAttribute("message", "删除文章成功");
    } else {
      attributes.addFlashAttribute("message", "删除文章失败");
    }
    return "redirect:/admin/blogs";
  }

  // 删除草稿
  @GetMapping("/blogs/deleteDraft")
  public String deleteDraft(@RequestParam Integer id,
      RedirectAttributes attributes) {
    if (blogService.deleteBlogById(id) == 1) {
      attributes.addFlashAttribute("message", "删除草稿成功");
    } else {
      attributes.addFlashAttribute("message", "删除草稿失败");
    }
    return "redirect:/admin/draft";
  }

  // 草稿列表
  @GetMapping("/draft")
  public String draft(Model model) {
    List<Blog> blogDrafts = blogService.getDrafts();
    for (Blog blog : blogDrafts) {
      blog.setCate(categoryService.getCategoryById(blog.getCategory()));
    }
    model.addAttribute("drafts", blogDrafts);
    return "admin/draft";
  }
}
