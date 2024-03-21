package com.hgr.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/home")
	public String home() {
		return "home/home";
	}

	@GetMapping("/login-process")
	@ResponseBody
	public String loginProcess() {
		//사용자 정보 조회 로직을 추가한다
		return "hi";
	}

}
