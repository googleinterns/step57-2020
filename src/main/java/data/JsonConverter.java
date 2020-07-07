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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.json.JSONObject;

/** A class that creates a config file from a Vendor object. */
public class JsonConverter {
  private static final String FILE_PATH_BASE = "data/";
  
  public File createFile(String vendorID) {
    String fileName = FILE_PATH_BASE + vendorID;

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
  //   createFile(vendor.getVendorID());

  //   JSONObject JSONVendor = vendor.toJson();
  //   ArrayList<LinkedHashMap> JSONAccounts = createAccountJSONObjects(vendor.getAccounts());

  //   // Create and write the contents to a File.
  //   File billingFile = buildFile(vendor.getVendorID(), JSONVendor, JSONAccounts);
    
  //   if(billingFile == null) {
  //     return false;
  //   }
  //   return true;
  // }

  // // Create the Account body of the JSON file.
  // public ArrayList<LinkedHashMap> createAccountJSONObjects(ArrayList<Account> accounts) {       
  //   ArrayList<LinkedHashMap> JSONAccounts = new ArrayList<LinkedHashMap>();

  //   for(int i = 0; i < accounts.size(); i++) {
  //     JSONAccounts.add(accounts.get(i).toJson());
  //   }
    
  //   return JSONAccounts;
  // }

  // // Write the JSON objects to a file.
  // public File buildFile(String vendorID, JSONObject JSONVendor, ArrayList<LinkedHashMap> JSONAccounts) {
  //   File billingFile = null;
  //   try {
  //     billingFile = new File(FILE_PATH_BASE + vendorID);
  //     BufferedWriter out = new BufferedWriter(new FileWriter(billingFile));
  //     out.write(JSONVendor.toString());

  //     for(int i = 0; i < JSONAccounts.size(); i++) {
  //       out.write(JSONAccounts.get(i).toString());
  //     }

  //     out.close();
  //   } catch(IOException e) {
  //     return null;
  //   }
  //   return billingFile;
  // }


  // /** Retrieve and return the contents of the desired configuration. */
  // public String getConfig(String vendorID) {
  //   String configContents = "";

  //   // Retrieve the file with the corresponding vendorID.
  //   File config = new File(FILE_PATH_BASE + vendorID);

  //   Scanner input;
  //   try {
  //     input = new Scanner(config);
  //   } catch(FileNotFoundException e) {
  //     return null;
  //   }

  //   while(input.hasNextLine()) {
  //     configContents += input.nextLine();
  //   }

  //   return configContents;
  // }
}

// do a string builder for vendor and accounts