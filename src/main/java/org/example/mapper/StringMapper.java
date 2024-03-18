package org.example.mapper;

public class StringMapper {
  public String asString(String str) {
    if ("NA".equalsIgnoreCase(str)) {
      return null;
    }
    return str;
  }
}
