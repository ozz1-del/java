/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:45:08 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:45:08 
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
import oz1.domain.entity.Tag;
import oz1.mapper.BlogMapper;
import oz1.mapper.TagMapper;
import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
  @Autowired(required = false)
  private TagMapper tagMapper;

  @Autowired(required = false)
  private BlogMapper blogMapper;

  @Transactional
  @Override
  public int addTag(Tag tag) {
    if (tagMapper.selectById(tag.getId()) != null) {
      return 0;
    } else {
      tag.setDate(new Date());
      tagMapper.insert(tag);
      return 1;
    }
  }

  @Transactional
  @Override
  public int deleteTagById(Integer id) {
    deleteBlogTagById(id);
    return tagMapper.deleteById(id);
  }

  private void deleteBlogTagById(Integer id) {
    LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Blog::getStatus, 1);
    queryWrapper.like(Blog::getTag, id);
    List<Blog> list = blogMapper.selectList(queryWrapper);
    for (Blog blog : list) {
      String res = "";
      String[] tags = blog.getTag().split(",");
      Integer index = 0;
      for (String tag : tags) {
        index++;
        if (Integer.valueOf(tag) == id) {
          continue;
        }
        res += tag + ",";
      }
      res = res.substring(0, res.length() - 1);
      blog.setTag(res);
      blogMapper.updateById(blog);
    }
  }

  @Override
  public int getTotal() {
    return tagMapper.selectList(null).size();
  }

  @Override
  public Tag getTagById(Integer id) {
    return tagMapper.selectById(id);
  }

  @Override
  public List<Tag> getTags() {
    LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(Tag::getId, 0);
    return tagMapper.selectList(queryWrapper);
  }

  @Transactional
  @Override
  public int updateTagById(Tag tag) {
    if (tag.getId() == null) {
      tag.setDate(new Date());
      return tagMapper.insert(tag);
    } else {
      return tagMapper.updateById(tag);
    }
  }

  @Override
  public List<Tag> getTagsByPage(Integer pageNum, Integer pageSize) {
    if (pageNum < 1) {
      pageNum = 1;
    }
    Page<Tag> page = new Page<>(pageNum, pageSize);
    LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(Tag::getId, 0);
    Page<Tag> selectPage = tagMapper.selectPage(page, queryWrapper);
    return selectPage.getRecords();
  }
}
