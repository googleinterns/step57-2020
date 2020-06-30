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

  // IGNORE COMMENTED OUT SECTIONS! 

  private static Sheets sheetsService;
  private static final String SPREADSHEET_ID = "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";
  private final String ACCOUNT_TITLE = "Account Sheet";
  private final String VENDOR_TITLE = "Vendor Sheet";

  public boolean updateSheets(ArrayList<Vendor> vendorList) 
    throws GeneralSecurityException, IOException {
    
    writeToSheet();

    // Create a list of all the accounts that the vendors have.
    // ArrayList<Account> accounts = getVendorAccounts(vendorList);
        
    // Create the Account spreadsheet.
    // updateAccountSheet(accounts);
    // Create the Vendor spreadsheet.
    // updateVendorSheet(vendorList);
      return true;
  }

  // Enter unimportant data to a spreadsheet.
  public static void writeToSheet() throws GeneralSecurityException, IOException {
    sheetsService = SheetsServiceUtil.getSheetsService();

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

  // Get all the vendors' accounts and store them in an ArrayList.
  // public ArrayList<Account> getVendorAccounts(ArrayList<Vendor> vendorList) {
  //     ArrayList<Account> accounts = new ArrayList<Account>();

  //     for(int i = 0; i < vendorList.size(); i++) {
  //         accounts.addAll(vendorList.get(i).getAccounts());
  //     }
  //     return accounts;
  // }

  // Update the existing Account spreadsheet with the updated data.
  // public void updateAccountSheet(ArrayList<Account> accounts) {
    // Alphabatize the list by AccountID. POSSIBLY MAKE COMPARATORS IN THE VENDOR AND ACCOUNT CLASSES

    // Actually update the Account sheet.
    // writeToSheet(new ValueRange);
  // }

  // Update the existing Vendor spreadsheet with the updated data.
  // public void updateVendorSheet(ArrayList<Vendor> vendorList) {
    // Alphabatize the list by VendorID. POSSIBLY MAKE COMPARATORS IN THE VENDOR AND ACCOUNT CLASSES

    // Actually update the Vendor sheet.
    // writeToSheet(new ValueRange);
  // }

  // public void writeToSheet(ValueRange contents) {
    // implement when you actually get data.
  // }
}