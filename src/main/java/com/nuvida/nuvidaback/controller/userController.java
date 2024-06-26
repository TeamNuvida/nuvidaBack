package com.nuvida.nuvidaback.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    // 테스트 코드
    @RequestMapping("/test")
    public String test(){
        System.out.println("test");
        return "test";
    }
}
