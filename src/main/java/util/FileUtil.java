package util;

import data.Vendor;

import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {
  public static ArrayList<String> getVendorIDs() {
    return new ArrayList<>(Arrays.asList("ALPHA", "BRAVO", "CHARLIE", "DELTA"));
  }

  public static ArrayList<String> getAccountIDs(String vendorID) {
    return new ArrayList<>(Arrays.asList("ECHO", "FOXTROT", "GOLF", "HOTEL"));
  }

  /** makes changes to the file in the codebase */
  public static void updateFile(Vendor vendor) {

  }

  /** return true when delete succeeds? */
  public static boolean deleteVendorConfig(String vendorID) {
    return false;
  }
}
