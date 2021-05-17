package com.example.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping ("/hello") //接口：localhost:8080/hello
    public String index(){
        return "Hello World";
    }
}
