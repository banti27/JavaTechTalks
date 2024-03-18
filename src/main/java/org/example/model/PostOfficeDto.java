package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PostOfficeDto {
  Long getId();

  String getOfficeName();

  String getPincode();

  String getOfficeType();

  String getDeliveryStatus();

  String getDistrict();

  String getState();

  @JsonIgnore
  long getTotalRecords();
}
