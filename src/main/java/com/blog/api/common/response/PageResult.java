package com.blog.api.common.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PageResult<T> {

    private List<T> list;

    private Pagination pagination;

    public PageResult(int pageIndex, int pageSize, int totalCount, int pageCount, List<T> list) {

        if (pageIndex <= 0) pageIndex = 1;
        if (totalCount < 0) totalCount = 0;
        if (pageCount < 0) pageCount = 0;
        if (list == null) list = new ArrayList<>();
        this.pagination = new Pagination();
        this.pagination.totalCount = totalCount;
        this.pagination.pageCount = pageCount;
        this.pagination.pageIndex = pageIndex;
        this.pagination.pageSize = pageCount;
        this.list = list;
    }

    public PageResult(List<T> list, Pagination pagination) {
        this.pagination = pagination;
        this.list = list;
    }

    @Data
    public class Pagination {
        private int totalCount;

        private int pageIndex;

        private int pageSize;

        private int pageCount;
    }

    public static<T> PageResult toPageResult(Page<T> page) {

        var pageResult = new PageResult(page.getNumber(),
                page.getSize(),(int)page.getTotalElements(),
                page.getTotalPages(), page.getContent());
        return pageResult;

    }


    public List<T>  GetList(T a){
        return  new ArrayList<>();
    }

}


