package org.ContinuityIns.entity;

import lombok.Data;

@Data
public class UserKey {
    private Integer id;
    private Integer userId;
    private String publicKey;
    private String privateKey;
}