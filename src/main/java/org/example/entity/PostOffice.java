package org.example.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "post_office")
public class PostOffice implements Serializable {
  public static final long serialVersionUID = 42L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String officeName;

  private String pincode;

  private String officeType;

  private String deliveryStatus;

  private String divisionName;

  private String regionName;

  private String circleName;

  private String taluk;

  private String district;

  private String state;

  private String telephone;

  private String relatedSubOffice;

  private String relatedHeadOffice;

  private String longitude;

  private String latitude;
}
