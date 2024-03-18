package org.example.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostOfficeCollection {
  private List<PostOfficeResponse> postOfficeResponseList;
  private PageInfo pageInfo;
}
