package org.ContinuityIns.controller;

import lombok.extern.slf4j.Slf4j;
import org.ContinuityIns.po.UserPO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.FollowService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/follower")
public class FollowerController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowService followService;
    @GetMapping("/add")
    public Result addFollower(@RequestParam Integer userId){
        UserPO userPO = userMapper.getUserById(userId);
        log.error("test");

        if (userPO == null){
            return Result.error("用户不存在");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer followerId = (Integer) map.get("id");
        followService.addFollower(followerId,userId);
        return Result.success();
    }
    @GetMapping("/query")
    public Result queryFollowers(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer followerId = (Integer) map.get("id");
        List<UserPO> userPOList = followService.selectFollowingsByUserId(followerId);
        return Result.success(userPOList);
    }
    @GetMapping("/delete")
    public Result deleteFollower(@RequestParam Integer userId){
        UserPO userPO = userMapper.getUserById(userId);
        if (userPO == null){
            return Result.error("用户不存在");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer followerId = (Integer) map.get("id");
        followService.deleteFollower(followerId,userId);
        return Result.success();
    }
}
