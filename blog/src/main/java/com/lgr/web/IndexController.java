package com.lgr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/12 15:27
 * Description:
 * Version: V1.0
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @RequestMapping("/blog")
    public String blog() {
        return "blog";
    }
}
