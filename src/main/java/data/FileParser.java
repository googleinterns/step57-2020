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

  /** Map each vendor ID to an array of account IDs */
  public HashMap<String, ArrayList<String>> getConfigMap() {
    ArrayList<String> vendorIDs = getVendorIDs();
    HashMap<String, ArrayList<String>> map = new HashMap<>();

    for (String vendor : vendorIDs) {
      ArrayList<String> accounts = getAccountIDs(vendor);
      map.put(vendor, accounts);
    }
    return map;
  }

  /** Return list of vendor IDs that exist in the filesystem */
  private ArrayList<String> getVendorIDs() {
    File root = new File(basePath);
    return new ArrayList<>(Arrays.asList(Objects.requireNonNull(root.list())));
  }

  /** TODO Return list of account IDs corresponding to a given vendor */
  private ArrayList<String> getAccountIDs(String vendorID) {
    return new ArrayList<>(Arrays.asList("ECHO", "FOXTROT", "GOLF", "HOTEL"));
  }
}

