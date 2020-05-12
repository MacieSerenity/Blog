package com.lgr.service;

import com.lgr.po.Blog;
import com.lgr.po.Tag;
import com.lgr.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 16:51
 * Description:
 * Version: V1.0
 */
public interface BlogService {

    Blog getBlog(Long id);
//      游客视图
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
//      管理员视图
    Page<Blog> listBlogAdmin(Pageable pageable,BlogQuery blogQuery);
//      首页更新视图
    Page<Blog> listBlog(Pageable pageable);
//       后台搜索带条件的blog
    Page<Blog> listBlog(String query,Pageable pageable);

//    归档查询所有年份
    Map<String,List<Blog>> archiveBlog();
//    归档Blog条数
    Long archiveBlogCount();


//    根据tagid 查询带有tagid的blog

    Page<Blog> listBlog(Long tagid,Pageable pageable);

//    处理博客详情界面的blog样式
    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

//    拿出size个 作为推荐的博客
    List<Blog> listBlogRecommendTop(Integer size);
}
