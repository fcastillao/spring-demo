package com.fix.demo.others;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SampleController {

    @RequestMapping("/sample")
    @ResponseBody
    String sample() {
        return "Hello sample!";
    }

    @RequestMapping("/sample2")
    @ResponseBody
    String sample2() {
        return "Hello sample secured!";
    }


    public String getErrorPath() {
        return "/error";
    }

}