package com.tencent.wxcloudrun.controller;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/xcx")
public class IndexController {
    @RequestMapping("/status")
    public String index() {
        return "running···";
    }
}
