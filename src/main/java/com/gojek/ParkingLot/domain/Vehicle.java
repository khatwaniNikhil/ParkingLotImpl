package com.gojek.ParkingLot.domain;

public class Vehicle {

  private String regNumber;
  private Color color;

  public Vehicle(String regNumber, Color color) {
    this.regNumber = regNumber;
    this.color = color;
  }

  public String getRegNumber() {
    return regNumber;
  }

  public Color getColor() {
    return color;
  }
}
