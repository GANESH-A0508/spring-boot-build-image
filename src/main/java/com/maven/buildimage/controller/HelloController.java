package com.maven.buildimage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/start")
    public String get(){
        return "Getting start with maven build image!!!!";
    }
}
