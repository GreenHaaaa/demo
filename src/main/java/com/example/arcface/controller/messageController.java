package com.example.arcface.controller;

import com.example.arcface.domain.*;
import com.example.arcface.domain.VO.MsgList;
import com.example.arcface.reposity.MessageReposity;
import com.example.arcface.reposity.TaskReposity;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/msg")
@CrossOrigin(allowCredentials="true",
        origins = "*",
        methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS,RequestMethod.DELETE},
        allowedHeaders={"x-requested-with", "accept", "authorization", "content-type"},
        exposedHeaders={"access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "X-Frame-Options"})
public class messageController {
    private UserReposity userReposity;
    private MessageReposity messageReposity;
    private TaskReposity taskReposity;
    @Autowired
    public messageController(UserReposity userReposity,MessageReposity messageReposity,TaskReposity taskReposity)
    {
        this.userReposity = userReposity;
        this.messageReposity = messageReposity;
        this.taskReposity =taskReposity;
    }
    public User getUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user  = userReposity.findById(userDetails.getUsername()).get();
        return user;
    }
    @RequestMapping(value = "/getMsgOfCurrentUser",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody List<MsgList> getMsgs()
    {
        return MsgList.fromMessages(messageReposity.findAllByToUser(getUser()));
    }
    @RequestMapping(value = "/getmsgOfTask",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getMsgOfTask(@RequestParam Long taskid)
    {
        Optional<Task> optionalTask = taskReposity.findById(taskid);
        if(!optionalTask.isPresent()){
            return new ResponseEntity<>(new Info("task is not found",404), HttpStatus.NOT_FOUND);
        }
        Task task = optionalTask.get();
        return new ResponseEntity(MsgList.fromMessages(messageReposity.findAllByTask(task)),HttpStatus.OK);
    }

}
