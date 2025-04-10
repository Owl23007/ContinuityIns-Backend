package org.ContinuityIns.service.impl;

import org.ContinuityIns.DAO.UserDAO;
import org.ContinuityIns.mapper.FollowerMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowerMapper followerMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addFollower(Integer followerId, Integer followedId) {
        followerMapper.addFollower(followerId, followedId);
    }

    @Override
    public void deleteFollower(Integer followerId, Integer followedId) {
        followerMapper.deleteFollower(followerId, followedId);
    }

    @Override
    public List<UserDAO> selectFollowingsByUserId(Integer userId) {
        List<UserDAO>res = new ArrayList<>();
        for (Integer id : followerMapper.selectFolloweringIdsByUserId(userId)){
            res.add(userMapper.getUserById(id));
        }
        return res;
    }
}