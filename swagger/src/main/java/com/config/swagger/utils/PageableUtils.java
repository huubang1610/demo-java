package com.config.swagger.utils;

import org.springframework.data.domain.*;

import java.util.List;

public class PageableUtils extends PageRequest {
    public PageableUtils(int page, int size, String orderBy, boolean desc) {
        super(page - 1, size, desc ? Sort.by(orderBy).descending() : Sort.by(orderBy));
    }

    public static Pageable pageable(int page, int size, String orderBy, boolean desc) {
        return new PageableUtils(page, size, orderBy, desc);
    }

    public static <T> Page<T> convert(List<T> list, PageRequest pageRequest) {
        int pageSize = pageRequest.getPageSize();
        int currentPage = pageRequest.getPageNumber();
        int startItem = currentPage * pageSize;

        List<T> pageList;

        if (list.size() < startItem) {
            pageList = List.of();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            pageList = list.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageList, pageRequest, list.size());
    }
}