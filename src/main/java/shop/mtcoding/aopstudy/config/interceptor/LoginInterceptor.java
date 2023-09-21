package shop.mtcoding.aopstudy.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.aopstudy.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("잘못된 접근입니다");
            return false;
        }else{
            return true;
        }
    }
}
