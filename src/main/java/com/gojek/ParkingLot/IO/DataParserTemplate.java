package com.gojek.ParkingLot.IO;

import java.io.IOException;

public interface DataParserTemplate {

  default public void parseDataAndGenerateOutput() throws IOException {
    String input;
    while ((input = readData()) != null && !isParsingComplete()) {
      writeData(processData(input));
    }
    cleanup();
  }

  boolean isParsingComplete();

  void writeData(String output);

  String processData(String input);

  String readData() throws IOException;

  void cleanup() throws IOException;
}
