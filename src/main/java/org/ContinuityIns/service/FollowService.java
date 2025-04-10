package org.ContinuityIns.service;

import org.ContinuityIns.DAO.UserDAO;

import java.util.List;

public interface FollowService {
    void addFollower(Integer followerId, Integer followedId);
    void deleteFollower(Integer followerId, Integer followedId);
    List<UserDAO> selectFollowingsByUserId(Integer userId);
}
