package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ViewMapper {
    
    /**
     * 检查用户是否已查看过文章
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 如果已查看过返回1，否则返回0
     */
    @Select("SELECT COUNT(*) FROM article_view_records WHERE article_id = #{articleId} AND user_id = #{userId}")
    int checkUserViewed(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
    
    /**
     * 插入文章浏览记录
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 受影响的行数
     */
    @Insert("INSERT INTO article_view_records (article_id, user_id) VALUES (#{articleId}, #{userId})")
    int insertArticleView(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
    
    /**
     * 更新文章浏览记录
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 受影响的行数
     */
    @Update("UPDATE article_view_records SET view_time = CURRENT_TIMESTAMP WHERE article_id = #{articleId} AND user_id = #{userId}")
    int updateArticleView(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
    
    /**
     * 获取文章的浏览次数
     * @param articleId 文章ID
     * @return 浏览次数
     */
    @Select("SELECT COUNT(*) FROM article_view_records WHERE article_id = #{articleId}")
    int getArticleViewCount(@Param("articleId") Integer articleId);
}
