package org.ContinuityIns.controller;

import org.ContinuityIns.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {
    /**
     * 视频控制器
     * @Author  Oii Woof
     * @Date 2025/1/16 23:00
     * @Description 进行视频的增删改查
     */

    //TODO:实现视频控制器 —— 用于对视频对象进行增删改查操作,仿照文章控制器的实现
    @Autowired
    private VideoService videoService;



}