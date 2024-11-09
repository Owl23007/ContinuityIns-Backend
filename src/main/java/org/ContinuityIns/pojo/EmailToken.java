package org.ContinuityIns.pojo;

import lombok.Data;
import lombok.Getter;

@Data
public class EmailToken {
    private Integer userId;
    private String email;
    @Getter
    private String token;
    private Long expireTime;


}
