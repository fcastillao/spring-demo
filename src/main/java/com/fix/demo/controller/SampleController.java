package com.fix.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    /**
     * find endpoint, not secured
     *
     * @return the find string
     */
    @RequestMapping("/sample")
    @ResponseBody
    public String sample() {
        return "Hello sample!";
    }

    /**
     * find endpoint, secured via user pass
     *
     * @return the find string + secured
     */
    @RequestMapping("/sample2")
    @ResponseBody
    public String sample2() {
        return "Hello sample secured!";
    }

}