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

import java.util.*;
import java.security.GeneralSecurityException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import data.SheetsConverter;
import data.SheetsServiceUtil;

@WebServlet("/BillingConfig")
public class BillingConfig extends HttpServlet {
  private static final String SPREADSHEET_ID = "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // GET method --> returns entire billing config as JSON
<<<<<<< HEAD
  try {
    Sheets sheetsService = SheetsServiceUtil.getSheetsService();

    ValueRange body = new ValueRange().setValues(Arrays.asList(
      Arrays.asList("Expenses January"), 
      Arrays.asList("books", "30"), 
      Arrays.asList("pens", "10"),
      Arrays.asList("Expenses February"), 
      Arrays.asList("clothes", "20"),
      Arrays.asList("shoes", "5")));

      UpdateValuesResponse result = sheetsService.spreadsheets().values()
      .update(SPREADSHEET_ID, "A1", body)
      .setValueInputOption("RAW")
      .execute();
    } catch (GeneralSecurityException e) {
    }


=======
    // return null if vendor ID doesn't exist
>>>>>>> 2c05ce2774bfaf649e42dd3b3497372e625ac36f
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
  }
}