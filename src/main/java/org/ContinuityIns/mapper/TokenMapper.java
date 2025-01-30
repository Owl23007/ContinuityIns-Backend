package org.ContinuityIns.mapper;

import org.ContinuityIns.entity.UserToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {
    @Insert("INSERT INTO user_tokens(user_id, token) VALUES(#{userId}, #{token})")
    void insertToken(int userId, String token);

    @Select("SELECT token FROM user_tokens WHERE user_id = #{userId}")
    UserToken getToken(int userId);

    @Delete("DELETE FROM user_tokens WHERE user_id = #{userId}")
    void deleteToken(int userId);

    @Delete("DELETE FROM user_tokens WHERE create_time < DATE_SUB(NOW(), INTERVAL 7 DAY)")
    void deleteExpiredTokens();
}