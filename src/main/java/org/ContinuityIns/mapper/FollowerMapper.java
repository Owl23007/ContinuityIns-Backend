package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowerMapper {
    @Insert("INSERT INTO user_follows(follower_id, following_id) VALUES(#{followerId}, #{followedId})")
    void addFollower(Integer followerId, Integer followedId);

    @Delete("DELETE FROM user_follows WHERE follower_id = #{followerId} AND following_id = #{followedId}")
    void deleteFollower(Integer followerId, Integer followedId);

    @Select("SELECT following_id FROM user_follows WHERE follower_id = #{userId}")
    List<Integer> selectFolloweringIdsByUserId(Integer userId);
}
