package org.ContinuityIns.controller;

import org.ContinuityIns.po.CategoryPO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     */
    @GetMapping("")
    public Result<List<CategoryPO>> getAllCategories() {
        try {
            List<CategoryPO> categories = categoryService.getAllCategories();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取分类列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取指定分类
     */
    @GetMapping("/{id}")
    public Result<CategoryPO> getCategoryById(@PathVariable("id") Integer categoryId) {
        try {
            CategoryPO category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return Result.error("分类不存在");
            }
            return Result.success(category);
        } catch (Exception e) {
            return Result.error("获取分类信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取子分类
     */
    @GetMapping("/{id}/children")
    public Result<List<CategoryPO>> getChildCategories(@PathVariable("id") Integer parentId) {
        try {
            List<CategoryPO> children = categoryService.getChildCategories(parentId);
            return Result.success(children);
        } catch (Exception e) {
            return Result.error("获取子分类列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建分类
     */
    @PostMapping("")
    public Result<CategoryPO> createCategory(@RequestBody CategoryPO category) {
        try {
            // 基本参数验证
            if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
                return Result.error("分类名称不能为空");
            }

            CategoryPO newCategory = categoryService.createCategory(category);
            if (newCategory == null) {
                return Result.error("创建分类失败");
            }
            return Result.success(newCategory);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("创建分类失败: " + e.getMessage());
        }
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public Result<Void> updateCategory(@PathVariable("id") Integer categoryId,
                                     @RequestBody CategoryPO category) {
        try {
            // 确保路径ID和请求体ID一致
            if (!categoryId.equals(category.getCategoryId())) {
                return Result.error("分类ID不匹配");
            }

            // 基本参数验证
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                return Result.error("分类名称不能为空");
            }

            boolean success = categoryService.updateCategory(category);
            if (!success) {
                return Result.error("更新分类失败");
            }
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("更新分类失败: " + e.getMessage());
        }
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable("id") Integer categoryId) {
        try {
            boolean success = categoryService.deleteCategory(categoryId);
            if (!success) {
                return Result.error("删除分类失败");
            }
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除分类失败: " + e.getMessage());
        }
    }
}
