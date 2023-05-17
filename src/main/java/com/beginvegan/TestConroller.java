package com.beginvegan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {
    @Value("${my_test}")
    String test;


    @GetMapping("/test")
    public String test() {
        return test;
    }
}
