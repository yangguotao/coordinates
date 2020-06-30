package com.springboot.coordinates.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangguotao
 * @Date: 2019/12/10
 * @Version 1.0
 */
@RestController
public class TestController {

    @GetMapping("")
    public String test() {
        return "success";
    }

}
