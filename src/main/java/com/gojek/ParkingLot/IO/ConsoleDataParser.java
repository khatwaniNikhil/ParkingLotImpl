package com.gojek.ParkingLot.IO;

import java.util.Scanner;

public class ConsoleDataParser extends AbstractDataParser {

  Scanner scanner = new Scanner(System.in);

  @Override
  public String readData() {
    nextInput = scanner.nextLine();
    if (nextInput == "exit") {
      isParsingComplete = true;
    }
    return nextInput;
  }

  @Override
  public void cleanup() {
    scanner.close();
  }

}
