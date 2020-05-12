package com.lgr.web;

import com.lgr.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/12 16:18
 * Description:
 * Version: V1.0
 */
@Controller
public class ShowAboutMeController {

	@Autowired
	BlogService blogService;

	@GetMapping("/about")
	public String show(Model model){
		model.addAttribute("newblogs",blogService.listBlogRecommendTop(3));
		return "/about";
	}
}
