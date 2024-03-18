package org.example.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.model.PageInfo;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtils {
  public static int getTotalPages(long totalRecords, int pageSize) {
    return totalRecords == 0 ? 1 : (int) Math.ceil((double) totalRecords / (double) pageSize);
  }

  public static int calculateOffset(int pageNumber, int pageSize) {
    return pageNumber * pageSize;
  }

  public static PageInfo buildPageInfo(long totalRecords, PageRequest pageRequest) {
    final var totalPages =
        PaginationUtils.getTotalPages(totalRecords, pageRequest.getPageSize()) - 1;
    final var isFirst = pageRequest.getPageNumber() == 0;
    final var isLast = totalPages == pageRequest.getPageNumber();
    return PageInfo.builder()
        .totalRecord(totalRecords)
        .totalPages(totalPages)
        .isFirst(isFirst)
        .isLast(isLast)
        .build();
  }
}
