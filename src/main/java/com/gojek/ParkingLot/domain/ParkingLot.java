package com.gojek.ParkingLot.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gojek.ParkingLot.IO.ConsoleDataParser;
import com.gojek.ParkingLot.IO.DataParserTemplate;
import com.gojek.ParkingLot.IO.FileDataParser;
import com.gojek.ParkingLot.exceptions.ParkingLotFullException;


public class ParkingLot {
  private int size;
  private Slots slots;
  private MinHeap shortestDistFreeSlotHeap;
  private Map<Color, Map<Integer, Slot>> colorSlotMap;
  private Map<String, Slot> registrationNumberSlotMap;

  public static void main(String[] args) throws IOException {
    DataParserTemplate parser = null;
    if (args.length == 0) {
      System.out.println("To finish, Type exit");
      parser = new ConsoleDataParser();
      parser.parseDataAndGenerateOutput();
    } else if (args[0].contains(".txt")) {
      InputStream iStream = ParkingLot.class.getResourceAsStream("/" + args[0]);
      if (iStream != null) {
        parser = new FileDataParser(iStream);
        parser.parseDataAndGenerateOutput();
      } else {
        File file = new File(args[0]);
        if (file.exists()) {
          parser = new FileDataParser(new FileInputStream(file));
          parser.parseDataAndGenerateOutput();
        }
      }
    } else {
      System.out.println("Input should be a valid txt file");
      System.exit(1);
    }
  }

  public ParkingLot(int size) {
    this.size = size;
    slots = new Slots(size);
    shortestDistFreeSlotHeap = new MinHeap();
    colorSlotMap = new HashMap<>();
    registrationNumberSlotMap = new HashMap<>();
    for (int slotNum = 1; slotNum <= size; slotNum++) {
      shortestDistFreeSlotHeap.add(slotNum);
    }
  }

  public Slot park(Vehicle vehicle) throws ParkingLotFullException {
    // sanity check
    if (registrationNumberSlotMap.containsKey(vehicle.getRegNumber())) {
      throw new IllegalStateException();
    }
    Slot allotedSlot = getNextFreeSlotOfShortestDistance();
    allotedSlot.setStatus(SlotStatus.BUSY);
    allotedSlot.setParkedVehicle(vehicle);
    addEntryToColorSlotMap(allotedSlot, vehicle);
    registrationNumberSlotMap.put(vehicle.getRegNumber(), allotedSlot);
    return allotedSlot;
  }

  public void leaveVehicleFromSlot(int slotNumber) {
    Vehicle parkedVehicle = slots.getParkedVehicle(slotNumber);
    if (parkedVehicle == null) {
      throw new IllegalStateException();
    }
    slots.markFree(slotNumber);
    removeEntryFromColorSlotMap(slotNumber, parkedVehicle);
    registrationNumberSlotMap.remove(parkedVehicle.getRegNumber());
    shortestDistFreeSlotHeap.add(slotNumber);
  }

  public List<String> getRegistrationNumbersOfCarsWithColor(Color color) {
    List<String> numbers = new LinkedList<String>();
    if (colorSlotMap.containsKey(color)) {
      Collection<Slot> slots = colorSlotMap.get(color).values();
      for (Slot slot : slots) {
        numbers.add(slot.getParkedVehicle().getRegNumber());
      }
    }
    return numbers;
  }

  public List<Integer> getSlotsNumbersWhereCarsWithColorParked(Color color) {
    List<Integer> numbers = new LinkedList<Integer>();
    if (colorSlotMap.containsKey(color)) {
      Collection<Slot> slots = colorSlotMap.get(color).values();
      for (Slot slot : slots) {
        numbers.add(slot.getSlotNumber());
      }
    }
    return numbers;
  }

  public Integer getSlotNumberForCarWithRegistrationNumber(String regNumber) {
    return (registrationNumberSlotMap.get(regNumber) == null) ? null : (registrationNumberSlotMap
        .get(regNumber).getSlotNumber());
  }

  private void addEntryToColorSlotMap(Slot allotedSlot, Vehicle vehicle) {
    if (colorSlotMap.containsKey(vehicle.getColor())) {
      Map<Integer, Slot> map = colorSlotMap.get(vehicle.getColor());
      map.put(allotedSlot.getSlotNumber(), allotedSlot);
    } else {
      Map<Integer, Slot> map = new HashMap<>();
      map.put(allotedSlot.getSlotNumber(), allotedSlot);
      colorSlotMap.put(vehicle.getColor(), map);
    }
  }

  private void removeEntryFromColorSlotMap(int slotNumber, Vehicle vehicle) {
    if (colorSlotMap.containsKey(vehicle.getColor())) {
      Map<Integer, Slot> map = colorSlotMap.get(vehicle.getColor());
      map.remove(slotNumber);
    }
  }

  private Slot getNextFreeSlotOfShortestDistance() throws ParkingLotFullException {
    if (shortestDistFreeSlotHeap.size() == 0) {
      throw new ParkingLotFullException();
    }
    return slots.getSlot(shortestDistFreeSlotHeap.remove());
  }

  public SlotStatus getSlotStatus(int slotNumber) {
    return slots.getSlotStatus(slotNumber);
  }

  public Vehicle getParkedVehicleOnSlot(int slotNumber) {
    return slots.getParkedVehicle(slotNumber);
  }

  public int getSize() {
    return size;
  }

  public LinkedList<String[]> occupiedStatus() {
    return slots.occupiedStatus();
  }
}
