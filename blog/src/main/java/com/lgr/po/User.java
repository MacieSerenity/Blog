package com.lgr.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/26 14:48
 * Description:
 * Version: V1.0
 */
@Data
@Entity(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String type;
    //    生成全时间，日期及加时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatTime;
    //    生成全时间，日期及加时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogList =new ArrayList<>();
}
