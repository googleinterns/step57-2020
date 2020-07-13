package util;

import data.Vendor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class to retrieve lists of vendors and accounts
 */
public class FileUtil {
  /** Return list of vendor IDs that exist in the filesystem */
  public static ArrayList<String> getVendorIDs() {
    return new ArrayList<>(Arrays.asList("ALPHA", "BRAVO", "CHARLIE", "DELTA"));
  }

  /** Return list of account IDs corresponding to a given vendor */
  public static ArrayList<String> getAccountIDs(String vendorID) {
    return new ArrayList<>(Arrays.asList("ECHO", "FOXTROT", "GOLF", "HOTEL"));
  }
}
