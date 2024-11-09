package org.ContinuityIns.service.impl;

import org.ContinuityIns.mapper.VideoMapper;
import org.ContinuityIns.pojo.Video;
import org.ContinuityIns.service.VideoService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public void addVideo(String title, String url, String cover, String status, String description, String Duration) {
        return;
    }
    //TODO:实现视频服务接口 —— 用于对视频对象进行增删改查操作
}