package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.*;
import org.ContinuityIns.DAO.ArticleDAO;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("INSERT INTO articles (title, user_id, content, cover_image, status, duration) " +
            "VALUES (#{title}, #{userId}, #{content}, #{coverImage}, #{status}, #{duration})")
    int insertArticle(ArticleDAO articleDAO);

    @Select("SELECT * FROM articles")
    List<ArticleDAO> selectAllArticles();

    @Update("UPDATE articles SET status = #{status} WHERE article_id = #{articleId}")
    int updateArticleStatus(int articleId, String status);

    @Select("SELECT * FROM articles WHERE article_id = #{id}")
    ArticleDAO selectArticleById(Integer id);

    @Update("UPDATE articles SET title = #{title}, user_id = #{userId}, content = #{content}, " +
            "cover_image = #{coverImage}, status = #{status}, duration = #{duration} " +
            "WHERE article_id = #{articleId}")
    int updateArticle(ArticleDAO articleDAO);

    @Delete("DELETE FROM articles WHERE article_id = #{articleId}")
    int deleteArticle(Integer articleId);

    @Select("SELECT * FROM articles WHERE user_id = #{userId}")
    List<ArticleDAO> selectArticlesByUser(Integer userId);
}
