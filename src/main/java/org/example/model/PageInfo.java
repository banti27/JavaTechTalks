package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PageInfo {
  private Long totalRecord;
  private Integer totalPages;
  private Boolean isFirst;
  private Boolean isLast;
}
