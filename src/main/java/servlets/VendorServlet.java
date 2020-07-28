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

import data.JsonConverter;
import data.Vendor;
import data.Account;
import data.SheetsConverter;

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;

@WebServlet("/VendorServlet")
public class VendorServlet extends HttpServlet {
  private static final String JSON_TYPE = "application/json;";
  private final String DELETE_PAGE_REDIRECT = "/deletefile.html";
  private final String VENDOR_ID_PARAM = "vendorID";
  private static final String TOKEN_ATTRIBUTE = "accessToken";

  @Override
  /**
   * Prints a hashmap to the response object
   * STRING (Vendor ID) : ARRAY OF STRINGS (Account IDs)
   * e.g., {"vend_1":["account1","account2","account3","account4"],
   *            "vend_2":["account5","account6","account7"]}
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {

    JsonConverter converter = new JsonConverter();
    String configMap = converter.getConfigMap();
    response.setContentType(JSON_TYPE);
    response.getWriter().println(configMap);
  }

  /** Delete the desired Vendor from the fileset and spreadsheets.*/
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws IOException {  
    String vendorID = request.getParameter(VENDOR_ID_PARAM);
    String responseString = String.format(
      "Config with VendorID:%s was successfully deleted.", vendorID);

    // Delete the file from the filesystem and update the sheets.
    try {
      deleteVendorFile(vendorID);
      updateSheets(request);    
    } catch (IOException | GeneralSecurityException e) {
      responseString = "Failed to Delete File.";
    } 

    // Convert the String message into a JSON String.
    String jsonMessage = messageAsJson(responseString);
    response.setContentType(JSON_TYPE);
    response.getWriter().println(jsonMessage);
  }

  private void deleteVendorFile(String vendorID) throws FileNotFoundException {
    JsonConverter converter = new JsonConverter();
    ArrayList<String> vendorIDs = converter.getVendorIDs();

    // Validate that the vendorID exists.
    if (!vendorIDs.contains(vendorID)) {
        throw new FileNotFoundException();
    }
    converter.deleteFile(vendorID);
  }

  /** Rebuild the sheets without the deleted Vendor's data. */
  public static void updateSheets(HttpServletRequest request) throws
          IOException, GeneralSecurityException, IllegalStateException, NullPointerException {

    JsonConverter converter = new JsonConverter();
    ArrayList<String> vendorIDs = converter.getVendorIDs();
    ArrayList<Vendor> vendors = getVendors(vendorIDs, converter); 

    // Only retrieve the session if one exists.
    HttpSession session = request.getSession(false);
    if (session != null) {
      String accessToken = session.getAttribute(TOKEN_ATTRIBUTE).toString();
      SheetsConverter sheets = new SheetsConverter();
      sheets.updateSheets(vendors, accessToken);
    } else {
      throw new IllegalStateException();
    }
  }

  private static ArrayList<Vendor> getVendors(ArrayList<String> vendorIDs,
      JsonConverter converter) throws IOException {
    ArrayList<Vendor> vendors = new ArrayList<Vendor>();

    for (int i = 0; i < vendorIDs.size(); i++) {
      String vendorJson = converter.getConfig(vendorIDs.get(i));
      vendors.add(new Vendor(vendorJson, vendorIDs.get(i)));
    }
    return vendors;
  } 

  /** Converts a String into a JSON string using Gson. */
  private String messageAsJson(String deleteMessage) {
    return new Gson().toJson(deleteMessage);
  }
}