package shop.mtcoding.aopstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.aopstudy.config.annotation.Hello;

@RestController
public class HelloController {

    @GetMapping("/hello/v1")
    public String v1(){
        return "v1";
    }

    // http://localhost:8080/hello/v2?username=ssar
    @Hello
    @GetMapping("/hello/v2")
    public String v2(String username){
        System.out.println("username : 값 변경? : "+username);
        return "v2";
    }
}
