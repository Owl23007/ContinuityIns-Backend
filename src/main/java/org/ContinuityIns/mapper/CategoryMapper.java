package org.ContinuityIns.mapper;

import org.ContinuityIns.DAO.CategoryDAO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from categories")
    List<CategoryDAO> selectAll();
}
