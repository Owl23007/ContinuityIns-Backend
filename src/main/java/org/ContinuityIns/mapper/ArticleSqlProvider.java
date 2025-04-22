package org.ContinuityIns.mapper;

import org.apache.ibatis.jdbc.SQL;
import java.util.List;
import java.util.Map;

public class ArticleSqlProvider {

    public String getDailyRecommends(Map<String, Object> params) {
        return new SQL() {{
            SELECT("DISTINCT a.*");
            FROM("articles a");
            LEFT_OUTER_JOIN("article_likes al ON a.article_id = al.article_id");
            LEFT_OUTER_JOIN("article_view_records av ON a.article_id = av.article_id");
            WHERE("a.status = 'PUBLISHED'");
            WHERE("a.create_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)");
            // 计算综合评分: 点赞数 * 0.6 + 浏览量 * 0.4
            ORDER_BY("(COALESCE(a.like_count, 0) * 0.6 + COALESCE(a.view_count, 0) * 0.4) DESC");
            ORDER_BY("a.create_time DESC");
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
            SELECT("DISTINCT a.*");
            FROM("articles a");
            LEFT_OUTER_JOIN("article_categories ac ON a.article_id = ac.article_id");
            LEFT_OUTER_JOIN("tag_articles at ON a.article_id = at.article_id");
            WHERE("a.status = 'PUBLISHED'");

            // 关键词搜索
            if (params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
                WHERE("(a.title LIKE CONCAT('%', #{keyword}, '%') OR a.content LIKE CONCAT('%', #{keyword}, '%'))");
            }

            // 分类过滤
            if (params.get("category") != null && !params.get("category").toString().isEmpty()) {
                WHERE("ac.category_name = #{category}");
            }

            // 标签过滤
            @SuppressWarnings("unchecked")
            List<String> tags = (List<String>) params.get("tags");
            if (tags != null && !tags.isEmpty()) {
                WHERE("at.tag_name IN <foreach item='tag' collection='tags' open='(' separator=',' close=')'>#{tag}</foreach>");
            }

            // 时间范围过滤
            if (params.get("dateStart") != null) {
                WHERE("a.create_time >= #{dateStart}");
            }
            if (params.get("dateEnd") != null) {
                WHERE("a.create_time <= #{dateEnd}");
            }

            // 排序
            String sort = (String) params.get("sort");
            if (sort != null) {
                switch (sort) {
                    case "latest":
                        ORDER_BY("a.create_time DESC");
                        break;
                    case "views":
                        ORDER_BY("a.view_count DESC");
                        break;
                    case "likes":
                        ORDER_BY("a.like_count DESC");
                        break;
                    default:
                        ORDER_BY("a.create_time DESC");
                }
            } else {
                ORDER_BY("a.create_time DESC");
            }
        }}.toString();
    }

    public String searchArticlesCount(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(DISTINCT a.article_id)");
            FROM("articles a");
            LEFT_OUTER_JOIN("article_categories ac ON a.article_id = ac.article_id");
            LEFT_OUTER_JOIN("tag_articles at ON a.article_id = at.article_id");
            WHERE("a.status = 'PUBLISHED'");

            if (params.get("keyword") != null && !params.get("keyword").toString().isEmpty()) {
                WHERE("(a.title LIKE CONCAT('%', #{keyword}, '%') OR a.content LIKE CONCAT('%', #{keyword}, '%'))");
            }

            if (params.get("category") != null && !params.get("category").toString().isEmpty()) {
                WHERE("ac.category_name = #{category}");
            }

            @SuppressWarnings("unchecked")
            List<String> tags = (List<String>) params.get("tags");
            if (tags != null && !tags.isEmpty()) {
                WHERE("at.tag_name IN <foreach item='tag' collection='tags' open='(' separator=',' close=')'>#{tag}</foreach>");
            }

            if (params.get("dateStart") != null) {
                WHERE("a.create_time >= #{dateStart}");
            }
            if (params.get("dateEnd") != null) {
                WHERE("a.create_time <= #{dateEnd}");
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
