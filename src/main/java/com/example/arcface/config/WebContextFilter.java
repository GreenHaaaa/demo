package com.example.arcface.config;



import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class WebContextFilter
 */

public class WebContextFilter implements Filter {

    /**
     * Default constructor.
     */
    public WebContextFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        // place your code here
        HttpServletResponse response1 = (HttpServletResponse) response;

        HttpServletRequest reqs = (HttpServletRequest) request;

//        response1.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin"));
        response1.setHeader("Access-Control-Allow-Credentials", "true");
        response1.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response1.setHeader("Access-Control-Max-Age", "3600");
        response1.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response1.setHeader("Access-Control-Allow-origin", "*");
        response1.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
        chain.doFilter(request, response1);
        // pass the request along the filter chain

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

}