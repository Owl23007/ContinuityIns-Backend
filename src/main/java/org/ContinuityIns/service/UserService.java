package org.ContinuityIns.service;

import org.ContinuityIns.po.UserPO;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    /**
     * 用户注册
     * 
     * @param username 用户名
     * @param email    邮箱
     * @param password 密码
     * @return 注册结果
     *         - 成功: Result.success("注册成功，请查收激活邮件")
     *         - 失败: Result.error("用户名已存在")
     *         Result.error("邮箱已被注册")
     *         Result.error("注册失败")
     */
    Result<Void> register(String username, String email, String password);

    /**
     * 激活账号
     * 
     * @param email 用户邮箱
     * @param token 激活token
     * @return 激活结果
     *         - 成功: Result.success("账号激活成功")
     *         - 失败: Result.error("激活链接无效或已过期")
     *         Result.error("账号已激活")
     *         Result.error("账号不存在")
     */
    Result<String> activateAccount(String email, String token);

    /**
     * 用户登录
     * 
     * @param identifier 登录标识（用户名或邮箱）
     * @param password   密码
     * @param ipAddress  IP地址
     * @return 登录结果
     *         - 成功: Result.success(token)
     *         - 失败: Result.error("用户名或密码错误")
     *         Result.error("账号未激活")
     *         Result.error("账号已被禁用")
     */
    Result<String> login(String identifier, String password, String ipAddress);

    /**
     * 获取当前用户信息
     * 
     * @return 用户信息
     *         - 成功: Result.success(userPO)
     *         - 失败: Result.error("未登录")
     */
    Result<UserPO> getUserInfo();

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     *         - 成功: Result.success(userInfo)
     *         - 失败: Result.error("用户不存在")
     */
    Result<UserPO> getUserInfoById(Integer userId);

    /**
     * 更新用户信息
     * 
     * @param nickname  昵称
     * @param signature 个性签名
     * @return 更新结果
     *         - 成功: Result.success("更新成功")
     *         - 失败: Result.error("更新失败")
     */
    Result<Void> updateUserInfo(String nickname, String signature);

    /**
     * 更新头像
     * 
     * @param url 头像URL
     * @return 更新结果
     *         - 成功: Result.success("头像更新成功")
     *         - 失败: Result.error("头像更新失败")
     */
    Result<Void> updateAvatar(String url);

    /**
     * 更新背景图
     * 
     * @param url 背景图URL
     * @return 更新结果
     *         - 成功: Result.success("背景图更新成功")
     *         - 失败: Result.error("背景图更新失败")
     */
    Result<Void> updateBackground(String url);

    /**
     * 更新密码
     * 
     * @param params 包含旧密码和新密码的参数Map
     * @return 更新结果
     *         - 成功: Result.success("密码更新成功")
     *         - 失败: Result.error("旧密码错误")
     *         Result.error("新密码格式不正确")
     */
    Result<Void> updatePassword(Map<String, String> params);

    /**
     * 删除账号
     * 
     * @param password 密码
     * @return 删除结果
     *         - 成功: Result.success("账号已删除")
     *         - 失败: Result.error("密码错误")
     *         Result.error("删除失败")
     */
    Result<Void> deleteAccount(String password);

    /**
     * 获取OSS上传策略
     * 
     * @param type 上传类型
     * @return OSS策略
     *         - 成功: Result.success(ossPolicy)
     *         - 失败: Result.error("获取上传策略失败")
     */
    Result<Map<String, Object>> getOssPolicy(String type);

    /**
     * 发送密码重置邮件
     * 
     * @param email 用户邮箱
     * @return 发送结果
     *         - 成功: Result.success("重置密码邮件已发送")
     *         - 失败: Result.error("用户不存在")
     *         Result.error("邮件发送失败")
     *         Result.error("60秒内只能发送一次重置邮件")
     */
    Result<Void> sendResetEmail(String email);

    /**
     * 重置密码
     * 
     * @param email    用户邮箱
     * @param token    重置密码token
     * @param password 新密码
     * @return 重置结果
     *         - 成功: Result.success("密码重置成功")
     *         - 失败: Result.error("重置链接无效或已过期")
     *         Result.error("用户不存在")
     *         Result.error("密码格式不正确")
     */
    Result<Void> resetPassword(String email, String token, String password);

    /**
     * 检查用户是否有效
     * 
     * @param userId 用户ID
     * @return 是否有效
     */
    Boolean isVaildate(Integer userId);

    /**
     * 更新用户设置
     * 
     * @param theme                   主题设置
     * @param notificationPreferences 通知偏好
     * @param privacySettings         隐私设置
     * @return 更新结果
     */
    Result<Void> updateUserSettings(UserPO.UserTheme theme, String notificationPreferences, String privacySettings);
}
