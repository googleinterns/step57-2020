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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.FormIdNames;

import static servlets.VendorServlet.updateSheets;

@WebServlet("/BillingConfig")
public class BillingConfig extends HttpServlet {
  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;";
  private static final String REDIRECT_INDEX = "/index.html";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    String accountID = request.getParameter(FormIdNames.ACCOUNT_ID);
    response.setContentType(CONTENT_TYPE_APPLICATION_JSON);

    try {
      JsonConverter json = new JsonConverter();
      System.out.println(accountID);
      if (accountID == null || !accountID.equals("null")) {
        // If an AccountID is not set, print the entire configuration.
        String configText = json.getConfigText(vendorID);
      } else {
        String configText = json.getAccountConfig(vendorID, accountID);
      }
      response.getWriter().println(configText);
    } catch (FileNotFoundException e) {
      response.sendError(400, "Error finding " + vendorID + "'s configuration");
    }
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

        // Redirect only when the operation succeeded.
        response.getWriter().println(newVendor.getVendorID());
        response.sendRedirect(REDIRECT_INDEX);
      } else {
        response.sendError(400, "This configuration does not exist.");
      }
    } catch(NumberFormatException e) {
      response.sendError(400, "A NumberFormatException occurred.");
    } catch(GeneralSecurityException e) {
      response.sendError(400, "A GeneralSecurityException occurred. Make sure you have authorized" +
              "the Google Sheets API.");
    } catch(IllegalStateException | NullPointerException e) {
      response.sendError(400, "Your OAuth token has expired. Visit /OAuth to refresh");
    }
  }
}