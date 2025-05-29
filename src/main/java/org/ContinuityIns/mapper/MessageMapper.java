package org.ContinuityIns.mapper;

import org.ContinuityIns.po.MessagePO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("insert into private_messages (sender_id, receiver_id, content,is_read,created_at) values (#{fromId},#{toId},#{content},0,now())")
    int sendMessage(Integer fromId, Integer toId, String content);

    @Select("select * from private_messages where receiver_id=#{userId}")
    List<MessagePO> selectByUserId(Integer userId);

    @Select("select * from private_messages where receiver_id=#{userId} and is_read=1")
    List<MessagePO> selectUnreadByUserId(Integer userId);
}
