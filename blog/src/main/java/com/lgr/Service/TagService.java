package com.lgr.service;

import com.lgr.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/09 9:14
 * Description:
 * Version: V1.0
 */
public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTag(Pageable pageable);
    Tag updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);
}
