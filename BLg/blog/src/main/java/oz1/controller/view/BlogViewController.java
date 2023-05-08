/*
 * @Author: ykk 
 * @Date: 2023-04-29 22:23:23 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 12:13:38
 * @Version: 2.0
 */
package oz1.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oz1.domain.entity.Blog;
import oz1.domain.entity.Tag;
import oz1.service.BlogService;
import oz1.service.CategoryService;
import oz1.service.CommentService;
import oz1.service.TagService;
import oz1.util.MarkdownUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class BlogViewController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private BlogService blogService;

  @Autowired
  private TagService tagService;

  @Autowired
  private CommentService commentService;

  @GetMapping("/index")
  public String index() {
    return "redirect:/";
  }

  // 博客列表
  @GetMapping("/")
  public String blogs(@RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "7") Integer pageSize,
      @RequestParam(defaultValue = "", required = false) String title,
      Model model) {

    model.addAttribute("blogs", getBlogs(pageNum, pageSize, title, model));
    model.addAttribute("categories", categoryService.getCategories());
    model.addAttribute("tags", tagService.getTags());
    model.addAttribute("tops", blogService.getTops());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageSize", pageSize);
    return "blogs";
  }

  // 获取博客列表
  private List<Blog> getBlogs(Integer pageNum, Integer pageSize, String title, Model model) {
    List<Blog> blogs;
    if (Objects.equals(title, "")) {
      blogs = blogService.getBlogsByPage(pageNum, pageSize);
      model.addAttribute("pageTotal", blogService.getTotal());
    } else {
      blogs = blogService.getBlogsByTerms(title, 0, 0);
      model.addAttribute("pageTotal", blogs.size());
    }
    return blogs;
  }

  // 博客详情
  @GetMapping("/blog")
  public String blog(@RequestParam Integer id,
      Model model) {

    Blog blog = blogService.getBlogById(id);
    setBlog(blog);
    model.addAttribute("blog", blog);
    model.addAttribute("comments", commentService.getCommentsByBlogId(id));
    return "/blog";
  }

  // 更新文章浏览量，将markdown转为html，并设置标签属性和文章类型
  private void setBlog(Blog blog) {
    // 更新浏览
    blogService.updateView(blog);
    // 转化为html
    String toHtml = MarkdownUtils.markdownToHtml(blog.getContent());
    blog.setContent(toHtml);
    // 设置标签名
    String[] tagsId = blog.getTag().split(",");
    List<Tag> ta = new ArrayList<>();
    for (String tagId : tagsId) {
      if (Objects.equals(tagId, "")) {
        Tag t = new Tag(0, "无", null);
        ta.add(t);
        break;
      }
      Tag tag = tagService.getTagById(Integer.valueOf(tagId));
      if (tag != null && tag.getId() != 0) {
        ta.add(tag);
      }
    }
    blog.setTa(ta);
    // 设置文章分类
    blog.setCate(categoryService.getById(blog.getCategory()));
  }
}
