package org.ContinuityIns.mapper;

import org.ContinuityIns.DTO.UserDTO;
import org.apache.ibatis.annotations.*;

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
    UserDTO getUserById(@Param("userId") Integer userId);

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    UserDTO getUserByUsername(@Param("username") String username);

    // 根据邮箱查询用户
    @Select("SELECT * FROM users WHERE email = #{email}")
    UserDTO getUserByEmail(@Param("email") String email);

    // 更新用户信息
    @Update("UPDATE users SET  signature = #{signature}, nickname = #{nickname}, update_time = NOW() WHERE user_id = #{userId}")
    void update(@Param("userId") Integer userId,
                @Param("signature") String signature,
                @Param("nickname") String nickname);

    // 更新用户状态
    @Update("UPDATE users SET status = #{status}, update_time = NOW() WHERE user_id = #{id}")
    void updateStatus(@Param("id") Integer id,
                      @Param("status") UserDTO.UserStatus status);

    // 更新用户头像
    @Update("UPDATE users SET avatar_image = #{url}, update_time = NOW() WHERE user_id = #{id}")
    void updateAvatar(@Param("id") Integer id,
                      @Param("url") String url);

    // 更新用户密码
    @Update("UPDATE users SET encr_password = #{rsaPassword}, salt = #{salt}, update_time = NOW() WHERE user_id = #{id}")
    void updatePassword(@Param("id") Integer id,
                        @Param("rsaPassword") String rsaPassword,
                        @Param("salt") String salt);

    // 注销用户
    @Update("UPDATE users SET status ='DEACTIVATED', username = CONCAT('注销', user_id), email = CONCAT('注销', user_id) ,update_time = NOW() WHERE user_id = #{id}")
    void cancel(@Param("id") Integer id);


    // 删除未验证且已删除token的用户
    @Delete("DELETE FROM users WHERE status = 'UNVERIFIED' AND user_id NOT IN (SELECT user_id FROM user_tokens)")
    void deleteUnverifiedUsers();

    // 根据邮箱获取用户名
    @Select("SELECT username FROM users WHERE email = #{identifier}")
    String getUsernameByEmail(@Param("identifier") String identifier);

    // 根据用户id获取盐值
    @Select("SELECT salt FROM users WHERE user_id = #{id}")
    String getSaltByUserId(@Param("id") Integer id);

    // 根据用户id获取用户密码
    @Select("SELECT encr_password FROM users WHERE user_id = #{id}")
    String getEncrPasswordByUserId(@Param("id") Integer id);
}