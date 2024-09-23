package org.example.springv3.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.springv3.core.util.JwtUtil;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;

import java.io.IOException;
import java.io.PrintWriter;

// 책임 : 인가 !! (인증된 사람들만 들어오게 해주는 것)
public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 자식으로 다운캐스팅해서 필요한 메서드 쓸거임
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String accessToken = req.getHeader("Authorization"); // 이 헤더에서 꺼내오자

        if(accessToken == null || accessToken.isBlank()) { // isEmpty 보다 안전하군
            resp.setHeader("Content-Type", "application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            Resp fail = Resp.fail(401, "토큰이 없어요"); // 인가 안된것도 맞는데 인증이 안된거니까
            String responseBody = new ObjectMapper().writeValueAsString(fail); // 객체를 문자열로 바꿈
            out.print(responseBody);
            out.flush();
            return;
        } // else 가 있으면 return 안필요 하다.

        try {
            User sessionUser = JwtUtil.verify(accessToken); // 위조, 만료  -> try catch 로 제어해야 한다.
            HttpSession session = req.getSession();
            session.setAttribute("sessionUser", sessionUser);
            chain.doFilter(req, resp); // 다음 필터로 가!! 없으면 DS 로 감.
        } catch (Exception e) { // throw 못날리니까 아래와 같이 해야함
//            resp.setHeader("Content-Type", "application/json; charset=utf-8");
            resp.setContentType("application/json; charset=utf-8"); // setHeader 랑 똑같은 것, setHeader 로 바꾸면
            PrintWriter out = resp.getWriter();
            Resp fail = Resp.fail(401, e.getMessage()); // 인가 안된것도 맞는데 인증이 안된거니까
            String responseBody = new ObjectMapper().writeValueAsString(fail); // 객체를 문자열로 바꿈
            out.print(responseBody);
//            out.println(fail);
            out.flush();
        }


    }
}
