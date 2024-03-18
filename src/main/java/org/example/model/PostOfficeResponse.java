package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostOfficeResponse {
  private Long id;
  private String officeName;
  private String pincode;
  private String officeType;
  private String deliveryStatus;
  private String district;
  private String state;
}
