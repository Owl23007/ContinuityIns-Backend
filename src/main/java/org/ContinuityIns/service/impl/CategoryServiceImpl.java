package org.ContinuityIns.service.impl;

import org.ContinuityIns.po.CategoryPO;
import org.ContinuityIns.mapper.CategoryMapper;
import org.ContinuityIns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryPO> getAllCategories() {
        return categoryMapper.selectAll();
    }

    @Override
    public CategoryPO getCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryMapper.selectById(categoryId);
    }

    @Override
    public List<CategoryPO> getChildCategories(Integer parentId) {
        return categoryMapper.selectByParentId(parentId);
    }

    @Override
    @Transactional
    public CategoryPO createCategory(CategoryPO category) {
        if (category == null) {
            throw new IllegalArgumentException("分类信息不能为空");
        }
        // 如果没有设置排序顺序，默认设置为0
        if (category.getSortOrder() == null) {
            category.setSortOrder(0);
        }

        // 如果没有设置父分类ID，默认设置为0（作为顶级分类）
        if (category.getParentId() == null) {
            category.setParentId(0);
        }

        // 如果设置了父分类ID，验证父分类是否存在
        if (category.getParentId() > 0) {
            CategoryPO parentCategory = getCategoryById(category.getParentId());
            if (parentCategory == null) {
                throw new IllegalArgumentException("父分类不存在");
            }
        }

        int rows = categoryMapper.insert(category);
        if (rows > 0) {
            return category;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateCategory(CategoryPO category) {
        if (category == null || category.getCategoryId() == null) {
            throw new IllegalArgumentException("分类信息不完整");
        }

        // 验证分类是否存在
        CategoryPO existingCategory = getCategoryById(category.getCategoryId());
        if (existingCategory == null) {
            throw new IllegalArgumentException("要更新的分类不存在");
        }

        // 如果更新了父分类ID，需要验证：
        // 1. 新的父分类是否存在
        // 2. 不能将分类的父分类设置为自己或其子分类
        if (category.getParentId() != null && !category.getParentId().equals(existingCategory.getParentId())) {
            if (category.getParentId().equals(category.getCategoryId())) {
                throw new IllegalArgumentException("不能将分类的父分类设置为自己");
            }
            if (category.getParentId() > 0) {
                CategoryPO newParentCategory = getCategoryById(category.getParentId());
                if (newParentCategory == null) {
                    throw new IllegalArgumentException("新的父分类不存在");
                }
            }
        }

        return categoryMapper.update(category) > 0;
    }

    @Override
    @Transactional
    public boolean deleteCategory(Integer categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("分类ID不能为空");
        }

        // 验证分类是否存在
        CategoryPO category = getCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("要删除的分类不存在");
        }

        // 检查是否有子分类
        int childCount = categoryMapper.countChildren(categoryId);
        if (childCount > 0) {
            throw new IllegalArgumentException("该分类下还有子分类，不能直接删除");
        }

        return categoryMapper.deleteById(categoryId) > 0;
    }
}
