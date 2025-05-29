package org.ContinuityIns.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer userId;
    private String username;
    private String nickname;
    private String avatarImage;
    // 可根据前端需求添加更多字段，如脱敏、格式化等
}
