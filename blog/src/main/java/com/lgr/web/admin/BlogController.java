package com.lgr.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/08 13:12
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogController {


    @GetMapping("/blogs")
    public String blog(){
        return "admin/blogManage";
    }
}
