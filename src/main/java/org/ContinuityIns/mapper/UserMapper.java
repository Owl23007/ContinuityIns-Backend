package org.ContinuityIns.mapper;

import org.ContinuityIns.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    // 注册
    @Insert("INSERT INTO users(username, encr_password, email, create_time, update_time, salt, nickname) VALUES(#{username}, #{password}, #{email}, NOW(), NOW(), #{salt}, #{nickname})")
    void add(String username, String email, String password, String salt, String nickname);

    // 初始化用户信息
    @Update("UPDATE users SET signature = #{signature}, nickname = #{nickname} WHERE user_id = #{id}")
    void init(Integer id, String username, String signature, String nickname);

    // 根据用户id查询用户
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getOtherUserById(Integer userId);

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUserByUsername(String username);

    // 根据邮箱查询用户
    @Select("SELECT * FROM users WHERE email = #{email}")
    User getUserByEmail(String email);

    // 更新用户信息
    @Update("UPDATE users SET username = #{username}, signature = #{signature}, nickname = #{nickname}, update_time = NOW() WHERE user_id = #{userId}")
    void update(User u);

    // 更新用户状态
    @Update("UPDATE users SET status = #{status}, update_time = NOW() WHERE user_id = #{id}")
    void updateStatus(Integer id, String status);

    // 更新用户头像
    @Update("UPDATE users SET avatar_image = #{url}, update_time = NOW() WHERE user_id = #{id}")
    void updateAvatar(Integer id, String url);

    // 更新用户密码
    @Update("UPDATE users SET encr_password = #{rsaPassword}, update_time = NOW() WHERE user_id = #{id}")
    void updatePassword(Integer id, String rsaPassword, String salt);

    // 注销用户
    @Update("UPDATE users SET status = '注销', update_time = NOW() WHERE user_id = #{id}")
    void cancel(Integer id);

    //释放用户名
    @Update("UPDATE users SET username = CONCAT('注销', user_id), email = CONCAT('注销', user_id) WHERE user_id = #{userId}")
    void releaseUser(Integer id);

    // 删除未验证且已删除token的用户
    @Delete("DELETE FROM users WHERE status = '未验证' AND user_id NOT IN (SELECT user_id FROM email_verification_tokens)")
    void deleteUnverifiedUsers();

    // 根据邮箱获取用户名
    @Select("SELECT username FROM users WHERE email = #{identifier}")
    String getUsernameByEmail(String identifier);
}