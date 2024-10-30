package kdu.cse.unispace.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kdu.cse.unispace.domain.Member;
import kdu.cse.unispace.service.MemberService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MemberService memberService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        boolean rememberMe = "true".equals(request.getParameter("remember-me"));

        String email = authentication.getName();
        Member member = memberService.findByEmail(email);

        // 토큰 생성
        String token = jwtTokenUtil.generateToken(authentication, rememberMe);

        // JSON 형식으로 응답 바디에 토큰 추가
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("id", member.getId());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();

    }
}