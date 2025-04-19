package org.ContinuityIns.service;

public interface TokenService {
    /**
     * 生成token
     * @param userId 用户ID
     * @return 生成的token
     */
    String generateToken(Integer userId);

    /**
     * 验证token
     *@param  email 用户邮箱
     * @param  token 激活token
     * @return 生成的token
     */
    Boolean verifyToken( String email, String token);
}
