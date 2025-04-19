package org.ContinuityIns.controller;

import org.ContinuityIns.DAO.MessageDAO;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Result sendMessage(@RequestBody Integer toId, String content){
        return messageService.sendMessage(toId, content);
    }

    @GetMapping("/get")
    public Result<List<MessageDAO>> getMessage(){
        return messageService.getMessage();
    }

    @GetMapping("/getUnread")
    public Result<List<MessageDAO>> getUnreadMessage(){
        return messageService.getUnreadMessage();
    }
}
