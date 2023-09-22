package com.gojek.ParkingLot;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.gojek.ParkingLot.domain.Color;
import com.gojek.ParkingLot.domain.ParkingLot;
import com.gojek.ParkingLot.domain.SlotStatus;
import com.gojek.ParkingLot.domain.Vehicle;
import com.gojek.ParkingLot.exceptions.ParkingLotFullException;

public class ParkingLotTest {

  @Test
  public void createParkingLot() {
    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    assertEquals(parkingLot.getSize(), 6);
  }

  @Test
  public void parkFirstVehicle() throws ParkingLotFullException {
    String regNumber = "KA­01­HH­1234";
    Color color = Color.White;
    Vehicle vehicle = new Vehicle(regNumber, color);
    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    parkingLot.park(vehicle);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle);
  }

  @Test
  public void parkTwoVehicle() throws ParkingLotFullException {
    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    String regNumber1 = "KA­01­HH­1234";
    Color color1 = Color.White;
    Vehicle vehicle1 = new Vehicle(regNumber1, color1);
    parkingLot.park(vehicle1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle1);

    String regNumber2 = "KA­01­HH­9999";
    Color color2 = Color.White;
    Vehicle vehicle2 = new Vehicle(regNumber2, color2);
    parkingLot.park(vehicle2);

    assertEquals(parkingLot.getParkedVehicleOnSlot(2), vehicle2);
    assertEquals(parkingLot.getSlotStatus(2), SlotStatus.BUSY);
  }

  @Test
  public void parkLeaveAndThenParkAnotherVehicle() throws ParkingLotFullException {
    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    String regNumber1 = "KA­01­HH­1234";
    Color color1 = Color.White;
    Vehicle vehicle1 = new Vehicle(regNumber1, color1);
    parkingLot.park(vehicle1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle1);

    parkingLot.leaveVehicleFromSlot(1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.FREE);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), null);


    String regNumber2 = "KA­01­HH­9999";
    Color color2 = Color.White;
    Vehicle vehicle2 = new Vehicle(regNumber2, color2);
    parkingLot.park(vehicle2);


    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle2);
    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
  }

  // slot number, vehicle reg No, Color
  @Test
  public void status() throws ParkingLotFullException {

    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    String regNumber1 = "KA­01­HH­1234";
    Color color1 = Color.White;
    Vehicle vehicle1 = new Vehicle(regNumber1, color1);
    parkingLot.park(vehicle1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle1);

    String regNumber2 = "KA­01­HH­9999";
    Color color2 = Color.White;
    Vehicle vehicle2 = new Vehicle(regNumber2, color2);
    parkingLot.park(vehicle2);

    assertEquals(parkingLot.getParkedVehicleOnSlot(2), vehicle2);
    assertEquals(parkingLot.getSlotStatus(2), SlotStatus.BUSY);

    final LinkedList<String[]> expected = new LinkedList<String[]>();
    expected.add(new String[] {"Slot No.", "Registration No", "Colour"});
    expected.add(new String[] {"1", "KA­01­HH­1234", "White"});
    expected.add(new String[] {"2", "KA­01­HH­9999", "White"});

    final LinkedList<String[]> actual = parkingLot.occupiedStatus();
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0)[0], actual.get(0)[0]);
    assertEquals(expected.get(0)[1], actual.get(0)[1]);
    assertEquals(expected.get(0)[2], actual.get(0)[2]);
    assertEquals(expected.get(1)[0], actual.get(1)[0]);
    assertEquals(expected.get(1)[1], actual.get(1)[1]);
    assertEquals(expected.get(1)[2], actual.get(1)[2]);
    assertEquals(expected.get(2)[0], actual.get(2)[0]);
    assertEquals(expected.get(2)[1], actual.get(2)[1]);
    assertEquals(expected.get(2)[2], actual.get(2)[2]);
  }

  @Test
  public void testGovtRegulationDataFetch() throws ParkingLotFullException {
    int size = 6;
    ParkingLot parkingLot = new ParkingLot(size);
    String regNumber1 = "KA­01­HH­1234";
    Color color1 = Color.White;
    Vehicle vehicle1 = new Vehicle(regNumber1, color1);
    parkingLot.park(vehicle1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle1);

    String regNumber2 = "KA­01­HH­9999";
    Color color2 = Color.White;
    Vehicle vehicle2 = new Vehicle(regNumber2, color2);
    parkingLot.park(vehicle2);

    assertEquals(parkingLot.getSlotStatus(2), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(2), vehicle2);

    String regNumber3 = "KA­01­BB­0001";
    Color color3 = Color.Black;
    Vehicle vehicle3 = new Vehicle(regNumber3, color3);
    parkingLot.park(vehicle3);

    assertEquals(parkingLot.getSlotStatus(3), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(3), vehicle3);

    String regNumber4 = "KA­01­HH­7777";
    Color color4 = Color.Red;
    Vehicle vehicle4 = new Vehicle(regNumber4, color4);
    parkingLot.park(vehicle4);

    assertEquals(parkingLot.getSlotStatus(4), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(4), vehicle4);

    String regNumber5 = "KA­01­HH­2701";
    Color color5 = Color.Blue;
    Vehicle vehicle5 = new Vehicle(regNumber5, color5);
    parkingLot.park(vehicle5);

    assertEquals(parkingLot.getSlotStatus(5), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(5), vehicle5);

    String regNumber6 = "KA­01­HH­3141";
    Color color6 = Color.Black;
    Vehicle vehicle6 = new Vehicle(regNumber6, color6);
    parkingLot.park(vehicle6);

    assertEquals(parkingLot.getParkedVehicleOnSlot(6), vehicle6);
    assertEquals(parkingLot.getSlotStatus(6), SlotStatus.BUSY);

    parkingLot.leaveVehicleFromSlot(4);
    assertEquals(parkingLot.getParkedVehicleOnSlot(4), null);
    assertEquals(parkingLot.getSlotStatus(4), SlotStatus.FREE);

    String regNumber7 = "KA­01­P­333";
    Color color7 = Color.White;
    Vehicle vehicle7 = new Vehicle(regNumber7, color7);
    parkingLot.park(vehicle7);

    assertEquals(parkingLot.getParkedVehicleOnSlot(4), vehicle7);
    assertEquals(parkingLot.getSlotStatus(4), SlotStatus.BUSY);

    List<String> regNumbers = parkingLot.getRegistrationNumbersOfCarsWithColor(Color.White);
    assertEquals(regNumbers.size(), 3);
    assertEquals(regNumbers.contains("KA­01­HH­1234"), true);
    assertEquals(regNumbers.contains("KA­01­HH­9999"), true);
    assertEquals(regNumbers.contains("KA­01­P­333"), true);

    List<Integer> slots = parkingLot.getSlotsNumbersWhereCarsWithColorParked(Color.White);
    assertEquals(slots.size(), 3);
    assertEquals(slots.contains(1), true);
    assertEquals(slots.contains(2), true);
    assertEquals(slots.contains(4), true);

    assertEquals(parkingLot.getSlotNumberForCarWithRegistrationNumber("KA­01­HH­3141"), 6);
    assertEquals(parkingLot.getSlotNumberForCarWithRegistrationNumber("MH­04­AY­1111"), null);
  }

  @Test(expected = ParkingLotFullException.class)
  public void testParkingFull() throws ParkingLotFullException {
    int size = 1;
    ParkingLot parkingLot = new ParkingLot(size);
    String regNumber1 = "KA­01­HH­1234";
    Color color1 = Color.White;
    Vehicle vehicle1 = new Vehicle(regNumber1, color1);
    parkingLot.park(vehicle1);

    assertEquals(parkingLot.getSlotStatus(1), SlotStatus.BUSY);
    assertEquals(parkingLot.getParkedVehicleOnSlot(1), vehicle1);

    String regNumber2 = "KA­01­HH­9999";
    Color color2 = Color.White;
    Vehicle vehicle2 = new Vehicle(regNumber2, color2);
    parkingLot.park(vehicle2);
  }
}
