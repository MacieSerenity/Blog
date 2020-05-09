package com.lgr.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 20:02
 * Description:这个包是给搜索的时候封装的一个实例包，用于存储关于搜索的一些信息
 * 如 title typeid和 recommend
 * Version: V1.0
 */
@Data
public class BlogQuery {
	private String title;
	private Long id;
	private boolean recommend;
}
