package org.ContinuityIns.controller;

import org.ContinuityIns.pojo.Result;
import org.ContinuityIns.pojo.User;
import org.ContinuityIns.service.impl.EmailServiceImpl;
import org.ContinuityIns.utils.AliOssUtil;
import org.ContinuityIns.utils.JwtUtil;
import org.ContinuityIns.utils.EncrUtil;
import org.ContinuityIns.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.mapper.EmailTokenMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    /**
     * 用户控制器
     * @Author: Oii Woof / MoXueYao
     * @Date: 2024/11/9
     * @Description: 进行用户的注册、登录、获取用户信息、更新用户信息、更新头像、更新密码、注销账号
     * @Tips: Controller层的方法主要是对用户的请求数据合法性进行验证，然后调用Service层的方法进行处理
     */

    @Autowired
    private UserService userService;

    @Autowired
    private EmailTokenMapper emailTokenMapper;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Value("${org.ContinuityIns.url}")
    private String rootLink;

    // 注册
    // @Pattern 验证参数的合法性
    @PostMapping("/register")
    public Result onRegister(@Pattern(regexp = "^\\S{5,16}$") String username, @Email String email, @NotNull String password) {
        // 检查用户是否已存在
        User u = userService.getUserByUsername(username);
        if (u != null && "未验证".equals(u.getStatus())) {
            //读取token
            try{
                String token = emailTokenMapper.getToken(u.getUserId()).getToken();
            }catch (Exception e){
                // 生成token
                String token = UUID.randomUUID().toString();
                emailService.insertToken(email, token);
            }
            // 发送验证邮件
            String token = emailTokenMapper.getToken(u.getUserId()).getToken();
            String subject = "欢迎注册ContinuityIns";
            String text = "欢迎注册ContinuityIns，点击链接激活账号:"+rootLink+"/user/active?email=" + email + "&token=" + token;
            emailService.sendEmail(email, subject, text);
            return Result.success("已重新发送，请查看邮箱以激活账号");
        }
        if (u != null && "正常".equals(u.getStatus())) {
            return Result.error("用户名已存在");
        }
        // 检查邮箱是否已经被注册
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return Result.error("邮箱已被注册");
        }

        //是否为英文+数字组合
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            return Result.error("用户名只能为英文和数字");
        }

        // 注册用户
        userService.register(username, email, password);

        // 查询用户
        u = userService.getUserByUsername(username);
        if (u == null || u.getUserId() == null) {
            return Result.error("用户注册失败");
        }

        // 生成token
        String token = UUID.randomUUID().toString();
        emailService.insertToken(email, token);

        // 发送验证邮件
        String subject = "欢迎注册ContinuityIns";
        String text = "欢迎注册ContinuityIns，点击链接激活账号：+"+rootLink+"/user/active?email=" + email + "&token=" + token;
        emailService.sendEmail(email, subject, text);

        return Result.success("注册成功，请查看邮箱以激活账号");
    }

    //邮箱验证
    @GetMapping("/active")
    public Result<String> onActive(@RequestParam String email, @RequestParam String token){
        // 验证邮箱
        emailService.verifyEmail(email, token);
        return Result.success("激活成功");
    }

    // 登录
    // @Pattern 验证参数的合法性
    @PostMapping("/login")
    public Result<String> onLogin(@NotNull String identifier, @NotNull String password) {
        // 判断是用户名还是邮箱
        String username = null;
        if (identifier.contains("@")) {
            username = userService.getUsernameByEmail(identifier);
        }else {
            username = identifier;
        }
        // 根据用户名查询用户
        User LoginUser = userService.getUserByUsername(username);
        if (LoginUser == null) {
            return Result.error("用户名不存在");
        }
        // 检查用户状态
        if (!Objects.equals(LoginUser.getStatus(), "正常")) {
            return Result.error("账号"+LoginUser.getStatus());
        }

        // 判断密码是否正确
        String salt = LoginUser.getSalt();
        if (Objects.equals(EncrUtil.getHash(password,salt), LoginUser.getEncrPassword())){
            // 登陆成功返回Token
            // 封装claims
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",LoginUser.getUserId());
            claims.put("username",LoginUser.getUsername());
            // 生成token
            String token = JwtUtil.genToken(claims);

            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    // 获取用户信息
    @GetMapping("/userinfo")
    public Result<User> onUserInfo(){
        // 根据用户名查询用户
        // 从ThreadLocal中获取用户名
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.getUserByUsername(username);
        return Result.success(u);
    }

    // 根据用户ID查询用户信息
    @GetMapping("/userinfoById")
    public Result onUserInfoById(@RequestParam Integer userId){
        // 根据用户id查询用户
        User u = userService.getUserById(userId);
        if(u==null)
            return Result.error("用户不存在");
        return Result.success(u);
    }

    // 更新用户信息
    @PutMapping("/update")
    public Result onUpdate(@RequestParam String nickname,@RequestParam String signature){
        // 校验参数
        if (!StringUtils.hasLength(nickname) || !StringUtils.hasLength(signature)){
            return Result.error("缺少参数。");
        }
        // 更新用户基本信息
        userService.updateInfo(nickname,signature);
        return Result.success();
    }

    // 更新头像
    @PatchMapping("/updateAvatar")
    public Result onUpdateAvatar(@RequestParam String url){
        // 校验参数
        if (!StringUtils.hasLength(url)){
            return Result.error("URL不能为空。");
        }
        // 更新用户头像
        userService.updateAvatar(url);
        return Result.success();
    }

    // 更新密码
    @PatchMapping("/updatePwd")
    public Result onUpdatePassword(@RequestBody Map<String,String> params){
        // 校验参数
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        if (!StringUtils.hasLength(old_pwd) || !StringUtils.hasLength(new_pwd) || !StringUtils.hasLength(re_pwd)){
            return Result.error("缺少参数。");
        }

        // 校验原密码是否正确
        Map<String,Object> claims = ThreadLocalUtil.get();
        User loginUser = userService.getUserByUsername((String) claims.get("username"));
        String salt = loginUser.getSalt();
        if (!EncrUtil.getHash(old_pwd,salt).equals(loginUser.getEncrPassword())){
            return Result.error("原密码错误。");
        }
        // 校验两次密码是否一致
        if (!new_pwd.equals(re_pwd)){
            return Result.error("两次密码不一致。");
        }
        // 更新密码
        userService.updatePassword(new_pwd);
        return Result.success();
    }

    // 注销账号
    @PostMapping("/deleteAcc")
    public Result onDeleteAcc(@NotNull String password){

        // 获取用户
        Map<String,Object> map = ThreadLocalUtil.get();

        Integer user_id = (Integer) map.get("id");
        String user_name = (String) map.get("username");

        String salt = userService.getUserByUsername(user_name).getSalt();
        User deleteUser = userService.getUserByUsername(user_name);

        System.out.println(password);
        // 校验密码
        if (!Objects.equals(EncrUtil.getHash(password,salt), deleteUser.getEncrPassword())){
            return Result.error("密码错误。");
        }


        // 切换用户状态
        userService.deleteAcc(user_id);

        //查看用户状态
        if (!Objects.equals(deleteUser.getStatus(), "注销")){
            return Result.error("注销失败。");
        }

        //生成校验码
        String Token = UUID.randomUUID().toString();
        emailService.insertToken(deleteUser.getEmail(), Token);

        //发送邮件
        String subject = "你的存续院账号已被注销";
        String text = "你的存续院账号已被注销，如非本人操作请于7天内联系管理员。\n " +
                      "账户ID:" + user_id+"\n" +
                      "校验码:"+Token+"\n"+
                      "联系邮箱:mailofowlwork@gmail.com\n"+
                      "请勿回复此邮件。";
        emailService.sendEmail(deleteUser.getEmail(), subject, text);

        return Result.success();
    }

    @GetMapping("/oss/policy")
    public Result<Map<String, String>> getOssPolicy() {
        // 生成上传策略（限制文件类型、大小、目录等）
        System.out.println("getOssPolicy");
        Map<String, String> policy = aliOssUtil.generatePolicy("avatars/", 2 * 1024 * 1024); // 限制2MB
        return Result.success(policy);
    }
}