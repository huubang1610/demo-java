package com.config.swagger.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;

@With
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListResult<T> {
    public long totalElements;
    public int totalPages;
    public int size;
    public int page;
    public List<T> content;


    public static <T> ListResult<T> from(Page<T> page) {
        return new ListResult<T>()
                .withPage(page.getNumber() + 1)
                .withSize(page.getSize())
                .withContent(page.getContent())
                .withTotalElements(page.getTotalElements())
                .withTotalPages(page.getTotalPages());
    }

    public static <T, R> ListResult<R> from(Page<T> page, List<R> content) {
        return new ListResult<R>()
                .withPage(page.getNumber() + 1)
                .withSize(page.getSize())
                .withContent(content)
                .withTotalElements(page.getTotalElements())
                .withTotalPages(page.getTotalPages());
    }

    // This method is mostly used to fake api.
    public static <T> ListResult<T> from(List<T> content, PageRequest request) {
        return new ListResult<T>()
                .withPage(request.getPageNumber() + 1)
                .withSize(request.getPageSize())
                .withContent(content)
                .withTotalElements(content.size())
                .withTotalPages(5);
    }

    public static <T, R> ListResult<R> from(ListResult<T> result, List<R> content) {
        return new ListResult<R>()
                .withPage(result.getPage())
                .withSize(result.getSize())
                .withContent(content)
                .withTotalElements(result.getTotalElements())
                .withTotalPages(result.getTotalPages());
    }
}