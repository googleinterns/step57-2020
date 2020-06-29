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

@WebServlet("/ReadData")
public class ReadData extends HttpServlet {
  // Returns a read-only copy of the request vendorID configuration
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // GET method
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
}