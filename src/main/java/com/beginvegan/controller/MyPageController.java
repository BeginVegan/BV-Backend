package com.beginvegan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mypage/*")
public class MyPageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
}
