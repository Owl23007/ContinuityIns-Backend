package org.ContinuityIns.Task;

import org.ContinuityIns.mapper.EmailTokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupTask {
    @Autowired
    private EmailTokenMapper emailTokenMapper;
    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpUnverifiedTokens() {
        emailTokenMapper.deleteExpiredTokens();
        userMapper.deleteUnverifiedUsers();
    }
}
