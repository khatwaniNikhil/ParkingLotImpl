package com.gojek.ParkingLot.service;

import com.gojek.ParkingLot.domain.Ticket;
import com.gojek.ParkingLot.domain.Vehicle;
import com.gojek.ParkingLot.exceptions.ParkingLotFullException;

public interface Ticketing {
  Ticket issueTicket(Vehicle v) throws ParkingLotFullException;

  void processReturnedTicket(Ticket ticket);
}
