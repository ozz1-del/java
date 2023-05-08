/*
 * @Author: ykk 
 * @Date: 2023-05-04 20:39:49 
 * @Last Modified by: ykk
 * @Last Modified time: 2023-05-04 21:12:30
 * @Version: 2.0
 */

package oz1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oz1.domain.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
  List<Category> getCategoriesByPage(Integer pageNum, Integer pageSize);

  Category getCategoryById(Integer id);

  int getTotal();

  List<Category> getCategories();

  int deleteCategoryById(Integer id);

  int updateCategoryById(Category category);

}
