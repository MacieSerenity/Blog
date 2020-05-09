package com.lgr.service;

import com.lgr.po.Blog;
import com.lgr.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 16:51
 * Description:
 * Version: V1.0
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

}
