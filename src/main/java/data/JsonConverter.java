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

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;


public class JsonConverter {
//  private static final String FILE_PATH_BASE = "vendorconfig/vendors/";
  
  public File createFile() {
    String fileName = "practiceFile";

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
    } catch(IOException e) {
      return null;
    } 

    return file;
  }

  // public boolean updateFile(Vendor vendor) {
  //   // retrieve the old one just in case the new one doesn't build correctly. TODO!!!!!!!
  //   String filePath = FILE_PATH_BASE + vendor.getVendorID();

    // // Populate JSON objects with the configuration data. 
    // JSONObject JSONVendor = createVendorJSONObject(vendor);
    // ArrayList<JSONObject> JSONAccounts = createAccountJSONObjects(vendor.getAccounts());

    // // Write the JSON objects to a file.
    // try {
    //   FileWriter file = new FileWriter(filePath);

    //   file.write(JSONVendor.toString());
    //   for(int i = 0; i < JSONAccounts.size(); i++) {
    //     file.write(JSONAccounts.get(i).toString());
    //   }

    //   file.close();
    // } catch(IOException e) {
    //   return false;
    // }
  //   return true;
  // }

  // METHODS FOR THE MVP.

  // // Create the Vendor header of the JSON file.
  // public JSONObject createVendorJSONObject(Vendor vendor) {
  //   // Create a JSON object to contain Vendor fields.
  //   JSONObject JSONVendor = new JSONObject();

  //   JSONVendor.put("Legacy_Vendor_ID", vendor.getLegacyVendorID());
  //   JSONVendor.put("Next_Gen_Vendor_ID", vendor.getNextGenVendorID());
  //   JSONVendor.put("Accounts", vendor.getAccounts());

  //   return JSONVendor;
  // }

  // // Create the Account body of the JSON file.
  // public ArrayList<JSONObject> createAccountJSONObjects(ArrayList<Account> accounts) {
  //   // Sort the accounts alphabetically by AccountID    
        
  //   // Create a JSON objects ArrayList to contain the JSON Accounts.
  //   ArrayList<JSONObject> JSONAccounts = new ArrayList<JSONObject>();

  //   for(int i = 0; i < accounts.size(); i++) {
  //     JSONAccounts.add(createAccount(accounts.get(i)));
  //   }
    
  //   return JSONAccounts;
  // }

  // private JSONObject createAccount(Account account) {
  //   // Create a JSON objects to contain Account fields.
  //   JSONObject settlementConfig = new JSONObject();
  //   JSONObject settlementAttributes = new JSONObject();
  //   JSONObject JSONAccount = new JSONObject();

  //   // Populate settlementConfig JSON object. LEARN HOW TO PUT QUOTES AROUND A VARIABLE
  //   settlementConfig.put("Matching_Mode", account.getMatchingMode());

  //   // Populate settlementAttributes JSON object.
  //   settlementAttributes.put("Currency_Code", account.getCurrency());
  //   settlementAttributes.put("Direction", account.getDirection());
  //   settlementAttributes.put("Entity", account.getEntity());

  //   // Populate JSON account object.
  //   JSONAccount.put("Legacy_Account_ID", account.getLegacyAccountID());
  //   JSONAccount.put("Next_Gen_Customer_ID", account.getNextGenAccountID());
  //   JSONAccount.put("Settlement_Attributes", settlementAttributes);
  //   JSONAccount.put("Settlement_Config", settlementConfig);
  //   JSONAccount.put("Account_ID", account.getAccountID());
  //   JSONAccount.put("Aggregation_Mode", account.getAggregationMode());

  //   return JSONAccount;
  // }
}