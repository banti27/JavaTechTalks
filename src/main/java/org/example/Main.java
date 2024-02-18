package org.example;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }

  public void print(String value) {
    System.out.println("FROM STRING");
  }

  public void print(Object value) {
    System.out.println("FROM OBJECT");
  }
}
