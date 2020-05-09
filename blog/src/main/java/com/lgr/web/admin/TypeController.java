package com.lgr.web.admin;

import com.lgr.po.Type;
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
 * Date: 2020/05/08 14:09
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping(value = "/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String list(
            @PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "/admin/type-Input";
    }

    @PostMapping("/types")
    public String addPost(@Valid Type type, BindingResult bindingResult, RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            bindingResult.rejectValue("name","name_already_existed","分类名已存在");
        }

        if (bindingResult.hasErrors()){
            return "/admin/type-Input";
        }
        Type t=typeService.saveType(type);
        if (t==null){
            attributes.addFlashAttribute("message","操作失败");

        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/type-Input";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult bindingResult,@PathVariable Long id,RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());

        if (type1!=null){
            bindingResult.rejectValue("name","name_already_existed","分类名已存在");
        }
        if (bindingResult.hasErrors()){
            return "/admin/type-Input";
        }
        Type t=typeService.updateType(id,type);
        if (t==null){
            attributes.addFlashAttribute("message","操作失败");

        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/types";
    }
}
