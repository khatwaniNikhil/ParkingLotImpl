package com.gojek.ParkingLot.service;

import com.gojek.ParkingLot.domain.ParkingLot;
import com.gojek.ParkingLot.domain.Slot;
import com.gojek.ParkingLot.domain.Ticket;
import com.gojek.ParkingLot.domain.Vehicle;
import com.gojek.ParkingLot.exceptions.ParkingLotFullException;

public class TicketingService implements Ticketing {
  ParkingLot parkingLot;

  @Override
  public Ticket issueTicket(Vehicle v) throws ParkingLotFullException {
    Slot allocatedSlot = parkingLot.park(v);
    // store registration number and color
    return new Ticket(allocatedSlot.getSlotNumber(), 100, System.currentTimeMillis(), v);
  }

  @Override
  public void processReturnedTicket(Ticket ticket) {
    parkingLot.leaveVehicleFromSlot(ticket.getAllocatedSlot());
  }

}
