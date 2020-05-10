package com.lgr.po;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/19 17:22
 * Description:
 * Version: V1.0
 */
@Data
@Entity(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    //    标题
    private String title;

    //    内容
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    //    首图
    private String firstPicture;
    //    标签
    private String flag;
    //    浏览次数
    private Integer views;
    //    赞赏
    private boolean appreciation;
    //    版权
    private boolean shareStatement;
    //    评论
    private boolean commentabled;
    //    公开/发布
    private boolean published;
    //    是否推荐
    private boolean recommend;
    //    创建时间
    //    生成全时间，日期及加时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //    更改时间
    //    生成全时间，日期及加时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

//    级联新增，新增一个带tag的blog时，也会在blog那边新增一个标签
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> taglist = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();

    @Transient
    private String tags;

}
