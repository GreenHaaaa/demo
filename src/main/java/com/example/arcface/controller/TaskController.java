package com.example.arcface.controller;

import com.example.arcface.domain.SomethingNotFoundExcption;
import com.example.arcface.domain.TaskUserInfo;
import com.example.arcface.domain.User;
import com.example.arcface.domain.VO.TaskInfo;
import com.example.arcface.domain.VO.TaskUserDetail;
import com.example.arcface.reposity.TaskInfoResposity;
import com.example.arcface.reposity.TaskReposity;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/taskdetail")
public class TaskController {
    private TaskReposity taskReposity;
    private UserReposity userReposity;
    private TaskInfoResposity taskInfoResposity;

    @Autowired
    public TaskController(TaskReposity taskReposity, UserReposity userReposity,TaskInfoResposity taskInfoResposity) {
        this.taskReposity = taskReposity;
        this.userReposity = userReposity;
        this.taskInfoResposity = taskInfoResposity;
    }
    private User getUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userReposity.findById(userDetails.getUsername());
        if(!optionalUser.isPresent()){ throw new SomethingNotFoundExcption("","user id");}
        return optionalUser.get();
    }
    @RequestMapping(value = "/getUserWorkTimeOfTask",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<TaskUserDetail>> getUserWorkTimeOfTask(@RequestParam Long taskid){
        List<TaskUserDetail> list = new ArrayList<>();
        if(!taskReposity.findById(taskid).isPresent()){
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        };
        List<TaskUserInfo> list1 = taskInfoResposity.findAllByTaskId(taskid);
        for(TaskUserInfo taskInfo :list1){
            User user = userReposity.getOne(taskInfo.getUserId());
            TaskUserDetail taskUserDetail = new TaskUserDetail(user.getName(),user.getCompany(),taskInfo.getWorkHours(),taskInfo.getUserId());
            list.add(taskUserDetail);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @RequestMapping(value = "/addUserWorkTime",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public  @ResponseBody ResponseEntity  addUserWorkTime(@RequestParam Long taskid,@RequestParam int time)
    {
      TaskUserInfo taskInfo =   taskInfoResposity.getAllByUserIdAndTaskId(getUser().getWorkNumber(),taskid);
      taskInfo.setWorkHours(taskInfo.getWorkHours()+time);
      taskInfoResposity.save(taskInfo);
      return new ResponseEntity(HttpStatus.OK);
    }


}
