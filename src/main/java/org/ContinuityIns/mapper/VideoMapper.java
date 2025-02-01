package org.ContinuityIns.mapper;

import org.ContinuityIns.DTO.VideoDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {
    @Insert("insert into videos (video_id, user_id, title, create_time, update_time, status, cover_image, video_url, duration) values (#{videoId}, #{userId}, #{title}, #{createTime}, #{updateTime}, #{status}, #{coverImage}, #{videoUrl}, #{duration})")
    void add(VideoDTO video);

    @Select("select * from videos")
    List<VideoDTO> list();

    @Select("select * from videos where video_id = #{videoId}")
    VideoDTO getVideoById(Integer videoId);

    @Update("update videos set title = #{title}, update_time = #{updateTime}, status = #{status}, cover_image = #{coverImage}, video_url = #{videoUrl}, duration = #{duration} where video_id = #{videoId}")
    void update(VideoDTO video);

    @Delete("delete from videos where video_id = #{videoId}")
    void delete(Integer videoId);
}