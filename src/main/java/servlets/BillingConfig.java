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
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BillingConfig")
public class BillingConfig extends HttpServlet {
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;";

  public static final String LEGACY_CUSTOMER_ID = "legacy-customer-id";
  public static final String NEXT_GEN_CUSTOMER_ID = "next-gen-customer-id";
  public static final String LEGACY_ACCOUNT_ID = "legacy-account-id";
  public static final String NEXT_GEN_ACCOUNT_ID = "next-gen-account-id";
  public static final String CURRENCY_CODE = "currency-code";
  public static final String DIRECTION = "direction";
  public static final String ENTITY = "entity";
  public static final String MATCHING_MODE = "matching-mode";
  public static final String PRODUCT_ACCOUNT_KEY = "product-account-key";
  public static final String AGGREGATION_MODE = "aggregation-mode";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // GET method --> returns entire billing config as JSON
    // return null if vendor ID doesn't exist
    response.setContentType("text/html;");
    response.getWriter().println(
            "{ \"legacy_customer_id\": \"STRING\", " +
            "\"next_gen_customer_id\": \"INT\", \"accounts\": [ { \"legacy_account_id\": " +
            "\"STRING\", \"next_gen_customer_id\": \"INT\", \"settlement_attributes\": " +
            "{ \"currency_code\": \"STRING\", \"direction\": \"STRING\", \"entity\": " +
            "\"STRING\" }, \"settlement_config\": { \"matching_mode\": \"STRING\" }, " +
            "\"product_account_key\": \"STRING\", \"aggregation_mode\": \"STRING\" }, " +
            "{ \"legacy_account_id\": \"STRING\", \"next_gen_customer_id\": \"INT\"," +
            " \"settlement_attributes\": { \"currency_code\": \"STRING\", \"direction\": " +
            "\"STRING\", \"entity\": \"STRING\" }, \"settlement_config\": { \"matching_mode\": " +
            "\"STRING\" }, \"product_account_key\": \"STRING\", \"aggregation_mode\": " +
            "\"STRING\" } ] }"
    );
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // POST method --> overwrites the existing configuration or creates a new one
    // currently assuming there is only one account --> add this to the form @Vincent?
    System.out.println(request.getParameter(LEGACY_CUSTOMER_ID));
    System.out.println(request.getParameter(NEXT_GEN_CUSTOMER_ID));
    System.out.println(request.getParameter(LEGACY_ACCOUNT_ID));
    System.out.println(request.getParameter(NEXT_GEN_ACCOUNT_ID));
    System.out.println(request.getParameter(CURRENCY_CODE));
    System.out.println(request.getParameter(DIRECTION));
    System.out.println(request.getParameter(ENTITY));
    System.out.println(request.getParameter(MATCHING_MODE));
    System.out.println(request.getParameter(PRODUCT_ACCOUNT_KEY));
    System.out.println(request.getParameter(AGGREGATION_MODE));
    response.setContentType(CONTENT_TYPE_TEXT_HTML);
//    response.sendRedirect("/index.html");
  }
}