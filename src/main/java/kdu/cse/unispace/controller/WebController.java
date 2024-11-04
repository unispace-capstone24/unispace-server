package kdu.cse.unispace.controller;

import kdu.cse.unispace.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final MemberService memberService;

    @GetMapping({"/", "/home"}) //홈페이지
    public String homePage() {
        return "home";
    }

    @GetMapping("/login") //로그인 페이지
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup") // 회원가입 페이지
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/main")
    public String mainPage() { //로그인 이후 연결되는 페이지
        return "main";
    }


}
