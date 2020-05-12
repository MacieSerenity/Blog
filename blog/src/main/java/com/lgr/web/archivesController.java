package com.lgr.web;

import com.lgr.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/12 15:26
 * Description:
 * Version: V1.0
 */
@Controller
public class archivesController {

	@Autowired
	BlogService blogService;

	@GetMapping("/archives")
	public String archives(Model model){
		model.addAttribute("archiveMap",blogService.archiveBlog());
		model.addAttribute("archiveBlogCount",blogService.archiveBlogCount());
		return "/archives";
	}
}
