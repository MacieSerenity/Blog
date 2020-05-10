package com.lgr.web.admin;

import com.lgr.po.Tag;
import com.lgr.service.TagService;
import com.lgr.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 11:48
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //显示tag
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
    Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

//    转到input
    @GetMapping("/tags/input")
    public String addTags(Model model){
        model.addAttribute("tag",new Tag());
        return "/admin/tags-Input";
    }

    @PostMapping("/tags")
    public String addTag(@Valid Tag tag,BindingResult bindingResult,RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());

        if (tag1!=null){
            bindingResult.rejectValue("name","tag_already_existed","标签名已存在");
        }
        if (bindingResult.hasErrors()){
            return "/admin/tags-Input";
        }

        Tag t=tagService.saveTag(tag);
        if (t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }

//    更改标签名
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,
                           Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "/admin/tags-Input";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,
                          BindingResult bindingResult,
                          @PathVariable Long id,
                          RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());

        if (tag1!=null){
            bindingResult.rejectValue("name","tag_already_existed","标签名已存在");
        }
        if (bindingResult.hasErrors()){
            return "/admin/tags-Input";
        }

        Tag t=tagService.updateTag(id,tag);
        if (t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }


    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,
                            RedirectAttributes attributes
                            ){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/tags";
    }

}
