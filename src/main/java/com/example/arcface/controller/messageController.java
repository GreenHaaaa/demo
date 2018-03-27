package com.example.arcface.controller;

import com.example.arcface.domain.Message;
import com.example.arcface.domain.MsgList;
import com.example.arcface.domain.User;
import com.example.arcface.reposity.MessageReposity;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @Autowired
    public messageController(UserReposity userReposity,MessageReposity messageReposity)
    {
        this.userReposity = userReposity;
        this.messageReposity = messageReposity;
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

}
