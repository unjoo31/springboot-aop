package shop.mtcoding.aopstudy.config.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.mtcoding.aopstudy.config.annotation.LoginUserAop;
import shop.mtcoding.aopstudy.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

@Aspect
@Component
public class LoginAdvice {

    @Around("execution(* shop.mtcoding.aopstudy.controller.*.*(..))")
    public Object loginUserAdviceAround(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Object[] args = jp.getArgs();
        Parameter[] parameters = method.getParameters();

        int loginUserAopIndex = IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(LoginUserAop.class))
                .findFirst()
                .orElse(-1);

        if (loginUserAopIndex >= 0) {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = req.getSession();
            User principal = (User) session.getAttribute("loginUser");

            args[loginUserAopIndex] = principal;

            // 해당 메서드 실행
            Object result = jp.proceed(args);

            // 실행 후 결과 값을 가지고, 로그를 남길 수 있음. (실재 로그는 log4j 사용해야함)
            System.out.println(result);
            return result;
        }

        return jp.proceed();
    }
}
