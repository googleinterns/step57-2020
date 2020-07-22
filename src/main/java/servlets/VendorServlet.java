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
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;

@WebServlet("/VendorServlet")
public class VendorServlet extends HttpServlet {
  private final String CONTENT_TYPE = "text/html;";
  private final String DELETE_PAGE_REDIRECT = "/deletefile.html";
  private final String VENDOR_ID_PARAM = "vendorID";

  @Override
  /**
   * Prints a hashmap to the response object
   * STRING (Vendor ID) : ARRAY OF STRINGS (Account IDs)
   * e.g., {"vend_1":["account1","account2","account3","account4"],
   *            "vend_2":["account5","account6","account7"]}
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    JsonConverter converter = new JsonConverter();
    String configMap = converter.getConfigMap();
    response.setContentType(CONTENT_TYPE);
    response.getWriter().println(configMap);
  }

  /** Delete the desired Vendor from the fileset and spreadsheets if posible.*/
  public void doDelete(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("Got Here");
    
    String vendorID = request.getParameter(VENDOR_ID_PARAM);
    String deleteStatus = "success";

    // Delete the file from the filesystem and update the sheets.
    try {
      deleteVendorFile(vendorID);
      updateSheets(request);    
    } catch (IOException e) {
      deleteStatus = "An IOException was thrown.";
    } catch (GeneralSecurityException e) {
      deleteStatus = "A GeneralSecurityException was thrown.";
    } 

    // Convert the String message into a JSON String.
    String jsonMessage = messageListAsJson(deleteStatus);
    try {
    response.setContentType(CONTENT_TYPE);
    response.getWriter().println(jsonMessage);
    } catch (IOException e ) {

    }
  }

  private void deleteVendorFile(String vendorID) throws IOException {
    JsonConverter converter = new JsonConverter();
    ArrayList<String> vendorIDs = converter.getVendorIDs();

    // Validate that the vendorID exists.
    if(!vendorIDs.contains(vendorID)) {
        throw new IOException();
    }
    converter.deleteFile(vendorID);
  }

  /** Rebuild the sheets without the deleted Vendor's data. */
  private void updateSheets(HttpServletRequest request) 
      throws IOException, GeneralSecurityException {
    JsonConverter converter = new JsonConverter();
    ArrayList<String> vendorIDs = converter.getVendorIDs();
    ArrayList<Vendor> vendors = new ArrayList<Vendor>(); 

    // Add the Vendors associated with the existing vendorIDs.
    for(int i = 0; i < vendorIDs.size(); i++) {
      String vendorJson = converter.getConfig(vendorIDs.get(i));
      vendors.add(new Vendor(vendorJson, vendorIDs.get(i)));
    }

    // Only retrieve the session if one exists.
    HttpSession session = request.getSession(false);
    String accessToken = session.getAttribute("accessToken").toString();

    SheetsConverter sheets = new SheetsConverter();
    sheets.updateSheets(vendors, accessToken);
  }

  /**
   * Converts a String into a JSON string using Gson.  
   */
  private String messageListAsJson(String deleteMessage) {
    Gson gson = new Gson();
    String jsonMessage = gson.toJson(deleteMessage);
    return jsonMessage;
  }
}