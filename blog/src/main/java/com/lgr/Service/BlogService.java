package com.lgr.service;

import com.lgr.po.Blog;
import com.lgr.po.Tag;
import com.lgr.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 16:51
 * Description:
 * Version: V1.0
 */
public interface BlogService {

    Blog getBlog(Long id);
//游客视图
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
//    管理员视图
    Page<Blog> listBlogAdmin(Pageable pageable,BlogQuery blogQuery);


    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query,Pageable pageable);


//    根据tagid

    Page<Blog> listBlog(Long tagid,Pageable pageable);

//    处理博客详情界面的blog样式
    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    List<Blog> listBlogRecommendTop(Integer size);


}
