package com.gojek.ParkingLot.domain;

public class Ticket {
  int allocatedSlot;
  double price;
  long issueTimeStamp;
  Vehicle vehicle;

  public Ticket(int allocatedSlot, double price, long issueTimeStamp, Vehicle vehicle) {
    super();
    this.allocatedSlot = allocatedSlot;
    this.price = price;
    this.issueTimeStamp = issueTimeStamp;
    this.vehicle = vehicle;
  }

  public int getAllocatedSlot() {
    return allocatedSlot;
  }

  public double getPrice() {
    return price;
  }

  public long getIssueTimeStamp() {
    return issueTimeStamp;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }
}
