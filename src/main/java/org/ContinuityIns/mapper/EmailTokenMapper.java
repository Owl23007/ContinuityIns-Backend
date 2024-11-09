package org.ContinuityIns.mapper;

import org.ContinuityIns.pojo.EmailToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmailTokenMapper {
    @Insert("INSERT INTO email_verification_tokens(user_id, token) VALUES(#{userId}, #{token})")
    void insertToken(int userId, String token);

    @Select("SELECT token FROM email_verification_tokens WHERE user_id = #{userId}")
    EmailToken getToken(int userId);

    @Delete("DELETE FROM email_verification_tokens WHERE user_id = #{userId}")
    void deleteToken(int userId);

    @Delete("DELETE FROM email_verification_tokens WHERE user_id IN (SELECT user_id FROM users WHERE status = '未验证' AND created_at < NOW() - INTERVAL 7 DAY)")
    void deleteExpiredTokens();

}