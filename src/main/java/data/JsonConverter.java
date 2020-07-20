 
// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package data;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

/** A class that creates a config file from a Vendor object. */
public class JsonConverter {
  private static final String DEFAULT_FILE_PATH = "../../src/main/resources/";

  private String basePath;

  public JsonConverter() {
    basePath = DEFAULT_FILE_PATH;
  }

  public JsonConverter(String filePathName) {
    basePath = filePathName;
  }

  public File createFile(String vendorID) {
    String fileName = basePath + vendorID;
    // Create a JSON object to write to the file.
    JSONObject parentObject = new JSONObject();
    JSONObject JSONCar = new JSONObject();
    JSONCar.put("Car", "Blue Tacoma");
    parentObject.put("Vehicle", JSONCar);
    // Write the JSON object to the file.
    File file = null;
    try {
      file = new File(fileName);
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      out.write(parentObject.toString());
      out.close();
    } catch (IOException e) {
      return null;
    }
    return file;
  }

  public boolean updateFile(Vendor vendor) {
    String jsonConfig = vendor.toJson();
    // Create and write the contents to a File.
    File billingFile = writeFile(vendor.getVendorID(), jsonConfig);

    return billingFile != null;
  }

  /**
   * Write a new billing config file to the local filesystem.
   *
   * @param vendorID   a String representing a Vendor's ID.
   * @param jsonConfig a String filled with billing config content.
   * @return File representing a newly built config file.
   */
  public File writeFile(String vendorID, String jsonConfig) {
    File billingFile = null;
    try {
      billingFile = new File(basePath + vendorID);
      BufferedWriter out = new BufferedWriter(new FileWriter(billingFile));
      out.write(jsonConfig);
      out.close();
    } catch (IOException e) {
      return null;
    }
    return billingFile;
  }

  /**
   * Retrieve and return the contents of the desired configuration.
   */
  public String getConfig(String vendorID) {
    String configContents = "";

    // Retrieve the file with the corresponding vendorID.
    File config = new File(basePath + vendorID);

    Scanner input;
    try {
      input = new Scanner(config);
    } catch(FileNotFoundException e) {
      return null;
    }

    while (input.hasNextLine()) {
      configContents += input.nextLine();
    }
    return configContents;
  }

  /**
   * Map each vendor ID to an array of account IDs.
   * @return JSON dictionary
   */
  public String getConfigMap() throws IOException {
    ArrayList<String> vendorIDs = getVendorIDs();
    HashMap<String, ArrayList<String>> map = new HashMap<>();

    for (String vendor : vendorIDs) {
      ArrayList<String> accounts = getAccountIDs(vendor);
      map.put(vendor, accounts);
    }

    Gson gson = new Gson();
    return gson.toJson(map);
  }

  public static ArrayList<Account> buildAccountsFromJsonArray(JSONArray accountArray) {
    ArrayList<Account> accounts = new ArrayList<>();
    for (int i = 0; i < accountArray.length(); i++) {
      accounts.add(new Account(accountArray.getJSONObject(i)));
    }
    return accounts;
  }

  /** Return list of vendor IDs that exist in the filesystem.*/
  private ArrayList<String> getVendorIDs() {
    File root = new File(basePath);
    return new ArrayList<>(Arrays.asList(Objects.requireNonNull(root.list())));
  }

  private ArrayList<String> getAccountIDs(String vendorID) throws IOException {
    try {
      String config = getConfig(vendorID);
      Vendor vendor = new Vendor(getConfig(vendorID), vendorID);
      ArrayList<Account> accounts = vendor.getAccounts();
      ArrayList<String> accountIds = new ArrayList<>();

      if (accounts.isEmpty()) {
        accountIds.add("No accounts found!");
        // TODO: We need a better system for error handling here
        return accountIds;
      }

      for (Account account : accounts) {
        accountIds.add(account.getAccountID());
      }

      return accountIds;
    } catch (IOException e) {
      return null;
    }
  }
}