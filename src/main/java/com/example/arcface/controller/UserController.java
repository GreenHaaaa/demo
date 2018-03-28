package com.example.arcface.controller;


import com.example.arcface.AFR_FSDK_FACEMODEL;
import com.example.arcface.demo.AFRTest;
import com.example.arcface.domain.*;
import com.example.arcface.reposity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "/s")
@CrossOrigin(allowCredentials="true",
        origins = "*",
        methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS,RequestMethod.DELETE},
        allowedHeaders={"x-requested-with", "accept", "authorization", "content-type"},
        exposedHeaders={"access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "X-Frame-Options"})
public class UserController {
    private UserReposity users;
    private ResourceReposity resourceReposity;
    private TaskReposity taskReposity;
    private MessageReposity messageReposity;
    private ProjectReposity projectReposity;

    @Autowired
    public UserController(UserReposity users, ResourceReposity resourceReposity, TaskReposity taskReposity,MessageReposity messageReposity,ProjectReposity projectReposity) {
        this.users = users;
        this.resourceReposity = resourceReposity;
        this.taskReposity = taskReposity;
        this.messageReposity = messageReposity;
        this.projectReposity = projectReposity;
    }
    public User getUser()
    {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = users.findById(userDetails.getUsername());
        if(!optionalUser.isPresent()) throw new SomethingNotFoundExcption("","user id");
        return optionalUser.get();
    }
    @RequestMapping(method = RequestMethod.GET)
    public Task sava()
    {
        User user = (User) users.findById("10001").get();
        user.setFaceFeature(AFRTest.getFeature("E:\\tools\\2.png"));
        Task task = new Task(1,user,"测试用任务",1,new Project(),"","",1);
        Resources resources= new Resources(1);
        resourceReposity.save(resources);
        taskReposity.save(task);
        task.getResourcesSet().add(resources);
        taskReposity.save(task);
        users.save(user);
        return task;
    }
    @RequestMapping(value="/auth", method=RequestMethod.GET)
    @PreAuthorize("hasRole('admin')")
     public Float authFeture()
    {
        User user = (User) users.findById("10000").get();
        Float s  =(float)1;
        try {
            AFR_FSDK_FACEMODEL facemodel = AFR_FSDK_FACEMODEL.fromByteArray(user.getFaceFeature());
            s = AFRTest.authFromFeatrue(facemodel);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    @RequestMapping(value = "/addmsg",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody Info addMessagge( @RequestBody Message m1,@RequestParam Long taskid)
    {


        User user  =getUser();
        /*
        * 这里传递数据格式
        * {
        *   "toUser":{"workNumber":"00111"},
        *   "messageContent":"1111"，
        *   "taskid":{"taskid":3}
        *  }
        *  无法对读到taskid的值
        */
        System.out.println(m1.getToUser());
        if(!users.findById(m1.getToUser().getWorkNumber()).isPresent()) return new Info("Touser ID is wrong",400);
        Message m;
        try{
            m  = new Message(user,m1.getMessageContent(), LocalDateTime.now().toString(),taskReposity.findById(taskid).get(),m1.getToUser());
        }catch (Exception e)
        {
            return new Info("taskid is wrong",400);
        }

        messageReposity.save(m);
        return new Info("success",200);
    }
    @ExceptionHandler(SomethingNotFoundExcption.class)
    public @ResponseBody Info getinfo(SomethingNotFoundExcption e)
    {
        return new Info(e.getItem()+"not exist",400);
    }
    @RequestMapping(value = "/getUserInfoById",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody UserInfo getUserInfo(@RequestParam String id)
    {
       Optional<User> optionalUser = users.findById(id);
       if(!optionalUser.isPresent()) throw new SomethingNotFoundExcption(id,"user id");
       User user =  optionalUser.get();
        return new UserInfo(user);
    }
    @RequestMapping(value = "/getTaskInfoById",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody Task getTaskInfo(@RequestParam  long id)
    {
        Optional<Task> optionalTask = taskReposity.findById(id);
        if(!optionalTask.isPresent()) throw new SomethingNotFoundExcption(String.valueOf(id),"task id ");
        return taskReposity.findById(id).get();
    }
    @RequestMapping(value = "addTask",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody Task addTask( @RequestBody Task task,@RequestParam String s)
    {

        UserDetails user1= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user  =users.findById( user1.getUsername()).get();
//        Project project  =new Project("测试用项目", "部门", 3, "2017-08-05", "2017-08-05", 2);
//        projectReposity.findById("bc30a51e623238a801623238cd480000");
        task.setTaskPublisher(user);
        task.setTaskStatus(0);
        task.setProject(projectReposity.findById(s).get());
        task.setTaskBegin(LocalDateTime.now().toString());
        Set<User>  userSet = new HashSet<>();
        userSet.add(user);
        task.setUsers(userSet);
        taskReposity.save(task);
        return  task;
    }
    @RequestMapping(value = "/setUserToTask",method = RequestMethod.POST)
     public @ResponseBody Info setUsers(@RequestBody UserList userSet,@RequestParam Long taskid)
    {

        Optional<Task> optionalTask = taskReposity.findById(taskid);
        if(!optionalTask.isPresent()) return  new Info("task not exist with userid "+taskid,404) ;
        Task task =  optionalTask.get();
        for (User user:userSet.getList())
        {
            if(!users.findById(user.getWorkNumber()).isPresent())
            {
                return new Info("user not exist with worknumber"+user.getWorkNumber(),404);
            }
           optionalTask.get().getUsers().add(users.findById(user.getWorkNumber()).get());
        }
//        task.setUsers(set);
        taskReposity.save(task);
        return  new Info("seuccess",200);

    }
    @RequestMapping(value = "/getTasksOfUser",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody  Set<Task> getTasksForUser()
    {
        UserDetails user1= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user  =users.findById( user1.getUsername()).get();
        return user.getTasks();
    }
    @RequestMapping(value = "/getUserMsg",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    List<UserInfo> getUsersMsg(@RequestParam int page)
    {
        Page<User> userPage  =users.findAll( PageRequest.of(page,6));
        List<User> userList = userPage.getContent();
        List<UserInfo> userInfos = new ArrayList<>();
        for(User a:userList)
        {
            userInfos.add(new UserInfo(a));
        }
        return userInfos;
    }
    @RequestMapping(value = "/getCountsOfUser",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody Long getCounts()
    {

        return users.count();
    }
    @RequestMapping(value = "/postPhoto",method = RequestMethod.POST)
    public @ResponseBody Info postPhoto(@RequestPart("file")MultipartFile file)
    {
        User user = getUser();
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        StringBuilder path = new StringBuilder();
        path.append("/usr/local/nginx/html/images/");
        path.append(user.getName());
        path.append("/1.");
        path.append(suffixName);
        File photo = new File(path.toString());
        if(!photo.getParentFile().exists()) photo.getParentFile().mkdirs();
        try{
            file.transferTo(photo);
        }catch (IOException e)
        {
            return new Info(e.getMessage(),500);
        }
        return new Info("success",200);
    }
    @RequestMapping(value = "/getInfoOfCurrentUser",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody UserInfo getInfoOf()
    {
        return  new UserInfo(getUser());

    }

}
