package com.lgr.web.admin;

import com.lgr.service.UserService;
import com.lgr.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/07 23:02
 * Description:
 * Version: V1.0
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        RedirectAttributes redirectAttributes){
        System.out.println(username);
        System.out.println(password);
        User user=userService.checkUser(username,password);

        if (user!=null){
            user.setPassword(null);
            httpSession.setAttribute("user",user);
            return "admin/index";
        }else {
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
