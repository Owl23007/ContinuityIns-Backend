package org.ContinuityIns.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ArticleSqlProvider {

    public String getDailyRecommends(Map<String, Object> params) {
        return new SQL() {{
            SELECT("a.*, " +
                   "(a.view_count * 1 + a.like_count * 2 + COALESCE(c.collection_count, 0) * 3) as heat_score");
            FROM("articles a");
            LEFT_OUTER_JOIN("(SELECT article_id, COUNT(*) as collection_count FROM article_collections GROUP BY article_id) c ON a.article_id = c.article_id");
            WHERE("a.status = 'PUBLISHED'");
            WHERE("a.create_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)");
            ORDER_BY("heat_score DESC, a.create_time DESC");
        }}.toString() + " LIMIT #{offset}, #{pageSize}";
    }

    public String getDailyRecommendsCount() {
        return new SQL() {{
            SELECT("COUNT(DISTINCT a.article_id)");
            FROM("articles a");
            WHERE("a.status = 'PUBLISHED'");
            WHERE("a.create_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)");
        }}.toString();
    }


    public String searchArticles(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("articles");
            WHERE("status = 'PUBLISHED'");
            if (params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
                WHERE("(title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%'))");
            }
            ORDER_BY("create_time DESC");
        }}.toString() + " LIMIT #{offset}, #{pageSize}";
    }

    public String searchArticlesCount(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("articles");
            WHERE("status = 'PUBLISHED'");
            if (params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
                WHERE("(title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%'))");
            }
        }}.toString();
    }

    public String selectUserArticles(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("articles");
            WHERE("user_id = #{userId}");
            if (params.get("status") != null) {
                WHERE("status = #{status}");
            }
            ORDER_BY("create_time DESC");
        }}.toString() + " LIMIT #{offset}, #{pageSize}";
    }

    public String selectUserArticlesCount(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("articles");
            WHERE("user_id = #{userId}");
            if (params.get("status") != null) {
                WHERE("status = #{status}");
            }
        }}.toString();
    }

    public String selectArticlesPage(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("articles");
            WHERE("status = 'PUBLISHED'");
            if (params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
                WHERE("(title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%'))");
            }
            ORDER_BY("#{sortBy} DESC");
        }}.toString() + " LIMIT #{offset}, #{pageSize}";
    }

    public String selectByUser(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("articles");
            WHERE("user_id = #{userId}");
            if (params.get("status") != null) {
                AND().WHERE("status = #{status}");
            }
            ORDER_BY("create_time DESC");
        }}.toString() + " LIMIT #{offset}, #{pageSize}";
    }
}