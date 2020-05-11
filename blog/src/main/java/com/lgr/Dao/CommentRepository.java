package com.lgr.dao;

import com.lgr.po.Blog;
import com.lgr.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/11 17:44
 * Description:
 * Version: V1.0
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
	List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);

}
