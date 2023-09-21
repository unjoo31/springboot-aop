package shop.mtcoding.aopstudy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.aopstudy.config.annotation.LoginUserAop;
import shop.mtcoding.aopstudy.config.annotation.LoginUserResolver;
import shop.mtcoding.aopstudy.config.exception.MyValidationException;
import shop.mtcoding.aopstudy.dto.JoinInDto;
import shop.mtcoding.aopstudy.model.User;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession session;

    @GetMapping("/login")
    public String login(){
        User user = new User(1, "ssar", "1234", "0102222");
        session.setAttribute("loginUser", user);
        return "login ok";
    }
    
    @GetMapping("/user") // 인증 필요 없음
    public String userInfoNoAuth(){
        return "user ok";
    }

    // username 출력 안됨
    @GetMapping("/auth/v1") // 인증 필요함
    public String userInfo(User user){ // 값 할당 안됨
        return "v1 username : "+user.getUsername();
    }

    @GetMapping("/auth/v2")
    public String authInfoAop(@LoginUserAop User user){ // 인증 필요함
        return "v2 username : "+user.getUsername();
    }

    @GetMapping("/auth/v3")
    public String authInfoResolver(@LoginUserResolver User user){ // 인증 필요함
        System.out.println(user.getUsername());
        return "v3 username : "+user.getUsername();
    }

    // Valid AOP 발동
    @PostMapping("/valid")
    public String join(@Valid JoinInDto joinInDto, BindingResult bindingResult){
        // @Valid가 있으면 JoinInDto내부에 있는 변수 위에 있는 어노테이션을 확인해서 체크한다음에 잘못 들어온 값을 bindingResult에 전달한다

        // 이 코드의 경우 body데이터가 있는 모든 메서드에서 실행되어야 하기 때문에 advice를 따로 만들어서 사용한다
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            // list형태임
            for(FieldError error : bindingResult.getFieldErrors()){
                System.out.println(error.getField()); // getField() : 잘못들어온 값을 알려줌
                System.out.println(error.getDefaultMessage()); // getDefaultMessage() : 메시지를 보여줌
                errorMap.put(error.getField(), error.getDefaultMessage());
                // 에러가 여러개여도 다 보내줄 필요 없기 때문에 에러가 하나 생기면 break한다.
                break;
            }
            throw new MyValidationException(errorMap);
        }
        
        return "ok";
    }
}
