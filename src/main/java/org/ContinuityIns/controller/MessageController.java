package org.ContinuityIns.controller;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Result sendMessage(@RequestBody Integer toId, String content){
        return messageService.sendMessage(toId, content);
    }
}
