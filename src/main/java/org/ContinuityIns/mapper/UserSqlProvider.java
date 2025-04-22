package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public class UserSqlProvider {
    public String searchUsers(
        @Param("keyword") String keyword,
        @Param("dateStart") LocalDateTime dateStart,
        @Param("dateEnd") LocalDateTime dateEnd
    ) {
        return "SELECT u.*, us.theme, us.notification_preferences, us.privacy_settings " +
                "FROM users u " +
                "LEFT JOIN user_settings us ON u.user_id = us.user_id " +
                "WHERE u.username LIKE CONCAT('%', #{keyword}, '%') " +
                "AND u.create_time BETWEEN #{dateStart} AND #{dateEnd} ";
    }
}
