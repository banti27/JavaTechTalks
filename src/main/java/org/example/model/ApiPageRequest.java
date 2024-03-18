package org.example.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ApiPageRequest {
  private PageRequest pageRequest;
  private boolean fetchMetaData;
}
