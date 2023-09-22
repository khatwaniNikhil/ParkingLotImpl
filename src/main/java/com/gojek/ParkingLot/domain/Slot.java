package com.gojek.ParkingLot.domain;

public class Slot {
  private int slotNumber;
  private SlotStatus status;
  private Vehicle parkedVehicle;

  Slot(int slotNumber, SlotStatus slotStatus) {
    this.slotNumber = slotNumber;
    this.status = slotStatus;
  }

  public void markFree() {
    this.status = SlotStatus.FREE;
    this.parkedVehicle = null;
  }

  public SlotStatus getStatus() {
    return status;
  }

  public void setStatus(SlotStatus status) {
    this.status = status;
  }

  public int getSlotNumber() {
    return slotNumber;
  }

  public Vehicle getParkedVehicle() {
    return parkedVehicle;
  }

  public void setParkedVehicle(Vehicle parkedVehicle) {
    this.parkedVehicle = parkedVehicle;
  }

}
