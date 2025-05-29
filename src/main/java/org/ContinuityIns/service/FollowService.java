package org.ContinuityIns.service;

import org.ContinuityIns.po.UserPO;

import java.util.List;

public interface FollowService {
    void addFollower(Integer followerId, Integer followedId);
    void deleteFollower(Integer followerId, Integer followedId);
    List<UserPO> selectFollowingsByUserId(Integer userId);
}
