package org.ContinuityIns.service;

import org.ContinuityIns.po.CategoryPO;
import java.util.List;

public interface CategoryService {
    /**
     * 获取所有分类
     */
    List<CategoryPO> getAllCategories();

    /**
     * 根据ID获取分类
     */
    CategoryPO getCategoryById(Integer categoryId);

    /**
     * 获取指定父分类下的所有子分类
     */
    List<CategoryPO> getChildCategories(Integer parentId);

    /**
     * 创建新分类
     */
    CategoryPO createCategory(CategoryPO category);

    /**
     * 更新分类信息
     */
    boolean updateCategory(CategoryPO category);

    /**
     * 删除分类
     * 如果分类下有子分类，则不允许删除
     */
    boolean deleteCategory(Integer categoryId);
}
