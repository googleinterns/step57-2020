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
package servlets;

import data.JsonConverter;
import data.Vendor;
import data.Account;
import data.SheetsConverter;
import static servlets.VendorServlet.updateSheets;

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
  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;";
  public static final String REDIRECT_EDITFILE = "/editfile.html";
  private static final String TOKEN_ATTRIBUTE = "accessToken";
  private final String LEGACY_CUSTOMER_ID_PARAM = "legacycustomerID";
  private final String NEXT_GEN_CUSTOMER_ID_PARAM = "nextgencustomerID";


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // POST method --> creates a new configuration file with a pre-made json config 
    // with the given vendor id and account id and all other values as null.
    JsonConverter converter = new JsonConverter();
    Vendor vendor = new Vendor();
    String legacyCustomerID = request.getParameter(LEGACY_CUSTOMER_ID_PARAM);
    String nextGenCustomerID = request.getParameter(NEXT_GEN_CUSTOMER_ID_PARAM);
    String responseString = String.format(
      "New Config with VendorID:%s was successfully created.", legacyCustomerID);

    // Set initial ids on the new vendor
    int nextGenCustomerIDInt = Integer.parseInt(nextGenCustomerID);
    vendor.setLegacyVendorID(legacyCustomerID);
    vendor.setNextGenVendorID(nextGenCustomerIDInt);

    // Check if file doesn't already exist within the file system.
    try {
      ArrayList currentVendors = converter.getVendorIDs();
      if (currentVendors.contains(legacyCustomerID) == true) {
        response.sendError(400, "File already exists within file system.");
      } else {
        String jsonConfig = vendor.buildJsonConfig();
        
        // Creates the new vendor configuration file.
        File newFile = converter.writeFile(legacyCustomerID, jsonConfig);

        // Updates the Sheet to reflect the new addition.
        updateSheets(request);

        // TODO: Add redirect to add account page. 
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        System.out.println(responseString);
        response.getWriter().println(responseString);
      }
      // TODO: Validate this exception is being thrown?
    } catch (IOException e) {
      response.sendError(400, "An error ocurred creating your file.");
    } catch (NumberFormatException e) {
      response.sendError(400, "A NumberFormatException ocurred.");
    } catch (GeneralSecurityException e) {
      response.sendError(400, "A GeneralSecurityException occurred. Make sure you have authorized" +
        " the Google Sheets API.");
    }
  }
}


// TODO: Create endpoint makes a file with a json with all ids that are null 
// except for the vendor/account id 