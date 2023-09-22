package com.gojek.ParkingLot.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileDataParser extends AbstractDataParser {
  Scanner scanner;
  InputStream inputStream;

  public FileDataParser(InputStream inputStream) throws IOException {
    this.inputStream = inputStream;
    scanner = new Scanner(inputStream);
  }

  @Override
  public String readData() throws IOException {
    if (scanner.hasNextLine()) {
      return scanner.nextLine();
    } else {
      isParsingComplete = true;
      return null;
    }
  }

  @Override
  public void cleanup() throws IOException {
    this.inputStream.close();
    scanner.close();
  }
}
