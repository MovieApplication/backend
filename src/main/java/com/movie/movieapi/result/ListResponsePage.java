package com.movie.movieapi.result;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ListResponsePage<T> {
    private List<T> items;
    private PaginationInfo paginationInfo;

    public ListResponsePage(Page<T> page) {
        int firstPage = ((page.getNumber()) / MovieConstants.DEFAULT_PAGE_SIZE) * MovieConstants.DEFAULT_PAGE_SIZE + 1;
        int lastPage = firstPage + MovieConstants.DEFAULT_PAGE_SIZE - 1;
        if (lastPage > page.getTotalPages()) {
            lastPage = page.getTotalPages();
        }

        this.items = page.getContent();
        this.paginationInfo = PaginationInfo.builder()
                .totalRecordCount(page.getTotalElements())
                .totalPageCount(page.getTotalPages())
                .recordsPerPage(page.getSize())
                .pageSize(MovieConstants.DEFAULT_PAGE_SIZE)
                .firstPage(firstPage)
                .lastPage(lastPage)
                .startPage(page.isFirst())
                .endPage(page.isLast())
                .currentPageNo(page.getNumber() + 1)
                .empty(page.isEmpty())
                .build();
    }
}
