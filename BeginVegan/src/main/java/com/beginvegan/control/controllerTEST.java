package com.beginvegan.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controllerTEST {
    @RequestMapping("/test")
    public String test() {
        return "ContollerTEST SUCCESS!";
    }
}
