package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserKeyMapper {
    @Insert("INSERT INTO user_keys(user_id, public_key, private_key) VALUES(#{userId}, #{publicKey}, #{privateKey})")
    void add(Integer userId, String publicKey, String privateKey);
}
