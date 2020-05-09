package com.lgr.dao;

import com.lgr.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 9:18
 * Description:
 * Version: V1.0
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
}
