package com.example.arcface.config;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;


public class FileterIniatializer {


    public void onStartup(ServletContext arg0) throws ServletException {
        // TODO Auto-generated method stub

        Dynamic cros  = arg0.addFilter("WebContextFilter",com.example.arcface.config.WebContextFilter.class );
        cros.addMappingForUrlPatterns(null, false, "/*");

    }

}

