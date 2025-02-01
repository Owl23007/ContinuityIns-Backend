package org.ContinuityIns.mapper;


import org.ContinuityIns.DTO.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    //添加评论
    @Insert("insert into comments (user_id,content,target_type,target_id) values (#{userId},#{comment},#{TargetType},#{ParentId})")
    void addComment(CommentDTO comment);

    //修改评论内容
    @Update("update comments set content = #{comment} where comment_id = #{CommentId}")
    void UpdateCommentContent(CommentDTO comment);

    //删除评论
    @Update("update comments set status = '删除' where comment_id = #{CommentId}")
    void deleteComment(int CommentId);

    //获取评论列表
    @Select("select * from comments where target_type = #{TargetType} and target_id = #{TargetId}")
    List<CommentDTO> getCommentList(String TargetType, int TargetId);

    //获取评论数据
    @Select("select * from comments where comment_id = #{CommentId}")
    void getComment(int CommentId);

    //点赞评论
    @Insert("insert into comment_likes (comment_id,user_id) values (#{CommentId},#{userId})")
    void likeComment(int CommentId,int userId);

    //取消点赞
    @Delete("delete from comment_likes where comment_id = #{CommentId} and user_id = #{userId}")
    void cancelLikeComment(int CommentId,int userId);

    //获取评论数据
    @Select("select count(*) from comment_likes where comment_id = #{CommentId}")
    Integer getCommentData(int CommentId);
}
