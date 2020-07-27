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

import java.io.IOException;
import java.util.ArrayList;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormIdNames;

import static servlets.VendorServlet.updateSheets;

@WebServlet("/BillingConfig")
public class BillingConfig extends HttpServlet {
  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;";
  private static final String REDIRECT_READFILE = "/index.html";

  private Vendor vendor;
  private Account account;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
    // GET method --> returns entire billing config as JSON
    // return null if vendor ID doesn't exist
    String vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    String accountID = request.getParameter(FormIdNames.ACCOUNT_ID);

    JsonConverter json = new JsonConverter();
    String configText = "";

    if (json.getConfig(vendorID) != null) {
      configText = json.getConfig(vendorID);
    } else {
      configText = "Error finding " + vendorID + "'s configuration";
    }

    response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
    response.getWriter().println(configText);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
    try {
      Vendor newVendor = new Vendor(request);
      JsonConverter jsonConverter = new JsonConverter();
      if (jsonConverter.updateFile(newVendor)) {
        // Update Google sheets following update file.
        updateSheets(request);
        // Redirect only when the operation succeeded
        response.getWriter().println(newVendor.getVendorID());
        response.sendRedirect(REDIRECT_READFILE);
      } else {
        response.sendError(400, "This configuration does not exist.");
      }
    } catch(NumberFormatException e) {
      response.sendError(400, "A NumberFormatException occurred.");
    } catch(GeneralSecurityException e) {
      response.sendError(400, "A GeneralSecurityException occurred. Make sure you have authorized" +
              "the Google Sheets API.");
    }
  }
}