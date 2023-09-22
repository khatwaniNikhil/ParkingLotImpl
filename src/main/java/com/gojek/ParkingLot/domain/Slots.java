package com.gojek.ParkingLot.domain;

import java.util.LinkedList;

public class Slots {
  private Slot[] slots;

  public Slots(int size) {
    this.slots = new Slot[size + 1];
    for (int slotNum = 1; slotNum <= size; slotNum++) {
      slots[slotNum] = new Slot(slotNum, SlotStatus.FREE);
    }
  }

  public void markFree(int slotNumber) {
    slots[slotNumber].markFree();
  }

  public Slot getSlot(int slotNumber) {
    return slots[slotNumber];
  }

  public SlotStatus getSlotStatus(int slotNumber) {
    return slots[slotNumber].getStatus();
  }

  public Vehicle getParkedVehicle(int slotNumber) {
    return slots[slotNumber].getParkedVehicle();
  }

  public LinkedList<String[]> occupiedStatus() {
    LinkedList<String[]> table = new LinkedList<String[]>();
    table.add(new String[] {"Slot No.", "Registration No", "Colour"});
    for (int i = 1; i < slots.length; i++) {
      if (slots[i].getStatus() == SlotStatus.BUSY) {
        table.add(new String[] {String.valueOf(slots[i].getSlotNumber()),
            slots[i].getParkedVehicle().getRegNumber(),
            slots[i].getParkedVehicle().getColor().toString()});
      }
    }
    return table;
  }

}
