package org.ContinuityIns.service;


public interface VideoService {

    //TODO:实现视频服务接口 —— 用于对视频对象进行增删改查操作

    //添加视频
   void addVideo(String title, String url, String cover, String status, String description, String Duration);
}
