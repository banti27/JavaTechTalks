package org.example.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PostOfficeCsvRow {
  @CsvBindByName(column = "office_name")
  private String officeName;

  @CsvBindByName(column = "pincode")
  private String pincode;

  @CsvBindByName(column = "office_type")
  private String officeType;

  @CsvBindByName(column = "delivery_status")
  private String deliveryStatus;

  @CsvBindByName(column = "division_name")
  private String divisionName;

  @CsvBindByName(column = "region_name")
  private String regionName;

  @CsvBindByName(column = "circle_name")
  private String circleName;

  @CsvBindByName(column = "taluk")
  private String taluk;

  @CsvBindByName(column = "district_name")
  private String district;

  @CsvBindByName(column = "state_name")
  private String state;

  @CsvBindByName(column = "telephone")
  private String telephone;

  @CsvBindByName(column = "related_suboffice")
  private String relatedSubOffice;

  @CsvBindByName(column = "related_headoffice")
  private String relatedHeadOffice;

  @CsvBindByName(column = "longitude")
  private String longitude;

  @CsvBindByName(column = "latitude")
  private String latitude;
}
