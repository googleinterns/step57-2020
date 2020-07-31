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
  private static final String EDIT_FORM_DESTINATION = "editForm";

  /**
   * Read data endpoint.
   * @param request form that contains valid vendor and account IDs
   * @param response returns a configuration in JSON format
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    String accountID = request.getParameter(FormIdNames.ACCOUNT_ID);
    String destination = request.getParameter(FormIdNames.DESTINATION);
    response.setContentType(CONTENT_TYPE_APPLICATION_JSON);

    try {
      JsonConverter json = new JsonConverter();
      String configText;
      if (destination != null && destination.equals(EDIT_FORM_DESTINATION)) {
        // The edit form endpoint asks for the entire configuration text
        configText = json.getConfigText(vendorID);
      } else {
        // Read data endpoint asks for configuration text of one account
        configText = json.getAccountConfig(vendorID, accountID);
      }
      response.getWriter().println(configText);
    } catch (IOException e) {
      response.sendError(400, "Error finding " + vendorID + "'s configuration");
    } catch (IllegalArgumentException e) {
      response.sendError(400, "Failed to find account associated with vendor "
              + vendorID);
    }
  }

  /**
   * Edit/update endpoint.
   * Accepts requests that update existing accounts that belong to an existing
   * vendor or requests that create new accounts under an existing vendor.
   * @param request  form data
   * @param response if the operation succeeds, the vendorId is printed to
   *                 response object. If the operation fails, a 400 error
   *                 message is sent.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
    try {
      JsonConverter jsonConverter = new JsonConverter();
      String vendorId = request.getParameter(FormIdNames.VENDOR_ID);
      String accountId = request.getParameter(FormIdNames.ACCOUNT_ID);
      // Construct vendor object from existing JSON to make changes directly
      Vendor vendor = new Vendor(jsonConverter.getConfigText(vendorId), vendorId);

      if (jsonConverter.accountExists(vendor, accountId)) {
        // Edit an existing account.
        vendor.editVendorAccount(request);
      } else {
        // Create a new account under an existing vendor.
        vendor.addNewAccount(request);
      }

      if (jsonConverter.updateFile(vendor)) {
        // Update Google sheets following update file.
        updateSheets(request);

        // Redirect only when the operation succeeded.
        response.getWriter().println(vendor.getVendorID());
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