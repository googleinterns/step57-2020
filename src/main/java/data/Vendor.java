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

import servlets.BillingConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

  public Vendor(HttpServletRequest request, String vendorID, int numAccounts) {
    // where does the account ID come from?
    this.vendorID = vendorID;
    String accountID = "dummy";
    this.legacyVendorID = request.getParameter(BillingConfig.LEGACY_CUSTOMER_ID);
    this.nextGenVendorID = Integer.parseInt(
            request.getParameter(BillingConfig.NEXT_GEN_CUSTOMER_ID));
    Account newAccount = new Account(request);
    accountList = new ArrayList<>();
    accountList.add(newAccount);
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

  /** Builds one row filled with an Vendor's data for the Sheet. */
  public List getVendorSheetsRow() {
    String[] vendorData = { 
      getVendorID(), 
      getLegacyVendorID(), 
      "" + getNextGenVendorID()
    };

    return Arrays.asList(vendorData);
  }

  /** Return a JSON String representing a billing config's content. */
  public String buildJsonConfig() {
    ArrayList<Account> accounts = getAccounts();
    String config = String.format("{\"legacy_vendor_id\":%s," +
      "\"next_gen_vendor_id\":%d,\"accounts\":[", getLegacyVendorID(), 
      getNextGenVendorID());

    for(int i = 0; i < accounts.size(); i++) {
      config += accounts.get(i).toJson();
    }
    config += "]}";

    return config;
  }
}
