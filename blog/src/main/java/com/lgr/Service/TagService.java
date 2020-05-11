package com.lgr.service;

import com.lgr.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    Tag getTagByName(String name);
    List<Tag> listTagTop(Integer size);

}
