/*
 * @Author: ykk 
 * @Date: 2023-04-29 22:24:07 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 12:10:46
 * @Version: 2.0
 */
package oz1.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import oz1.domain.entity.Blog;
import oz1.domain.entity.Comment;
import oz1.domain.entity.User;
import oz1.mapper.BlogMapper;
import oz1.mapper.CategoryMapper;
import oz1.mapper.CommentMapper;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

  @Autowired(required = false)
  private BlogMapper blogMapper;

  @Autowired(required = false)
  private CategoryMapper categoryMapper;

  @Autowired(required = false)
  private CommentMapper commentMapper;

  // 添加博客
  @Transactional
  @Override
  public int addBlog(Blog blog) {
    blog.setCreatetime(new Date());
    blog.setUpdatetime(new Date());
    if (blog.getProperty() != null) {
      switch (blog.getProperty()) {
        case 1:
          blog.setPropertyname("原创");
          break;
        case 2:
          blog.setPropertyname("转载");
          break;
        case 3:
          blog.setPropertyname("翻译");
          break;
        default:
          break;
      }
    } else {
      blog.setPropertyname("原创");
    }
    return blogMapper.insert(blog);
  }

  // 获取博客总数
  @Override
  public int getTotal() {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    return blogMapper.selectList(queryWrapper).size();
  }

  // 通过id删除博客
  @Transactional
  @Override
  public int deleteBlogById(Integer id) {
    deleteComment(id);
    return blogMapper.deleteById(id);
  }

  private void deleteComment(Integer id) {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getBlog, id);
    List<Comment> comments = commentMapper.selectList(queryWrapper);
    for (Comment comment : comments) {
      commentMapper.deleteById(comment.getId());
    }
  }

  // 通过id获取博客
  @Override
  public Blog getBlogById(Integer id) {
    return blogMapper.selectById(id);
  }

  // 更新/新增博客
  @Transactional
  @Override
  public int updateBlogById(Blog blog, HttpSession session) {
    if (blog.getId() == null) {
      User user = (User) session.getAttribute("user");
      blog.setUsername(user.getName());
      blog.setCreatetime(new Date());
      blog.setUpdatetime(new Date());
      if (blog.getProperty() != null) {
        switch (blog.getProperty()) {
          case 1:
            blog.setPropertyname("原创");
            break;
          case 2:
            blog.setPropertyname("转载");
            break;
          case 3:
            blog.setPropertyname("翻译");
            break;
          default:
            break;
        }
      } else {
        blog.setPropertyname("原创");
      }
      return blogMapper.insert(blog);
    } else {
      blog.setUpdatetime(new Date());
      return blogMapper.updateById(blog);
    }

  }

  // 更新浏览
  @Transactional
  @Override
  public int updateView(Blog blog) {
    Integer views = blog.getViews();
    blog.setViews(views + 1);
    return blogMapper.updateById(blog);
  }

  // 通过分类获取博客列表
  @Override
  public List<Blog> getBlogsByCategoryId(Integer pageNum, Integer pageSize, Integer id) {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    if (id != 0) {
      queryWrapper.eq(Blog::getCategory, id);
    }
    queryWrapper.eq(Blog::getStatus, 1);
    Page<Blog> page = new Page<>(pageNum, pageSize);
    return blogMapper.selectPage(page, queryWrapper).getRecords();
  }

  // 获取置顶博客
  @Override
  public List<Blog> getTops() {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getIstop, 1);
    return blogMapper.selectList(queryWrapper);
  }

  // 通过元素限制获取博客列表
  @Override
  public List<Blog> getBlogsByTerms(String title, Integer category, Integer istop) {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.orderByDesc(Blog::getCreatetime);
    queryWrapper.eq(Blog::getStatus, 1);
    if (!Objects.equals(title, "")) {
      queryWrapper.like(Blog::getTitle, title);
    }
    if (istop != 0) {
      queryWrapper.eq(Blog::getIstop, istop);
    }
    if (category != 0) {
      queryWrapper.eq(Blog::getCategory, category);
    }
    return blogMapper.selectList(queryWrapper);
  }

  // 分页查找博客
  @Override
  public List<Blog> getBlogsByPage(Integer pageNum, Integer pageSize) {
    if (pageNum < 1) {
      pageNum = 1;
    }
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    queryWrapper.orderByDesc(Blog::getCreatetime);
    Page<Blog> page = new Page<>(pageNum, pageSize);
    return blogMapper.selectPage(page, queryWrapper).getRecords();
  }

  // 获取草稿
  @Override
  public List<Blog> getDrafts() {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 0);
    return blogMapper.selectList(queryWrapper);
  }

  // 通过年份获取博客列表
  public List<Blog> findByYear(String year) {
    List<Blog> blogList = new ArrayList<>();
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    for (Blog blog : blogMapper.selectList(queryWrapper)) {
      String y = new SimpleDateFormat("yyyy").format(blog.getCreatetime());
      if (Objects.equals(year, y)) {
        blog.setCate(categoryMapper.selectById(blog.getCategory()));
        blogList.add(blog);
      }
    }
    return blogList;
  }

  // 获取博客列表中的年份列表
  public List<String> findGroupYear() {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    queryWrapper.orderByDesc(Blog::getCreatetime);
    List<String> years = new ArrayList<>();
    List<Blog> blogList = blogMapper.selectList(queryWrapper);
    for (Blog blog : blogList) {
      String year = new SimpleDateFormat("yyyy").format(blog.getCreatetime());
      years.add(year);
    }
    List<String> collect = years.stream().distinct().collect(Collectors.toList());
    return collect;
  }

  // 获取归档博客哈希表 year-list
  @Override
  public LinkedHashMap<String, List<Blog>> archiveBlog() {
    LinkedHashMap<String, List<Blog>> listMap = new LinkedHashMap<>();
    List<String> groupYear = findGroupYear();
    for (String year : groupYear) {
      List<Blog> byYear = findByYear(year);
      listMap.put(year, byYear);
    }
    return listMap;
  }

  @Override
  public List<Blog> getBlogsByCategoryId(Integer id) {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    queryWrapper.eq(Blog::getCategory, id);
    return blogMapper.selectList(queryWrapper);
  }
}
