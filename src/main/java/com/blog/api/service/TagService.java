package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.dto.CategoryDto;
import com.blog.api.dto.TagDto;
import com.blog.api.model.Api;
import com.blog.api.model.Tag;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.TagRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TagService extends BaseService<Tag, Integer> {

    private TagRepository dal;


    @Autowired
    public TagService(TagRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public List<TagDto> getTags() {
        var list = dal.getTags();
        return list.stream().map(n -> {
            var dto = new TagDto();
            dto.setId(Integer.valueOf(n.get("id").toString()));
            dto.setTagName(n.get("tag_name").toString());
            dto.setPostCount(Integer.valueOf(n.get("post_count").toString()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void clearPostTag(int postId) {
        dal.clearPostTag(postId);
    }

    @Transactional
    public void bindPostTag(int tagId, int postId) {

        dal.bindPostTag(tagId, postId);
    }


}
