package com.lgr.po;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/26 14:41
 * Description:
 * Version: V1.0
 */
@Data
@Entity(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
//    主键ID
    private long id;
//    分类名
    @NotBlank(message = "分类名不能为空")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs;
}
