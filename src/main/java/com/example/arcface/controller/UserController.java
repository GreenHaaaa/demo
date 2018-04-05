package com.example.arcface.controller;


import com.example.arcface.AFR_FSDK_FACEMODEL;
import com.example.arcface.config.constraints.ReturnMessaage;
import com.example.arcface.demo.AFRTest;
import com.example.arcface.domain.*;
import com.example.arcface.domain.VO.TaskInfo;
import com.example.arcface.domain.VO.UserInfoWithLevel;
import com.example.arcface.reposity.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
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
    private TimeStampReposity timeStampReposity;
    private UserReposity users;
    private ResourceReposity resourceReposity;
    private TaskReposity taskReposity;
    private MessageReposity messageReposity;
    private ProjectReposity projectReposity;
    private AuthorityReposity authorityReposity;

    @Autowired
    public UserController(TimeStampReposity timeStampReposity,UserReposity users, ResourceReposity resourceReposity, TaskReposity taskReposity,MessageReposity messageReposity,ProjectReposity projectReposity,AuthorityReposity authorityReposity) {
        this.users = users;
        this.resourceReposity = resourceReposity;
        this.taskReposity = taskReposity;
        this.messageReposity = messageReposity;
        this.projectReposity = projectReposity;
        this.authorityReposity =authorityReposity;
        this.timeStampReposity =timeStampReposity;
    }
    @RequestMapping(value = "/addFeature",method = RequestMethod.POST)
    public @ResponseBody Info addFeture(@RequestPart("file") MultipartFile file,HttpServletRequest request)
    {
        User user = getUser();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = request.getServletContext().getRealPath("Image/"+user.getWorkNumber()+"/");
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
              System.out.println(dest.getCanonicalPath());
                user.setFaceFeature(AFRTest.getFeature(dest.getCanonicalPath()));
            } catch (IllegalStateException e) {
                e.printStackTrace();return new Info("wrong" ,400);
            } catch (IOException e) {
                e.printStackTrace();return new Info("wrong" ,400);
            }
        }
        user.setStatus(1);
        users.save(user);
        return new Info("success",200);
    }
    private User getUser()
    {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = users.findById(userDetails.getUsername());
        if(!optionalUser.isPresent()) throw new SomethingNotFoundExcption("","user id");
        return optionalUser.get();
    }
    private boolean IsAdmin()
    {
        return false;
    }
    @RequestMapping(value="/auth", method=RequestMethod.POST)
     public @ResponseBody Info authFeture(@RequestPart("file") MultipartFile file, HttpServletRequest request)
    {
        File dest = null;
        User user =getUser();
        if(user.getStatus()==0) return new Info("load you face feture frist",400);
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = request.getServletContext().getRealPath("Image/"+user.getWorkNumber()+"/");
             dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                System.out.println(dest.getCanonicalPath());
                user.setFaceFeature(AFRTest.getFeature(dest.getCanonicalPath()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Float s  =1f;
        try {
            AFR_FSDK_FACEMODEL facemodel = AFR_FSDK_FACEMODEL.fromByteArray(user.getFaceFeature());
            s = AFRTest.authFromFeatrue(facemodel,dest.getCanonicalPath());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("相似度"+s);
        if(s>0.65) return new Info("success",200);
        return new Info("auth fail",400);
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
    public @ResponseBody
    TaskInfo getTaskInfo(@RequestParam  long id)
    {
        Optional<Task> optionalTask = taskReposity.findById(id);
        if(!optionalTask.isPresent()){ throw new SomethingNotFoundExcption(String.valueOf(id),"task id ");}
        Task task = optionalTask.get();
        List<TimeStamp> a = timeStampReposity.findAllByTask(task);
        List<String> aa = new ArrayList<>();
        for(TimeStamp timeStamp:a){
            aa.add(timeStamp.getTime());
        }
        return new TaskInfo(aa,task);
    }
    @RequestMapping(value = "/addUserToProject",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody ResponseEntity<?> addUserToProject(
            @RequestBody User user,
            @RequestParam String s,
            @RequestParam int lv
    )
    {

        Project project= projectReposity.findById(s).get();
        Authority authority = authorityReposity.findAllByUserAndProject(getUser(),project);
        if(authority.getAuthoity()<3) {return new ResponseEntity<>(new Info("can not don this",403),HttpStatus.FORBIDDEN);}
        Authority authority1 = new Authority(getUser(),project,lv);
        authorityReposity.save(authority);
        return new ResponseEntity<>(new Info("success",200),HttpStatus.OK);
    }
    @RequestMapping(value = "addTask",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody ResponseEntity<?> addTask( @RequestBody Task task,@RequestParam String s)
    {

        User user  =getUser();
        Project project= projectReposity.findById(s).get();
        if(project==null) {return new ResponseEntity<>(new Info("project not found",404),HttpStatus.NOT_FOUND);}
        Authority authority = authorityReposity.findAllByUserAndProject(user,project);
        if(authority==null&&authority.getAuthoity()<3){return new ResponseEntity<>(new Info(ReturnMessaage.FORBIDDEN.getName(),403),HttpStatus.FORBIDDEN);}
        task.setTaskPublisher(user);
        task.setTaskStatus(0);
        task.setProject(project);
        task.setTaskBegin(LocalDateTime.now().toString());
        Set<User>  userSet = new HashSet<>();
        userSet.add(user);
        task.setUsers(userSet);
        taskReposity.save(task);
        return  new ResponseEntity<>(task,HttpStatus.OK);
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
    List<UserInfo> getUsersMsg(@RequestParam int page
//            ,@RequestParam int model
    )
    {
        Page<User> userPage  =users.findAll( PageRequest.of(page,6));
        List<User> userList = userPage.getContent();
        List<UserInfo> userInfos = new ArrayList<>();
//        List<User> adminUsers = users.findAllByRole("ROLE_DAMIN");
//        System.out.println(model+" =============================");
//        if(model==0) {
            for(User a:userList)
            {
                userInfos.add(new UserInfo(a));
            }
//        }
//        if(model==1){s
//            for(User a:adminUsers)
//            {
//                userInfos.add(new UserInfo(a));
//            }
//        }
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
        path.append(user.getWorkNumber());
        path.append("/1");
        path.append(suffixName);

        File photo = new File(path.toString());
        if(!photo.getParentFile().exists()) photo.getParentFile().mkdirs();
        try{
            file.transferTo(photo);
            Thumbnails.of(photo).size(100,100).toFile(photo);
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
    @RequestMapping(value = "/getCountOfTask",method = RequestMethod.GET)
    public @ResponseBody
    int getcount(@RequestParam Long id)
    {
        Optional<Task> taskOption = taskReposity.findById(id);
        if(!taskOption.isPresent()) {throw  new SomethingNotFoundExcption("task","task "+id);}
        return taskOption.get().getUsers().size();
    }
    @RequestMapping(value = "/searchTask" ,method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<taskInfo>> searchTask(@RequestParam String taskName)
    {
        User user = getUser();
        List<taskInfo> ids = new ArrayList<>();
        for (Task task:taskReposity.findAllByTaskNameLike(taskName))
        {
            Authority authority = authorityReposity.findAllByUserAndProject(user,task.getProject());
            if(authority.getAuthoity()>=task.getSecurityLv())
            {
                ids.add(new taskInfo(task.getTaskName(),task.getTaskPublisher().getName(),task.getWorkload(),task.getProject().getName(),task.getTaskBegin()+task.getTaskEnd(),task.getSecurityLv()));

            }
        }
        return new ResponseEntity(ids, HttpStatus.OK);
    }
    @RequestMapping(value = "getUsersOfTask",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getUsersOfTask(@RequestParam Long taskid){
        Optional<Task> taskOptional = taskReposity.findById(taskid);
        if(!taskOptional.isPresent()){
            return  new ResponseEntity(new SomethingNotFoundExcption("task not found",""),HttpStatus.NOT_FOUND);
        }
        Task task = taskOptional.get();
        Set<User> users1 = task.getUsers();
        List<UserInfo> list = new ArrayList<>();
        for(User user:users1)
        {
            Authority authority = authorityReposity.findAllByUserAndProject(user,task.getProject());
            list.add(new UserInfoWithLevel(user,authority.getAuthoity()));
        }
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
}
