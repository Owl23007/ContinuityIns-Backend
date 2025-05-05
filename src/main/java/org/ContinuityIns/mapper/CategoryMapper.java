package org.ContinuityIns.mapper;

import org.ContinuityIns.DAO.CategoryDAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<CategoryDAO> selectAll();

    @Select("SELECT * FROM categories WHERE category_id = #{categoryId}")
    CategoryDAO selectById(Integer categoryId);

    @Select("SELECT * FROM categories WHERE parent_id = #{parentId}")
    List<CategoryDAO> selectByParentId(Integer parentId);

    @Insert("INSERT INTO categories (name, parent_id, sort_order, description, create_time) " +
            "VALUES (#{name}, #{parentId}, #{sortOrder}, #{description}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    int insert(CategoryDAO category);

    @Update("UPDATE categories SET name = #{name}, parent_id = #{parentId}, " +
            "sort_order = #{sortOrder}, description = #{description} " +
            "WHERE category_id = #{categoryId}")
    int update(CategoryDAO category);

    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    int deleteById(Integer categoryId);

    @Select("SELECT COUNT(*) FROM categories WHERE parent_id = #{categoryId}")
    int countChildren(Integer categoryId);
}
