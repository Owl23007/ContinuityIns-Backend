package org.ContinuityIns.interceptors;

import jakarta.validation.constraints.NotNull;
import org.ContinuityIns.DTO.UserDTO;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.JwtUtil;
import org.ContinuityIns.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//请求拦截器，返回true代表放行，返回false代表不放行
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final UserMapper userMapper;

    public LoginInterceptor(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * 该实现总是返回 {@code true}.
     * @param request 当前的 HTTP 请求
     * @param response 当前的 HTTP 响应
     * @param handler 要执行的处理器，用于类型和/或实例评估
     * @return 如果执行链应继续下一个拦截器或处理器本身，则返回 {@code true}.
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 令牌验证

        String token = request.getHeader("Authorization");
        String data = request.getQueryString();
        if (token != null && token.startsWith("Duel ")) {
            token = token.substring(5); // 去掉 "Duel " 前缀
        }
        try {
            // 解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 将token携带的信息存放在ThreadLocal中
            ThreadLocalUtil.set(claims);

            // 放行
            return true;
        } catch (Exception e){
            // 拦截
            response.setStatus(401);
            return false;
        }
    }
    // 后置处理
    // 删除ThreadLocal中的数据以防止内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 移除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
