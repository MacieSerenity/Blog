package com.lgr.web.admin;

import com.lgr.po.Blog;
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
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private BlogService blogService;

//    用来获取type数据
    @Autowired
    private TypeService typeService;

//    这个是查询整个页面的
    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 2,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        //返回一个type类型的数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/blogManage";
    }

//    局部搜索，局部查询,返回blogManage里面的一个blogList片段
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/blogManage :: blogList";
    }


}
