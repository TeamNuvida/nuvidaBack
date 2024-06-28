package com.nuvida.nuvidaback.controller;

import com.nuvida.nuvidaback.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @Autowired
    private userMapper mapper;

    // 테스트 코드
    @RequestMapping("/test")
    public String test(){
        mapper.test();
        System.out.println("test");
        return "test";
    }
}
