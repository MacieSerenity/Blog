package com.lgr.web.admin;

import com.lgr.po.Blog;
import com.lgr.po.User;
import com.lgr.service.BlogService;
import com.lgr.service.TagService;
import com.lgr.service.TypeService;
import com.lgr.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

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

    private static final String INPUT="/admin/blog-Input";
    private static final String LIST="/admin/blogManage";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

//    用来获取type数据
    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


//    这个是查询整个页面的
    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        //返回一个type类型的数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        Page<Blog> page=blogService.listBlog(pageable,blogQuery);
        return "/admin/blogManage";
    }

//    局部搜索，局部查询,返回blogManage里面的一个blogList片段
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "/admin/blogManage :: blogList";
    }


    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession httpSession){
        blog.setUser((User) httpSession.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTaglist(tagService.listTag(blog.getTags()));
        Blog blog1=blogService.saveBlog(blog);

        if (blog1==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }

        return REDIRECT_LIST;
    }



}
