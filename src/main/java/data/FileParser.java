package data;

import java.io.File;
import java.util.*;

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
  public ArrayList<String> getVendorIDs() {
    File root = new File(basePath);
    return new ArrayList<>(Arrays.asList(Objects.requireNonNull(root.list())));
  }

  /** Return list of account IDs corresponding to a given vendor */
  public ArrayList<String> getAccountIDs(String vendorID) {
    return new ArrayList<>(Arrays.asList("ECHO", "FOXTROT", "GOLF", "HOTEL"));
  }
}

