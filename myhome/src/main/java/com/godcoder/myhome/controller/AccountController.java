package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.User;
import com.godcoder.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return"account/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        // 사용자 저장해야 하는데 사용자가 저장할때 패스워드 암호화, 사용자 권한등을
        // 추가하는 비즈니스 로직이 필요하므로 service를 추가해야 함.
        userService.save(user);
        return "redirect:/";
    }

}
