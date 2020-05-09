package com.lgr.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/26 14:43
 * Description:
 * Version: V1.0
 */
@Data
@Entity(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
//    标签名
    private String name;

    @ManyToMany(mappedBy = "taglist")
    private List<Blog> blogList = new ArrayList<>();
}
