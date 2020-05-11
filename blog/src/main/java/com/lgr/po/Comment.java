package com.lgr.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/26 14:44
 * Description:
 * Version: V1.0
 */
@Data
@Entity(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String Email;
    private String content;
//    头像
    private String avatar;
//    生成全时间，日期及加时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    private Boolean isAdmin;

//    评论关系映射
//    一个父评论可以有多个子评论
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

//    一个子评论只能有一个父评论
    @ManyToOne
    private Comment parentComment;


}
