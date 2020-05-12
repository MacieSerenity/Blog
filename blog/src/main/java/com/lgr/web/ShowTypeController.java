package com.lgr.web;

import com.lgr.po.Type;
import com.lgr.service.BlogService;
import com.lgr.service.TypeService;
import com.lgr.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/12 12:35
 * Description:
 * Version: V1.0
 */
@Controller
public class ShowTypeController {

	@Autowired
	private TypeService typeService;

	@Autowired
	private BlogService blogService;

	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
					                      Pageable pageable, @PathVariable Long id, Model model){
		List<Type> typeList=typeService.listTypeTop(99);
		if (id==-1){
//			把第一个标签的id设置为默认id
			id= typeList.get(0).getId();
		}
		BlogQuery blogQuery= new BlogQuery();
		blogQuery.setId(id);
		model.addAttribute("types",typeList);
		model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
		model.addAttribute("activeTypeId",id);
		model.addAttribute("newblogs",blogService.listBlogRecommendTop(3));
		return "/types";
	}
}

