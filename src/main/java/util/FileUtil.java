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
}
