package com.beginvegan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConroller {
    @Value("${my_test}")
    String test;

    @Value("${my_test2}")
    String test2;


    @GetMapping("/test")
    public String test() {
        return test;
    }

    @GetMapping("/test2")
    public String test2222() {
        return test2;
    }
}
