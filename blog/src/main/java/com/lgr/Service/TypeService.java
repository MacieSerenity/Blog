package com.lgr.service;

import com.lgr.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/08 13:54
 * Description:
 * Version: V1.0
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);

    //用于返回一个所有type的list用于添加，查看，搜索分类
    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
