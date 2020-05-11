package com.lgr.web;

import com.lgr.service.BlogService;
import com.lgr.service.TagService;
import com.lgr.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/12 15:27
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(8));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogRecommendTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                 Pageable pageable,
                         @RequestParam String query,
                         Model model){
        //select * from t_blog where title like '% ?1 %‘
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
