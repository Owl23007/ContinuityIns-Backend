package org.ContinuityIns.task;

import org.ContinuityIns.mapper.TokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupTask {
    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpUnverifiedTokens() {
        userMapper.deleteUnverifiedUsers();
    }
}
