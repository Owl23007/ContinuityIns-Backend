package org.ContinuityIns.mapper;

import lombok.Getter;
import lombok.Setter;

import org.ContinuityIns.DTO.ArticleDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * @param title     文章标题
     * @param userId   用户id
     * @param coverImage 封面图片
     * @param content  文章内容
     * @param duration  文章字数
     * @param status    文章状态
     */

    // 增加文章
    @Insert("insert into articles(title, user_id, cover_image, content, duration) values(#{title}, #{userId}, #{coverImage}, #{content}, #{duration})")
    void addArticle(String title, Integer userId, String coverImage, String content, Integer duration, String status);

    // 删除文章
    @Update("update articles set status = '已删除' where article_id = #{id}")
    void deleteArticle(Integer id);

    // 修改文章
    @Update("update articles set title = #{title}, cover_image = #{coverImage}, content = #{content}, duration = #{duration}, status = #{status} where article_id = #{articleId}")
    void updateArticle(Integer articleId, String title, String coverImage, String content, Integer duration, String status);

    // 修改文章状态
    @Update("update articles set status = #{status} where article_id = #{articleId}")
    void updateStatus(Integer articleId, String status);

    // 获取文章预览列表
    @Select("select * from articles")
    List<ArticleDTO> getList();

    // 根据文章id获取文章
    @Select("select * from articles where article_id = #{id}")
    ArticleDTO getArticleById(Integer id);

    // 根据用户id获取文章预览
    @Select("select * from articles where user_id = #{userId}")
    List<ArticleDTO> getArticleListByUser(Integer userId);

    // 获取文章的收藏、点赞、浏览数
    @Select("SELECT " +
            "SUM(CASE WHEN is_starred = TRUE THEN 1 ELSE 0 END) AS starCount, " +
            "SUM(CASE WHEN is_liked = TRUE THEN 1 ELSE 0 END) AS likeCount, " +
            "COUNT(*) AS viewCount " +
            "FROM article_view_records " +
            "WHERE article_id = #{articleId}")
    ArticleData getArticleData(@Param("articleId") Integer articleId);

    @Setter
    @Getter
    class ArticleData {
        /**
         * 文章的收藏、点赞、浏览数
         */
        private int viewCount;
        private int starCount;
        private int likeCount;

    }
}


