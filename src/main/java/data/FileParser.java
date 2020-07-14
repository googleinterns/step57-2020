package data;

import java.util.ArrayList;
import java.util.Arrays;

public class FileParser {
  private static final String DEFAULT_FILE_PATH = "../../src/main/resources";

  private String basePath;

  public FileParser() {
    this.basePath = DEFAULT_FILE_PATH;
  }

  public FileParser(String filepath) {
    this.basePath = filepath;
  }

  /** Return list of vendor IDs that exist in the filesystem */
  public static ArrayList<String> getVendorIDs() {
    return new ArrayList<>(Arrays.asList("ALPHA", "BRAVO", "CHARLIE", "DELTA"));
  }

  /** Return list of account IDs corresponding to a given vendor */
  public static ArrayList<String> getAccountIDs(String vendorID) {
    return new ArrayList<>(Arrays.asList("ECHO", "FOXTROT", "GOLF", "HOTEL"));
  }
}

