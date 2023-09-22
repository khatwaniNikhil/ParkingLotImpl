package com.gojek.ParkingLot.IO;

import java.util.LinkedList;
import java.util.List;

import com.gojek.ParkingLot.domain.Color;
import com.gojek.ParkingLot.domain.ParkingLot;
import com.gojek.ParkingLot.domain.Slot;
import com.gojek.ParkingLot.domain.Vehicle;
import com.gojek.ParkingLot.exceptions.ParkingLotFullException;

public abstract class AbstractDataParser implements DataParserTemplate {

  ParkingLot parkingLot;
  boolean isParsingComplete = false;
  String nextInput;

  public String processData(String input) {
    String[] inputTokens = input.split(" ");

    switch (inputTokens[0]) {
      case "create_parking_lot": {
        int noSlots;
        if (parkingLot == null) {
          noSlots = Integer.valueOf(inputTokens[1]);
          parkingLot = new ParkingLot(noSlots);
          return "Created a parking lot with " + noSlots + " slots";
        } else {
          throw new IllegalArgumentException();
        }
      }
      case "park": {
        String regNo = inputTokens[1];
        Color color = Color.valueOf(inputTokens[2]);
        try {
          Slot allotedSlot = parkingLot.park(new Vehicle(regNo, color));
          return "Allocated slot number: " + allotedSlot.getSlotNumber();
        } catch (ParkingLotFullException exception) {
          return "Sorry, parking lot is full";
        }
      }
      case "leave": {
        int slotToLeave = Integer.valueOf(inputTokens[1]);
        parkingLot.leaveVehicleFromSlot(slotToLeave);
        return "Slot number " + slotToLeave + " is free";
      }
      case "status": {
        LinkedList<String[]> status = parkingLot.occupiedStatus();
        StringBuffer buffer = new StringBuffer();
        for (String[] s : status) {
          for (String t : s) {
            buffer.append(t + "\t");
          }
          buffer.append("\n");
        }
        return buffer.toString();
      }
      case "registration_numbers_for_cars_with_colour": {
        Color color = Color.valueOf(inputTokens[1]);
        List<String> strList = parkingLot.getRegistrationNumbersOfCarsWithColor(color);
        StringBuffer buffer = new StringBuffer();
        for (String s : strList) {
          buffer.append("," + s);
        }
        return buffer.substring(1);
      }
      case "slot_numbers_for_cars_with_colour": {
        Color color = Color.valueOf(inputTokens[1]);
        List<Integer> slotList = parkingLot.getSlotsNumbersWhereCarsWithColorParked(color);
        StringBuffer buffer = new StringBuffer();
        for (Integer slot : slotList) {
          buffer.append("," + slot);
        }
        return buffer.substring(1);
      }
      case "slot_number_for_registration_number": {
        String regNo = inputTokens[1];
        Integer slotNo = parkingLot.getSlotNumberForCarWithRegistrationNumber(regNo);
        if (slotNo == null) {
          return "Not found";
        } else {
          return slotNo.toString();
        }
      }
      case "exit": {
        System.exit(1);
      }
      default:
        return "Try again with valid input, Type exit to finish";
    }
  }

  @Override
  public boolean isParsingComplete() {
    return isParsingComplete;
  }

  @Override
  public void writeData(String output) {
    System.out.println(output + "\n");
  }
}
