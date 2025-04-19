package org.ContinuityIns.mapper;

import java.util.List;
import org.ContinuityIns.DAO.ArticleDAO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO articles (title, user_id, content, cover_image, status, word_count) " +
            "VALUES (#{title}, #{userId}, #{content}, #{coverImage}, #{status}, #{duration})")
    int insertArticle(ArticleDAO articleDAO);

    @Select("SELECT * FROM articles")
    List<ArticleDAO> selectAllArticles();

    @Update("UPDATE articles SET status = #{status} WHERE article_id = #{articleId}")
    int updateArticleStatus(int articleId, String status);

    @Select("SELECT * FROM articles WHERE article_id = #{id}")
    ArticleDAO selectArticleById(Integer id);

    @Update("UPDATE articles SET title = #{title}, user_id = #{userId}, content = #{content}, " +
            "cover_image = #{coverImage}, status = #{status}, word_count = #{duration} " +
            "WHERE article_id = #{articleId}")
    int updateArticle(ArticleDAO articleDAO);

    @Delete("DELETE FROM articles WHERE article_id = #{articleId}")
    int deleteArticle(Integer articleId);

    @Select("SELECT * FROM articles WHERE user_id = #{userId}")
    List<ArticleDAO> selectArticlesByUser(Integer userId);

    @Select("SELECT * FROM articles ORDER BY create_time DESC LIMIT #{offset}, #{pageSize}")
    List<ArticleDAO> selectArticlesPageByTime(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM articles ORDER BY view_count DESC LIMIT #{offset}, #{pageSize}")
    List<ArticleDAO> selectArticlesPageByView(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM articles ORDER BY like_count DESC LIMIT #{offset}, #{pageSize}")
    List<ArticleDAO> selectArticlesPageByLike(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM articles WHERE status = 'PUBLISHED'")
    int selectTotalArticleCount();

    @SelectProvider(type = ArticleSqlProvider.class, method = "searchArticles")
    List<ArticleDAO> searchArticles(
        @Param("keyword") String keyword,
        @Param("offset") int offset,
        @Param("pageSize") int pageSize
    );

    @SelectProvider(type = ArticleSqlProvider.class, method = "searchArticlesCount")
    int searchArticlesCount(@Param("keyword") String keyword);

    /**
     * 根据排序条件获取文章分页列表
     */
    @SelectProvider(type = ArticleSqlProvider.class, method = "selectArticlesPage")
    List<ArticleDAO> selectArticlesPage(
            @Param("offset") int offset,
            @Param("pageSize") int pageSize,
            @Param("sortBy") String sortBy);

    /**
     * 检查用户是否已点赞文章
     */
    @Select("SELECT COUNT(*) FROM article_likes WHERE article_id = #{articleId} AND user_id = #{userId}")
    int checkUserLiked(@Param("articleId") Integer articleId, @Param("userId") Integer userId);

    /**
     * 添加文章点赞
     */
    @Insert("INSERT INTO article_likes (article_id, user_id) VALUES (#{articleId}, #{userId})")
    int insertArticleLike(@Param("articleId") Integer articleId, @Param("userId") Integer userId);

    /**
     * 删除文章点赞
     */
    @Delete("DELETE FROM article_likes WHERE article_id = #{articleId} AND user_id = #{userId}")
    int deleteArticleLike(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
    
    /**
     * 更新文章浏览量
     */
    @Update("UPDATE articles SET view_count = view_count + 1 WHERE article_id = #{articleId}")
    int incrementViewCount(@Param("articleId") Integer articleId);

    /**
     * 增加文章点赞数
     */
    @Update("UPDATE articles SET like_count = like_count + 1 WHERE article_id = #{articleId}")
    int incrementLikeCount(@Param("articleId") Integer articleId);

    /**
     * 减少文章点赞数
     */
    @Update("UPDATE articles SET like_count = like_count - 1 WHERE article_id = #{articleId}")
    int decrementLikeCount(@Param("articleId") Integer articleId);

    /**
     * 获取用户特定状态的文章
     */
    @Select("<script>" +
            "SELECT * FROM articles WHERE user_id = #{userId} " +
            "<if test='status != null'> AND status = #{status} </if>" +
            "ORDER BY create_time DESC LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<ArticleDAO> selectUserArticles(
            @Param("userId") Integer userId,
            @Param("status") String status,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 获取用户特定状态的文章数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM articles WHERE user_id = #{userId} " +
            "<if test='status != null'> AND status = #{status} </if>" +
            "</script>")
    int selectUserArticlesCount(@Param("userId") Integer userId, @Param("status") String status);
}
