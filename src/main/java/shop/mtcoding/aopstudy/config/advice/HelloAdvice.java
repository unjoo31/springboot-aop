package shop.mtcoding.aopstudy.config.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloAdvice {

    // 깃발에 별칭주기
    @Pointcut("@annotation(shop.mtcoding.aopstudy.config.annotation.Hello)")
    public void hello(){}

    // 매개변수에 접근해서 값을 찾는 것을 가능 - 값을 주입하려면 @Around 사용해야함
    @Before("hello()")
    public void helloAdvice(JoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof String){
                String username = (String) arg;
                System.out.println(username+"님 안녕");
            }
        }
    }
}
