package com.lgr.service;

import com.lgr.po.Comment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/11 17:42
 * Description:
 * Version: V1.0
 */
public interface CommentService {

	List<Comment> listCommentByBlogId(Long blogId);

	Comment saveComment(Comment comment);
}
