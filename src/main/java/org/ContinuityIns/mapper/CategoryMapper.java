package org.ContinuityIns.mapper;

import org.ContinuityIns.po.CategoryPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<CategoryPO> selectAll();

    @Select("SELECT * FROM categories WHERE category_id = #{categoryId}")
    CategoryPO selectById(Integer categoryId);

    @Select("SELECT * FROM categories WHERE parent_id = #{parentId}")
    List<CategoryPO> selectByParentId(Integer parentId);

    @Insert("INSERT INTO categories (name, parent_id, sort_order, description, create_time) " +
            "VALUES (#{name}, #{parentId}, #{sortOrder}, #{description}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    int insert(CategoryPO category);

    @Update("UPDATE categories SET name = #{name}, parent_id = #{parentId}, " +
            "sort_order = #{sortOrder}, description = #{description} " +
            "WHERE category_id = #{categoryId}")
    int update(CategoryPO category);

    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    int deleteById(Integer categoryId);

    @Select("SELECT COUNT(*) FROM categories WHERE parent_id = #{categoryId}")
    int countChildren(Integer categoryId);
}
