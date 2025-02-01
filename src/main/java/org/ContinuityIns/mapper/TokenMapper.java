package org.ContinuityIns.mapper;

import org.ContinuityIns.DTO.UserTokenDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {
    @Insert("INSERT INTO user_tokens(user_id, token,create_time) VALUES(#{userId}, #{token},NOW())")
    void insertToken(int userId, String token);

    @Select("SELECT * FROM user_tokens WHERE user_id = #{userId}")
    UserTokenDTO getToken(int userId);

    @Delete("DELETE FROM user_tokens WHERE user_id = #{userId}")
    void deleteToken(int userId);

    @Delete("DELETE FROM user_tokens WHERE create_time < DATE_SUB(NOW(), INTERVAL 7 DAY)")
    void deleteExpiredTokens();
}