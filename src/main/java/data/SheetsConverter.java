// Copyright 2019 Google LLC
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
package data;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A class that updates the Account and Vendor spreadsheets. */
public class SheetsConverter {
  private static Sheets sheetsService;
  private static final String SPREADSHEET_ID = "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";
  private final String ACCOUNT_TITLE = "Account Sheet";
  private final String VENDOR_TITLE = "Vendor Sheet";

  public boolean updateSheets(ArrayList<Vendor> vendorList, String accessToken) 
    throws GeneralSecurityException, IOException {
    writeToSheet(accessToken); 
    return true;
  }

  // Enter unimportant data to a spreadsheet.
  public static void writeToSheet(String accessToken) throws GeneralSecurityException, IOException {
    sheetsService = SheetsServiceUtil.getSheetsService(accessToken);

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
  }
}