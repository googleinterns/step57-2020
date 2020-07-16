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
  private final String ACCOUNT_RANGE = "'sheet2'!A1";
  private final String VENDOR_RANGE = "'sheet1'!A1";
  private final String INPUT_OPTION = "RAW";

  public boolean updateSheets(ArrayList<Vendor> vendorList, String accessToken) 
    throws GeneralSecurityException, IOException {
    writeToSheet(vendorList, accessToken); 
    return true;
  }

  public void writeToSheet(ArrayList<Vendor> vendorList, String accessToken) throws GeneralSecurityException, IOException {
    sheetsService = SheetsServiceUtil.getSheetsService(accessToken);
    ArrayList<ArrayList<Account>> allAccounts = getAllAccounts(vendorList); 

    // Retrieve the latest spreadsheet data.
    ValueRange accountBody = new ValueRange().setValues(
      buildAccountSheetBody(allAccounts));
    ValueRange vendorBody = new ValueRange().setValues(
      buildVendorSheetBody(vendorList));

    // Update the Account and Vendor spreadsheets using the update data.
    UpdateValuesResponse updateAccountSheet = sheetsService.spreadsheets()
      .values().update(SPREADSHEET_ID, ACCOUNT_RANGE, accountBody)
      .setValueInputOption(INPUT_OPTION).execute();
    UpdateValuesResponse updateVendorSheet = sheetsService.spreadsheets()
      .values().update(SPREADSHEET_ID, VENDOR_RANGE, vendorBody)
      .setValueInputOption(INPUT_OPTION).execute();
  }

  /** Gets every Account that is associated with any Vendor from the List. */
  public ArrayList<ArrayList<Account>> getAllAccounts(ArrayList<Vendor> vendors) {
    ArrayList<ArrayList<Account>> allAccounts =  new ArrayList<ArrayList<Account>>();

    for(int i = 0; i < vendors.size(); i++) {
      allAccounts.add(vendors.get(i).getAccounts());
    }

    return allAccounts; 
  }

  public List buildAccountSheetBody(ArrayList<ArrayList<Account>> accounts) {
    // TODO: @cfloeder Sort the accounts by some criteria x. 
    List<List<String>> accountSheetData = new ArrayList<List<String>>();

    for(int i = 0; i < accounts.size(); i++) {
      for(int j = 0; j < accounts.get(i).size(); j++) {
        accountSheetData.add(accounts.get(i).get(j).getAccountSheetsRow());
      }
    }
    
    return accountSheetData;
  }

  public List buildVendorSheetBody(ArrayList<Vendor> vendors) {
    // TODO: @cfloeder Sort the vendors by some criteria x. 
    List<List<String>> vendorSheetData = new ArrayList<List<String>>();

    for(int i = 0; i < vendors.size(); i++) {
      vendorSheetData.add(vendors.get(i).getVendorSheetsRow());
    }

    return vendorSheetData;
  }
}
