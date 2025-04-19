package org.ContinuityIns.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ArticleSqlProvider {

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
}