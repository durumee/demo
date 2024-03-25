package com.hgr.demo.web;

import com.hgr.demo.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MemController {

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            MemberDTO userDetails = (MemberDTO) authentication.getPrincipal();
//            System.out.println("로그인 ID :: " + userDetails.getMemLgnId());
//            model.addAttribute("userDetails", userDetails);
//        }

        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "info data";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/member/join-process")
    @ResponseBody
    public String joinProc() {
        log.info("::가입처리");
        //가입처리
        return "hi";
    }

}
