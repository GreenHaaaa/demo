package com.example.arcface.controller;

import com.example.arcface.domain.*;
import com.example.arcface.reposity.AuthorityReposity;
import com.example.arcface.reposity.ProjectReposity;
import com.example.arcface.reposity.TaskReposity;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(allowCredentials="true",
        origins = "*",
        methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS,RequestMethod.DELETE},
        allowedHeaders={"x-requested-with", "accept", "authorization", "content-type",},
        exposedHeaders={"access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "X-Frame-Options"})
@RequestMapping(value = "/project")
public class ProjectController  {
    private ProjectReposity projectReposity;
    private TaskReposity taskReposity;
    private AuthorityReposity authorityReposity;
    private UserReposity userReposity;
    @Autowired
    public ProjectController(UserReposity userReposity,ProjectReposity projectReposity,TaskReposity taskReposity,AuthorityReposity authorityReposity)
    {
        this.userReposity = userReposity;
        this.authorityReposity = authorityReposity;
        this.projectReposity = projectReposity;
        this.taskReposity = taskReposity;
    }
    public User getUser()
    {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user  = userReposity.findById(userDetails.getUsername()).get();
        return user;
    }
    @RequestMapping(value = "/addProject",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    Info  addproject(@RequestBody Project project)
    {
        Project project1  = projectReposity.save(project);
        authorityReposity.save(new Authority(getUser(),project1,1));
        return new Info("success",200);
    }
    @RequestMapping(value = "/getUsersOfProject",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody List<UserAuthority> getUsersOfProject(@RequestBody  Project project)
    {
       List<Authority> authorities = authorityReposity.findAllByProject(project);
       List<UserAuthority> list = new ArrayList<UserAuthority>();
       for(Authority authority :authorities)
       {
           User user = authority.getUser();
           list.add(new UserAuthority(user.getWorkNumber(),authority.getAuthoity(),user.getName(),user.getDepartment()));
       }
       return  list;
    }
    @RequestMapping(value = "/getProjectByUser",method = RequestMethod.GET,consumes = "application/json",produces = "application/json")
    public @ResponseBody
    List<Project> getProjectByUser(HttpServletResponse servletResponse)
    {

        List<Authority> list =  authorityReposity.findAllByUser(getUser());
        List<Project> projects = new ArrayList<>();
        for (Authority a: list) {
            projects.add(a.getProject());
        }
        return projects;
    }
    @RequestMapping(value = "/getTaskOfProject",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public @ResponseBody List<Task>  getTasksForProject(@RequestBody Project project)
    {
        return   taskReposity.findAllByProject(project);
    }

}
