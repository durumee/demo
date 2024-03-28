package com.hgr.demo.web;

import com.hgr.demo.dto.MemberDTO;
import com.hgr.demo.dto.TestDTO;
import com.hgr.demo.dto.TestSubDTO;
import com.hgr.demo.entity.MemberRent;
import com.hgr.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class MemController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    public MemController(PasswordEncoder passwordEncoder, MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        log.info(passwordEncoder.encode("1234"));
//        if (authentication != null && authentication.isAuthenticated()) {
//            MemberDTO userDetails = (MemberDTO) authentication.getPrincipal();
//            System.out.println("로그인 ID :: " + userDetails.getMemLgnId());
//            model.addAttribute("userDetails", userDetails);
//        }

        return "index";
    }

    @GetMapping("/test")
    public String test() throws Exception {
        memberService.addRent();
        return "index";
    }

    @GetMapping("/rent")
    public String rent(Model model, @RequestParam(value = "page", defaultValue = "0") int pageNo) throws Exception {
        Page<MemberRent> page = memberService.getRents(pageNo);
        model.addAttribute("rentPage", page);
        return "home/rent";
    }

    @GetMapping("/home")
    public String home(@ModelAttribute MemberDTO memberDTO, @ModelAttribute TestDTO inTestDTO, Model model) {
        Authentication authz = SecurityContextHolder.getContext().getAuthentication();
        log.info(authz.getAuthorities().toString());
        log.info(memberDTO.getMemLgnId());
        log.info(inTestDTO.getTestSubDTO().getExtNm());

        if (memberDTO == null) {
            memberDTO = new MemberDTO();
        }

        TestDTO testDTO = new TestDTO();
        TestSubDTO testSubDTO = new TestSubDTO();
        testSubDTO.setExtNm("테스트 확장이에요");
        testDTO.setTestNm("테스트에요");
        testDTO.setTestSubDTO(testSubDTO);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("testDTO", testDTO);
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
