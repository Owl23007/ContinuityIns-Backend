package org.ContinuityIns.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
    @Insert("insert into private_messages (sender_id, receiver_id, content,is_read,created_at) values (#{fromId},#{toId},#{content},0,now())")
    int sendMessage(Integer fromId, Integer toId, String content);
}
