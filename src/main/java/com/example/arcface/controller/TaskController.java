package com.example.arcface.controller;

import com.example.arcface.domain.Info;
import com.example.arcface.domain.SomethingNotFoundExcption;
import com.example.arcface.domain.TaskUserInfo;
import com.example.arcface.domain.User;
import com.example.arcface.domain.VO.TaskInfo;
import com.example.arcface.domain.VO.TaskUserDetail;
import com.example.arcface.reposity.TaskInfoResposity;
import com.example.arcface.reposity.TaskReposity;
import com.example.arcface.reposity.UserReposity;
import com.example.arcface.utils.FileWalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/taskdetail")
public class TaskController {
    private static final String sourcePath = "/usr/local/nginx/html/images/sources";
    private static final  String TEMPPATH="/usr/local/nginx/html/images/temp";
    private final  static String USER_ROLE="ROLE_USER";
    private final  static String ADMIN_ROLE="ROLE_ADMIN";
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
    @GetMapping(value = "/getFileList")
    public @ResponseBody ResponseEntity<List<String>> getFileList(@RequestParam Long id)
    {
        if(!taskReposity.findById(id).isPresent()){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        };
        System.out.println(sourcePath+"/"+id+"/");
       return new ResponseEntity<>( FileWalkUtil.getFileList(sourcePath+"/"+id+"/"),HttpStatus.OK);
    }
    @GetMapping(value = "/getTempList")
    public @ResponseBody ResponseEntity<List<String>> getTempList(@RequestParam Long id) {
        if(!taskReposity.findById(id).isPresent()){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        };
        return new ResponseEntity<>( FileWalkUtil.getFileList(TEMPPATH+"/"+id+"/"),HttpStatus.OK);

    }
    @PostMapping(value = "/reviewed")
    public @ResponseBody ResponseEntity<Info> reViewed(@RequestParam int isOk,@RequestParam Long id,@RequestParam String filename){
        User user = getUser();
        if(user.getRole().equals(ADMIN_ROLE)){
            if(isOk==1){
                FileWalkUtil.copyFile(TEMPPATH+"/"+id+"/"+filename,sourcePath+"/"+id+"/"+filename);
            }
        }
        return new ResponseEntity<>(new Info("you are not admin",403),HttpStatus.FORBIDDEN);
    }


}
