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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.JsonConverter;

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
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String vendorID = request.getParameter(VENDOR_ID_PARAM);

    // Delete the file from the filesystem.
    deleteVendorFile(vendorID);

    // Update the sheets.
    updateSheets();

    // Get the status of the delete(ex. success/failure).
    String deleteMessage = someMethod();

    // Convert the String message into a JSON String.
    String jsonMessage = messageListAsJson(commentList);

    response.setContentType(CONTENT_TYPE);
    response.getWriter().println(jsonMessage);
  }

  private void deleteVendorFile(String vendorID) {
    JsonConverter deleter = new JsonConverter();
    deleter.deleteFile(vendorID);
  }

  /** Rebuild the sheets without the deleted Vendor's data. */
  private void updateSheets() {
    JsonConverter converter = new JsonConverter();
    ArrayList<String> vendorIDs = converter.getVendorIDs();
    ArrayList<Vendor> vendors = new ArrayList<Vendor>(); 

    for(int i = 0; i < vendorIDs.size(); i++) {
      vendors.add(vendorIDs.get(i).)
    }

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