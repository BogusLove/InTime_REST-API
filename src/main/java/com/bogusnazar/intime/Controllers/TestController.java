package com.bogusnazar.intime.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Artem on 5/16/2018.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/")
    public String test() {
        return "Hello world";

    }
}
