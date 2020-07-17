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

import com.google.gson.stream.JsonReader;
import servlets.BillingConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

  /** Construct a Vendor object from JSON string */
  public Vendor(String json) throws IOException {
    accountList = new ArrayList<>();
    InputStream in = new ByteArrayInputStream(json.getBytes());
    JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    try {
      reader.beginObject();
      reader.nextName();
      legacyVendorID = reader.nextString();
      reader.nextName();
      nextGenVendorID = reader.nextInt();
      reader.nextName();
      reader.beginArray();
      while (reader.hasNext()) {
        assert accountList != null;
        accountList.add(readJsonAccount(reader));
      }
      reader.endArray();
    } finally {
      reader.close();
    }
  }

  private Account readJsonAccount(JsonReader reader) throws IOException {
    reader.beginObject();
    reader.nextName();
    String legacyAccountID = reader.nextString();
    reader.nextName();
    int nextGenAccountID = reader.nextInt();
    reader.nextName();
    reader.beginObject();
    reader.nextName();
    String currency = reader.nextString();
    reader.nextName();
    String direction = reader.nextString();
    reader.nextName();
    String entity = reader.nextString();
    reader.endObject();
    reader.nextName();
    reader.beginObject();
    reader.nextName();
    String matchingMode = reader.nextString();
    reader.endObject();
    reader.nextName();
    String accountID = reader.nextString();
    reader.nextName();
    String aggregationMode = reader.nextString();
    String vendorID = "thisIsn'tInTheObject";
    Account newAcc = new Account(accountID, vendorID,  entity,  currency, direction,
            legacyAccountID,  nextGenAccountID, matchingMode,  aggregationMode);
    reader.endObject();;
    return newAcc;
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

  /** Return a JSON String representing a billing config's content. */
  public String toJson() {
    ArrayList<Account> accounts = getAccounts();
    String config = String.format("{\"legacy_vendor_id\":\"%s\"," +
      "\"next_gen_vendor_id\":%d,\"accounts\":[", getLegacyVendorID(), 
      getNextGenVendorID());

    for (Account account : accounts) {
      config += account.toJson() + ",";
    }
    // Remove trailing comma from list of accounts for valid JSON
    if (config.endsWith(",")) {
      config = config.substring(0, config.length() - 1);
    }
    config += "]}";
    return config;
  }
}