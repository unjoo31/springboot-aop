package shop.mtcoding.aopstudy.config.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.mtcoding.aopstudy.config.annotation.LoginUserResolver;
import shop.mtcoding.aopstudy.model.User;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Configuration
public class MyLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean check1 = parameter.getParameterAnnotation(LoginUserResolver.class) != null;
        boolean check2 = User.class.equals(parameter.getParameterType());
        return check1 && check2;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute("loginUser");
    }
}
