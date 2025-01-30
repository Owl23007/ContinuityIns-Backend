package org.ContinuityIns.service.impl;

import org.ContinuityIns.service.VideoService;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public void addVideo(String title, String url, String cover, String status, String description, String Duration) {
        return;
    }
    //TODO:实现视频服务接口 —— 用于对视频对象进行增删改查操作
}