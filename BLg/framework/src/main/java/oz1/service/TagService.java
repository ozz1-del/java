/*
 * @Author: ykk 
 * @Date: 2023-05-05 11:44:50 
 * @Last Modified by:   ykk 
 * @Last Modified time: 2023-05-05 11:44:50 
 * @Version: 2.0
 */
package oz1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oz1.domain.entity.Tag;
import java.util.List;

public interface TagService extends IService<Tag> {
  List<Tag> getTags();

  int addTag(Tag tag);

  int deleteTagById(Integer id);

  int getTotal();

  int updateTagById(Tag tag);

  Tag getTagById(Integer id);

  List<Tag> getTagsByPage(Integer pageNum, Integer pageSize);
}
