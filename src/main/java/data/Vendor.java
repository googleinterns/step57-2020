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

import org.json.JSONArray;
import org.json.JSONObject;
import util.FormIdNames;
import util.JsonKeys;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/** A class representing a billing vendor object. */
public class Vendor {
  private String vendorID;
  private String legacyVendorID;
  private int nextGenVendorID;
  private ArrayList<Account> accountList;

  public Vendor() {
    accountList = new ArrayList<Account>();
  }

  public Vendor(String vendorID, String legacyVendorID, int nextGenVendorID) {
    this.vendorID = vendorID;
    this.legacyVendorID = legacyVendorID;
    this.nextGenVendorID = nextGenVendorID;
    accountList = new ArrayList<Account>();
  }

  public Vendor(HttpServletRequest request) throws NumberFormatException {
    this.vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    this.legacyVendorID = request.getParameter(FormIdNames.LEGACY_CUSTOMER_ID);
    this.nextGenVendorID = Integer.parseInt(request.getParameter(
            FormIdNames.NEXT_GEN_CUSTOMER_ID));
    Account newAccount = new Account(request);
    accountList = new ArrayList<>();
    accountList.add(newAccount);
  }

  /** Construct a Vendor object from JSON string. */
  public Vendor(String json, String vendorID) throws IOException{
    JSONObject vendorJson = new JSONObject(json);
    this.vendorID = vendorID;
    this.legacyVendorID = vendorJson.getString(JsonKeys.LEGACY_CUSTOMER_ID_KEY);
    this.nextGenVendorID = vendorJson.getInt(JsonKeys.NEXT_GEN_CUSTOMER_ID);

    // Populate account list from JSON
    JSONArray accountArray = vendorJson.getJSONArray(JsonKeys.ACCOUNT_ARRAY_KEY);
    this.accountList = JsonConverter.buildAccountsFromJsonArray(accountArray);
  }

  public String getVendorID() {
    return vendorID; 
  }

  public void setVendorID(String vendorID) {
    this.vendorID = vendorID;
  }

  public String getLegacyVendorID() {
    return legacyVendorID;
  } 

  public void setLegacyVendorID(String legacyVendorID) {
    this.legacyVendorID = legacyVendorID;
  }

  public int getNextGenVendorID() {
    return nextGenVendorID;
  }
 
  public void setNextGenVendorID(int nextGenVendorID) {
    this.nextGenVendorID = nextGenVendorID;
  }

  public void addAccount(Account account) {
    accountList.add(account);
  }

  public ArrayList<Account> getAccounts() {
    return accountList;
  }

  public static List<String> getVendorSheetHeader() {
    String[] accountData = {  
      FormIdNames.VENDOR_ID, 
      FormIdNames.LEGACY_CUSTOMER_ID,
      FormIdNames.NEXT_GEN_CUSTOMER_ID
    };

    return Arrays.asList(accountData);
  }

  /** Builds one row filled with an Vendor's data for the Sheet. */
  public List<String> getVendorSheetsRow() {
    String[] vendorData = { 
      getVendorID(), 
      getLegacyVendorID(), 
      Integer.toString(getNextGenVendorID())
    };

    return Arrays.asList(vendorData);
  }

  /** Return a JSON String representing a billing config's content. */
  public String buildJsonConfig() {
    ArrayList<Account> accounts = getAccounts();
    String config = String.format("{\"legacy_customer_id\":\"%s\"," +
      "\"next_gen_customer_id\":%d,\"accounts\":[", getLegacyVendorID(),
      getNextGenVendorID());

    if (!accounts.isEmpty()) {
      config += accounts.stream().map(Account::buildJsonConfig)
        .collect(Collectors.joining(","));
    }
    config += "]}";
    return config;
  }
//
//  public Vendor update(Vendor other) {
//    return;
//  }
}
