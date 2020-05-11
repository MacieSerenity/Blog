package com.lgr.service.impl;

import com.lgr.dao.CommentRepository;
import com.lgr.po.Comment;
import com.lgr.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/11 17:43
 * Description:
 * Version: V1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> listCommentByBlogId(Long blogId) {
		Sort sort=Sort.by(Sort.Direction.ASC,"createTime");
		List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
		return eachComment(comments);
	}

	@Override
	public Comment saveComment(Comment comment) {
		Long parentCommentId=comment.getParentComment().getId();
		if (parentCommentId != -1){
			comment.setParentComment(commentRepository.findById(parentCommentId).get());
		}else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}


	/**
	 * @Description:
	 *              循环遍历每个顶节点的评论
	 *              也就是父节点为null的节点
	 * @Method: eachComment
	 * @Param: [comments]
	 * @return: java.util.List<com.lgr.po.Comment>
	 * @Author: MacieSerenity
	 * @Date: 2020/5/11
	 */
	private List<Comment> eachComment(List<Comment> comments){
		List<Comment> commentsView = new ArrayList<>();
		for (Comment comment:comments){
			Comment c=new Comment();
			BeanUtils.copyProperties(comment,c);
			commentsView.add(c);
		}
		combineChildren(commentsView);
		return commentsView;
	}

	/**
	 * @Description: 将根节点
	 * @Method: combineChildren
	 * @Param: [comments] root根节点，blog不为空的对象结合
	 * @return: void
	 * @Author: MacieSerenity
	 * @Date: 2020/5/11
	 */
	private void combineChildren(List<Comment> comments){
		for (Comment comment:comments){
			List<Comment> commentList=comment.getReplyComments();
			for (Comment reply:commentList){
				recursively(reply);
			}

//			修改顶级结点的reply集合为迭代处理后的集合
			comment.setReplyComments(tempReplys);
//			清除临时存放区
			tempReplys=new ArrayList<>();
		}
	}

	/*
	 * 存放迭代找出的所有子回复的集合
	 */
	private List<Comment> tempReplys=new ArrayList<>();

	/**
	 * @Description: 递归迭代，把所有子回复放入数组
	 * @Method: recursively
	 * @Param: [comment]
	 * @return: void
	 * @Author: MacieSerenity
	 * @Date: 2020/5/11
	 */
	private void recursively(Comment comment){
//		把顶节点放入临时数组里面
		tempReplys.add(comment);
		if (comment.getReplyComments().size()>0){
			List<Comment> replys=comment.getReplyComments();
			for (Comment reply : replys){
				tempReplys.add(reply);
				if (reply.getReplyComments().size()>0){
					recursively(reply);
				}
			}
		}
	}

}
