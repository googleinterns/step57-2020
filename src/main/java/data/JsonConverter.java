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
import org.json.JSONObject;

/** A class that creates a config file from a Vendor object. */
public class JsonConverter {
  private static final String FILE_PATH_BASE = "../../src/main/resources/";
  private static final String TEST_PATH_BASE = "src/test/resources/";
  
  public File createFile(String vendorID, boolean isTesting) {
    String basePath = "";
    // base file path should change based on testing status
    if (!isTesting) {
      basePath = FILE_PATH_BASE;
    } else {
      basePath = TEST_PATH_BASE;
    }
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
    } catch(IOException e) {
      return null;
    } 

    return file;
  }

  public boolean updateFile(Vendor vendor, boolean isTesting) {
    createFile(vendor.getVendorID(), isTesting);
    return true;
  }

  /** Retrieve and return the contents of the desired configuration.
   * @param vendorID raw vendor id
   * @param isTesting Maven sources test files from a separate working directory than runtime files.
   *                 Test cases should indicate isTesting = true so the filepath is correct.
   */
  public String getConfig(String vendorID, boolean isTesting) {
    String basePath = "";
    if (!isTesting) {
      basePath = FILE_PATH_BASE;
    } else {
      basePath = TEST_PATH_BASE;
    }
    String configContents = "";

    // Retrieve the file with the corresponding vendorID.
    File config = new File(basePath + vendorID);
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