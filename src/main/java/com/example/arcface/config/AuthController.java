package com.example.arcface.config;


import com.example.arcface.demo.AFRTest;
import com.example.arcface.domain.Info;
import com.example.arcface.domain.User;
import com.example.arcface.domain.UserExistException;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class AuthController {
    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private AuthService authService;
    @RequestMapping(value = "/auth", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    public @ResponseBody ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {


        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public @ResponseBody  ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    User register(User user,
                  HttpServletRequest request
    )throws AuthenticationException
    {
        if(authService.register(user)==null) {
            throw  new UserExistException(user.getWorkNumber());
        }
        return user;

    }
    @ExceptionHandler(UserExistException.class)
    public @ResponseBody  Info sendMsg(UserExistException e)
    {
        return new Info(e.getTip(),500);
    }
}