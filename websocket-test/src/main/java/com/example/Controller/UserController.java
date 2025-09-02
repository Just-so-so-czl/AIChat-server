package com.example.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

//    @RequestMapping("/login")
//    public Result login(User user, HttpSession session) {
//        Result result = new Result();
//        if(user != null && "123".equals(user.getPassword())) {
//            result.setFlag(true);
//            //将用户名存储到session对象中
//            session.setAttribute("user",user.getUsername());
//        } else {
//            result.setFlag(false);
//            result.setMessage("登陆失败");
//        }
//
//        return result;
//    }
}
