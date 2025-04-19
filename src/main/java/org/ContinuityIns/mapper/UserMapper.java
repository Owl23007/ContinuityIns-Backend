package org.ContinuityIns.mapper;

import org.ContinuityIns.DAO.UserDAO;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface UserMapper {

    // 注册
    @Insert("INSERT INTO users(username, encr_password, email, create_time, update_time, salt, nickname,signature) " +
            "VALUES(#{username}, #{password}, #{email}, NOW(), NOW(), #{salt}, #{nickname}, '这个人很懒，什么都没有留下')")
    void add(@Param("username") String username,
             @Param("email") String email,
             @Param("password") String password,
             @Param("salt") String salt,
             @Param("nickname") String nickname);

    // 初始化用户信息
    @Update("UPDATE users SET signature = #{signature}, nickname = #{nickname} WHERE user_id = #{id}")
    void init(@Param("id") Integer id,
              @Param("signature") String signature,
              @Param("nickname") String nickname);

    // 根据用户id查询用户
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    UserDAO getUserById(@Param("userId") Integer userId);

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    UserDAO getUserByUsername(@Param("username") String username);

    // 根据邮箱查询用户
    @Select("SELECT * FROM users WHERE email = #{email}")
    UserDAO getUserByEmail(@Param("email") String email);

    // 更新用户信息
    @Update("UPDATE users SET  signature = #{signature}, nickname = #{nickname}, update_time = NOW() WHERE user_id = #{userId}")
    void update(@Param("userId") Integer userId,
                @Param("signature") String signature,
                @Param("nickname") String nickname);

    // 更新用户状态
    @Update("UPDATE users SET status = #{status}, update_time = NOW() WHERE user_id = #{id}")
    void updateStatus(@Param("id") Integer id,
                      @Param("status") UserDAO.UserStatus status);

    // 更新用户头像
    @Update("UPDATE users SET avatar_image = #{url}, update_time = NOW() WHERE user_id = #{id}")
    void updateAvatar(@Param("id") Integer id,
                      @Param("url") String url);

    // 更新用户背景图
    @Update("UPDATE users SET background_image = #{url}, update_time = NOW() WHERE user_id = #{id}")
    void updateBackground(@Param("id") Integer id,
                          @Param("url") String url);

    // 更新用户密码
    @Update("UPDATE users SET encr_password = #{rsaPassword}, salt = #{salt}, update_time = NOW() WHERE user_id = #{id}")
    void updatePassword(@Param("id") Integer id,
                        @Param("rsaPassword") String rsaPassword,
                        @Param("salt") String salt);

    // 注销用户
    @Update("UPDATE users SET status ='DEACTIVATED', username = CONCAT('注销', user_id), email = CONCAT('注销', user_id) ,update_time = NOW() WHERE user_id = #{id}")
    void cancel(@Param("id") Integer id);

    // 更新最后登录信息
    @Update("UPDATE users SET last_login = #{lastLogin}, last_login_ip = #{ip}, update_time = NOW() WHERE user_id = #{userId}")
    void updateLastLogin(@Param("userId") Integer userId, @Param("lastLogin") java.util.Date lastLogin, @Param("ip") String ip);

    // 根据邮箱获取用户名
    @Select("SELECT username FROM users WHERE email = #{identifier}")
    String getUsernameByEmail(@Param("identifier") String identifier);

    // 根据用户id获取盐值
    @Select("SELECT salt FROM users WHERE user_id = #{id}")
    String getSaltByUserId(@Param("id") Integer id);

    // 根据用户id获取用户密码
    @Select("SELECT encr_password FROM users WHERE user_id = #{id}")
    String getEncrPasswordByUserId(@Param("id") Integer id);

    @Insert("INSERT INTO user_settings(user_id, theme, notification_preferences, privacy_settings) " +
            "VALUES(#{userId}, 'SYSTEM', '{}', '{}')")
    void initUserSettings(@Param("userId") Integer userId);

    @Update("UPDATE user_settings SET theme = #{theme}, " +
            "notification_preferences = #{notificationPreferences}, " +
            "privacy_settings = #{privacySettings}, " +
            "update_time = NOW() " +
            "WHERE user_id = #{userId}")
    void updateUserSettings(@Param("userId") Integer userId,
                          @Param("theme") String theme,
                          @Param("notificationPreferences") String notificationPreferences,
                          @Param("privacySettings") String privacySettings);

    @Select("SELECT u.*, us.theme, us.notification_preferences, us.privacy_settings " +
            "FROM users u " +
            "LEFT JOIN user_settings us ON u.user_id = us.user_id " +
            "WHERE u.user_id = #{userId}")
    UserDAO getUserWithSettingsById(@Param("userId") Integer userId);
    
    @Delete("DELETE FROM users WHERE status = 'UNVERIFIED' AND create_time < DATE_SUB(NOW(), INTERVAL 3 DAY)")
    void deleteUnverifiedUsers();

    @Update("UPDATE users SET token = #{token}, token_expiration = #{expiryDate} WHERE user_id = #{userId}")
    void updateToken(Integer userId, String token, Date expiryDate);
}
