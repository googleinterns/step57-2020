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
import data.SheetsConverter;

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

@WebServlet("/BillingConfig")
public class BillingConfig extends HttpServlet {
  // May need to be "application/json;
  private static final String CONTENT_TYPE_TEXT_HTML = "application/json;";
  private static final String REDIRECT_READFILE = "/index.html";

  public static final String VENDOR_ID = "vendor-id";
  public static final String ACCOUNT_ID = "account-id";
  public static final String LEGACY_CUSTOMER_ID = "legacy-customer-id";
  public static final String NEXT_GEN_CUSTOMER_ID = "next-gen-customer-id";
  public static final String LEGACY_ACCOUNT_ID = "legacy-account-id";
  public static final String NEXT_GEN_ACCOUNT_ID = "next-gen-account-id";
  public static final String CURRENCY_CODE = "currency-code";
  public static final String DIRECTION = "direction";
  public static final String ENTITY = "entity";
  public static final String MATCHING_MODE = "matching-mode";
  public static final String AGGREGATION_MODE = "aggregation-mode";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
    // GET method --> returns entire billing config as JSON
    // return null if vendor ID doesn't exist
    String vendorID = request.getParameter(VENDOR_ID);
    vendorID = "sample";
    // TODO: @cade Add query string functionality to find vendor configs. 
    String accountID = request.getParameter(ACCOUNT_ID);
    JsonConverter json = new JsonConverter();
    String configText = "";

    if (json.getConfig(vendorID) != null) {
      configText = json.getConfig(vendorID);
    } else {
      configText = "Error finding " + vendorID + "'s configuration";
    }
    System.out.println(configText);

    /** 
     * TODO: @cade, eventually you will want to pass this access token into the 
     * updateSheets() method in the SheetsConverter class.
     */
    // Only retrieves the session if one exists.
    HttpSession session = request.getSession(false);
    String accessToken = session.getAttribute("accessToken").toString();

    SheetsConverter sheet = new SheetsConverter(); 
    try {
      sheet.writeToSheet(accessToken);
    } catch (GeneralSecurityException e) {
      // TODO: @cade Figure out how you want to handle this error.
    }

    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    response.getWriter().println(configText);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // POST method --> overwrites the existing configuration or creates a new one
    // currently assuming there is only one account --> add this to the form @Vincent?
    // TODO: can the vendor ID be appended to the request as a query string @vincent?
    String tempVendorID = "ALPHA";
    Vendor newVendor = new Vendor(request, tempVendorID, 1);
    JsonConverter jsonConverter = new JsonConverter();
    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    response.sendRedirect("/index.html");
  }
}