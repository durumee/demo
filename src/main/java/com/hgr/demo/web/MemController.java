package com.hgr.demo.web;

import com.hgr.demo.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemController {

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("userDetails", userDetails);
        }

        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/login-process")
    public String loginProc() {
        //로그인
        return "로그인";
    }

    @GetMapping("/member/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/member/join-process")
    @ResponseBody
    public String joinProc() {
        //가입처리
        return "hi";
    }

}
