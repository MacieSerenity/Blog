package com.lgr.service.impl;

import com.lgr.NotFoundException;
import com.lgr.dao.BlogRepository;
import com.lgr.po.Blog;
import com.lgr.po.Type;
import com.lgr.service.BlogService;
import com.lgr.vo.BlogQuery;
import com.util.MarkDownUtils;
import com.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 16:57
 * Description:
 * Version: V1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }


//    使用jpa封装的一个对比实体中是否有值的方法Specification
//    需要再Repository继承JpaSpecificationExecutor<Blog>类
//    后台查看视图
    @Transactional
    @Override
    public Page<Blog> listBlogAdmin(Pageable pageable,BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>(){
//            实现它的方法
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                新建一个Predicate对象的数组，存放用于搜索的条件
                List<Predicate> predicateList = new ArrayList<>();

//                判断标题是否为空，为空就不加入数组，不查询此条件
//                若是不为空，就是用criteriaBuilder下的like的模糊查询封装方法
//                手动加上 % 用于sql模糊查询
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    System.out.println("IS GETTITLE");
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }

//                查询分类条件，若是不为空，加入条件数组
                if(blogQuery.getId()!=null){
                    System.out.println("IS GETID");
//                    这里使用的是对比，因为type是用id查询，不是模糊查询
                    predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getId()));
                }

                if(blogQuery.isRecommend()){
//                    是否推荐为Boolean型,直接判断
                    System.out.println("IS RECOMMEND");
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blogQuery.isRecommend()));
                }
//                执行查询
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
//                不需要返回值,自动封装到pageable中
                return null;
            }
        },pageable);
    }

//前台看视图
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>(){
            //            实现它的方法
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                新建一个Predicate对象的数组，存放用于搜索的条件
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getId()));
                predicateList.add(criteriaBuilder.equal(root.<Boolean>get("published"),true));
//                执行查询
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
//                不需要返回值,自动封装到pageable中
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
//        修改查询为返回只发布的
//        return blogRepository.findAll(pageable);
        return blogRepository.findAllByPublished(true,pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogRepository.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for (String year:years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long archiveBlogCount() {
        return blogRepository.countPublished();
    }

    @Override
    public Page<Blog> listBlog(Long tagid, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("taglist");
                return criteriaBuilder.equal(join.get("id"),tagid);
            }
        },pageable);
    }


    /**
     * @Description: 处理MarkDown转为Html
     * @Method: getAndConvert
     * @Param: [id]
     * @return: com.lgr.po.Blog
     * @Author: MacieSerenity
     * @Date: 2020/5/11
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.findById(id).get();
        if (blog==null){
            throw new NotFoundException("博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);

        return b;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog tempBlog=blogRepository.findById(id).get();
        if (tempBlog==null){
            throw new NotFoundException("没有找到这篇博客");
        }

        /**
         * MyBeanUtils.getNullPropertyNames 将null值分离出来
         */
        BeanUtils.copyProperties(blog,tempBlog, MyBeanUtils.getNullPropertyNames(blog));
        tempBlog.setUpdateTime(new Date());
        return blogRepository.save(tempBlog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listBlogRecommendTop(Integer size) {
//        Pageable pageable= PageRequest.of(0,size, Sort.Direction.DESC,"updateTime");
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable=PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }
}
