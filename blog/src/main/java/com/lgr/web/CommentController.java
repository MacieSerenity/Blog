package com.lgr.web;

import com.lgr.po.Comment;
import com.lgr.po.User;
import com.lgr.service.BlogService;
import com.lgr.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/11 17:37
 * Description:
 * Version: V1.0
 */
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;

	@Value("${comment.avatar}")
	private String avatar;

	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model){
		model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
		return "blog::commentList";
	}

	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session){
		Long blogId=comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		User user =(User)session.getAttribute("user");

		if (user!=null){
			comment.setAvatar(user.getAvatar());
			comment.setIsAdmin(true);
			comment.setNickname(user.getNickname());
		}else {
			comment.setAvatar(avatar);
			comment.setIsAdmin(false);
		}
		commentService.saveComment(comment);
		return "redirect:/comments/"+comment.getBlog().getId();
	}
}
