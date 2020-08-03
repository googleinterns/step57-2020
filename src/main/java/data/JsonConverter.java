 
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

  /**
   * Checks to see if a vendor's configuration already exists, and updates
   * it if it does. If a configuration does not exist, return false
   * without writing changes.
   * @param vendor Vendor to update configuration
   * @return configuration write status
   */
  public boolean updateFile(Vendor vendor) {
    // Create and write the contents to a File.
    if (!configExists(vendor.getVendorID())) {
      return false;
    } else {
      String jsonConfig = vendor.buildJsonConfig();
      File config = writeFile(vendor.getVendorID(), jsonConfig);
      return true;
    }
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
   * @throws FileNotFoundException thrown when configuration is not found.
   */
  public String getConfigText(String vendorID) throws FileNotFoundException {
    String configContents = "";
    // Retrieve the file with the corresponding vendorID.
    File config = new File(basePath + vendorID);

    Scanner input = new Scanner(config);

    while (input.hasNextLine()) {
      configContents += input.nextLine();
    }
    return configContents;
  }

  public String getAccountConfig(String vendorId, String accountId)
          throws IllegalArgumentException, IOException {

    String config = getConfigText(vendorId);
    Vendor vendor = new Vendor(config, vendorId);
    Account account = vendor.getAccountById(accountId);
    if (account == null) {
      throw new IllegalArgumentException();
    }
    return account.buildJsonConfig();
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

  /**
   * Converts a JSON array of Account objects to an ArrayList of Accounts
   * @param accountArray JSON representation of array of account objects
   * @return list of accounts
   */
  public static ArrayList<Account> buildAccountsFromJsonArray(JSONArray accountArray) {
    ArrayList<Account> accounts = new ArrayList<Account>();
    for (int i = 0; i < accountArray.length(); i++) {
      accounts.add(new Account(accountArray.getJSONObject(i)));
    }
    return accounts;
  }

  /** Return list of vendor IDs that exist in the filesystem.*/
  public ArrayList<String> getVendorIDs() {
    File root = new File(basePath);
    return new ArrayList<String>(Arrays.asList(Objects.requireNonNull(root.list())));
  }

  public boolean configExists(String vendorID) {
    return getVendorIDs().contains(vendorID);
  }

  public boolean accountExists(Vendor vendor, String accountId) {
    return getAccountIDs(vendor).contains(accountId);
  }

  /**
   * Returns an ArrayList of account ID strings.
   * If none exist for the vendor, an empty ArrayList is returned
   */
  private ArrayList<String> getAccountIDs(String vendorID) throws IOException {
    try {
      String config = getConfigText(vendorID);
      Vendor vendor = new Vendor(config, vendorID);
      ArrayList<String> accountIds = new ArrayList<String>();
      vendor.getAccounts().forEach(account -> accountIds.add(account.getAccountID()));

      return accountIds;
    } catch (IOException e) {
      throw new IOException("Failed to parse JSON configuration");
    }
  }

  private ArrayList<String> getAccountIDs(Vendor vendor) {
    ArrayList<String> accountIds = new ArrayList<String>();
    vendor.getAccounts().forEach(account -> accountIds.add(account.getAccountID()));

    return accountIds;
  }

  /** Delete the file that is associated with the desired vendorID. */
  public void deleteFile(String vendorID) {
    File fileToDelete = new File(basePath + vendorID); 
    fileToDelete.delete();
  }
}