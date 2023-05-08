/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:40:05 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-05 12:00:12
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
import oz1.domain.entity.Category;
import oz1.mapper.BlogMapper;
import oz1.mapper.CategoryMapper;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

  @Autowired(required = false)
  private CategoryMapper categoryMapper;

  @Autowired(required = false)
  private BlogMapper blogMapper;

  @Transactional
  @Override
  public int deleteCategoryById(Integer id) {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getCategory, id);
    queryWrapper.eq(Blog::getStatus, 1);

    List<Blog> blogs = blogMapper.selectList(queryWrapper);
    for (Blog blog : blogs) {
      blog.setCategory(0);
      blogMapper.updateById(blog);
    }
    return categoryMapper.deleteById(id);
  }

  @Override
  public int getTotal() {
    return categoryMapper.selectList(null).size();
  }

  @Override
  public Category getCategoryById(Integer id) {
    return categoryMapper.selectById(id);
  }

  @Override
  public List<Category> getCategories() {
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(Category::getId, 0);
    updateNumber();
    return categoryMapper.selectList(queryWrapper);
  }

  private void updateNumber() {
    List<Category> categories = categoryMapper.selectList(null);
    for (Category category : categories) {
      LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Blog::getCategory, category.getId());
      ;
      queryWrapper.eq(Blog::getStatus, 1);
      category.setNumber(blogMapper.selectCount(queryWrapper).intValue());
      categoryMapper.updateById(category);
    }
  }

  @Transactional
  @Override
  public int updateCategoryById(Category category) {
    category.setDate(new Date());
    if (category.getId() == null) {
      category.setNumber(0);
      return categoryMapper.insert(category);
    } else {
      return categoryMapper.updateById(category);
    }
  }

  @Override
  public List<Category> getCategoriesByPage(Integer pageNum, Integer pageSize) {
    updateNumber();

    if (pageNum < 1) {
      pageNum = 1;
    }
    Page<Category> page = new Page<>(pageNum, pageSize);
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(Category::getId, 0);
    Page<Category> selectPage = categoryMapper.selectPage(page, queryWrapper);
    return selectPage.getRecords();
  }
}
