package org.ContinuityIns.service;

import org.ContinuityIns.DAO.CategoryDAO;
import java.util.List;

public interface CategoryService {
    /**
     * 获取所有分类
     */
    List<CategoryDAO> getAllCategories();

    /**
     * 根据ID获取分类
     */
    CategoryDAO getCategoryById(Integer categoryId);

    /**
     * 获取指定父分类下的所有子分类
     */
    List<CategoryDAO> getChildCategories(Integer parentId);

    /**
     * 创建新分类
     */
    CategoryDAO createCategory(CategoryDAO category);

    /**
     * 更新分类信息
     */
    boolean updateCategory(CategoryDAO category);

    /**
     * 删除分类
     * 如果分类下有子分类，则不允许删除
     */
    boolean deleteCategory(Integer categoryId);
}
