/*
 * @Author: ykk 
 * @Date: 2023-04-29 22:23:58 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 11:44:29
 * @Version: 2.0
 */

package oz1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oz1.domain.entity.Blog;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

public interface BlogService extends IService<Blog> {
  List<Blog> getBlogsByPage(Integer pageNum, Integer pageSize);

  List<Blog> getBlogsByTerms(String title, Integer category, Integer istop);

  List<Blog> getDrafts();

  List<Blog> getTops();

  Blog getBlogById(Integer id);

  int deleteBlogById(Integer id);

  int addBlog(Blog blog);

  int updateView(Blog blog);

  int getTotal();

  int updateBlogById(Blog blog, HttpSession session);

  List<Blog> getBlogsByCategoryId(Integer pageNum, Integer pageSize, Integer id);

  List<Blog> getBlogsByCategoryId(Integer id);

  LinkedHashMap<String, List<Blog>> archiveBlog();
}
