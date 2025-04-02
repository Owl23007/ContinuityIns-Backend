package org.ContinuityIns.DAO;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class UserTokenDAO {
    private Integer userId;

    private String token;

    private LocalDateTime createTime;

    public long getCreateTimeToLong() {
        // 将 LocalDateTime 转换为 +8 时区的 ZonedDateTime
        ZonedDateTime zonedDateTime = createTime.atZone(ZoneId.of("Asia/Shanghai"));
        return zonedDateTime.toInstant().toEpochMilli();
    }
}
