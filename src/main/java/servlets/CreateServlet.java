// Copyright 2020 Google LLC
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
package servlets;

import static servlets.VendorServlet.updateSheets;

import data.JsonConverter;
import data.Vendor;
import data.Account;
import util.FormIdNames;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;";
  private static final String ADD_ACCOUNT_REDIRECT = "/add-account.html";

  @Override
  /** 
   * Creates a new file with the vendor ID as the name. The file 
   * contains the legacy customer ID and next gen customer ID and 
   * an empty array for accounts. 
   * e.g., {"legacy_customer_id":"cust_1",
   *          "next_gen_customer_id":12345,"accounts":[]}
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    JsonConverter converter = new JsonConverter();
    String vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    String legacyCustomerID = request.getParameter(FormIdNames.LEGACY_CUSTOMER_ID_QUERY);
    String nextGenCustomerID = request.getParameter(FormIdNames.NEXT_GEN_CUSTOMER_ID_QUERY);
    String responseString = String.format(
      "New Config with VendorID: %s was successfully created.", vendorID);

    // Set initial ids and file name on the new vendor.
    int nextGenCustomerIDInt = Integer.parseInt(nextGenCustomerID);
    Vendor vendor = new Vendor(vendorID, legacyCustomerID, nextGenCustomerIDInt);

    // Check if file doesn't already exist within the file system.
    try {
      ArrayList<String> currentVendors = converter.getVendorIDs();
      if (currentVendors.contains(vendorID)) {
        String jsonMessage = new Gson().toJson("File already exists in file system.");
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        response.getWriter().println(jsonMessage);
      } else {
        String jsonConfig = vendor.buildJsonConfig();
        
        // Creates the new vendor configuration file.
        File newFile = converter.writeFile(vendorID, jsonConfig);

        // Updates the Sheet to reflect the new addition.
        updateSheets(request);

        String responseJson = new Gson().toJson(responseString);
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        response.getWriter().println(responseJson);
      }
    } catch (IOException e) {
      response.sendError(400, "An error occurred creating your file.");
    } catch (NumberFormatException e) {
      response.sendError(400, "A NumberFormatException occurred.");
    } catch (GeneralSecurityException e) {
      response.sendError(400, "A GeneralSecurityException occurred. Make sure you have authorized" +
        " the Google Sheets API.");
    }
  }
}