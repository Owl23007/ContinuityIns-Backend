package org.ContinuityIns.mapper;

import org.ContinuityIns.DAO.TagDAO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from tags order by create_time desc limit 25")
    List<TagDAO> getHotTags();

    @Select("select * from tags where tag_name like concat('%', #{keyword}, '%') and status = 0")
    List<TagDAO> getTagListByKeyword(String keyword);

    @Select("select * from tags where tag_name = #{tagName}")
    TagDAO getTag(String tagName);

    @Select("select tag_id from tags where tag_name = #{tagName}")
    Integer getTagId(String tagName);

    @Insert("insert into tags (tag_name,user_id,create_time) values (#{tagName},#{userId},now())")
    void addTag(String tagName, Integer userId);

    @Insert("insert into tag_articles (article_id,tag_id,create_time) values (#{articleId},#{tagId},now())")
    void bindTag(Integer articleId, Integer tagId);
}
