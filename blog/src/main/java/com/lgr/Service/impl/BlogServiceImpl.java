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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery) {
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

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }


    /**
     * @Description: 处理MarkDown转为Html
     * @Method: getAndConvert
     * @Param: [id]
     * @return: com.lgr.po.Blog
     * @Author: MacieSerenity
     * @Date: 2020/5/11
     */
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
