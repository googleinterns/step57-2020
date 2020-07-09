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

  public boolean updateFile(Vendor vendor) {
    String jsonConfig = vendor.buildJsonConfig();

    // Create and write the contents to a File.
    File billingFile = writeFile(vendor.getVendorID(), jsonConfig);
    
    return billingFile != null;
  }

  /**
   * Write a new billig config file to the local filesystem.
   * @param vendorID a String representing a Vendor's ID.
   * @param jsonConfig a String filled with billing config content.
   * @return File representing a newly built config file.
   */
  public File writeFile(String vendorID, String jsonConfig) {
    File billingFile = null;
    try {
      billingFile = new File(FILE_PATH_BASE + vendorID);
      BufferedWriter out = new BufferedWriter(new FileWriter(billingFile));
      out.write(jsonConfig);
      out.close();
    } catch(IOException e) {
      return null;
    }
    return billingFile;
  }

  /**
   * Retrieve and return the contents of the desired configuration.
   * @param vendorID a String representing a Vendor's ID.
   * @return a String representing the desired billing config.
   */
  public String getConfig(String vendorID) {
    String configContents = "";

    // Retrieve the file with the corresponding vendorID.
    File config = new File(FILE_PATH_BASE + vendorID);

    Scanner input;
    try {
      input = new Scanner(config);
    } catch(FileNotFoundException e) {
      return null;
    }

    while(input.hasNextLine()) {
      configContents += input.nextLine();
    }

    return configContents;
  }
}
