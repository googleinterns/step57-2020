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
import com.google.api.services.sheets.v4.model.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/** A class that updates the Account and Vendor spreadsheets. */
public class SheetsConverter {
  private static Sheets sheetsService;
  private static final String SPREADSHEET_ID = 
    "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";
  private final String ACCOUNT_RANGE = "'sheet2'!A1";
  private final String VENDOR_RANGE = "'sheet1'!A1";
  private final String TAB_1 = "Sheet1";
  private final String TAB_2 = "Sheet2";
  private final String INPUT_OPTION = "RAW";

  public void updateSheets(ArrayList<Vendor> vendorList, String accessToken) 
    throws GeneralSecurityException, IOException {
    sheetsService = SheetsServiceUtil.getSheetsService(accessToken);
    clearSheets(sheetsService);
    writeToSheet(vendorList, sheetsService); 
  }

  public void writeToSheet(ArrayList<Vendor> vendorList, Sheets sheetsService) 
    throws GeneralSecurityException, IOException {
    HashMap<String, ArrayList<Account>> allAccounts = getAllAccounts(vendorList); 

    // Retrieve the latest spreadsheet data as a List<List<Object>> 
    // as required by the Sheets API.
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

  /** Clear all data from the Account and Vendor spreadsheets. */
  public void clearSheets(Sheets sheetsService) throws IOException {
    ClearValuesRequest requestBody = new ClearValuesRequest();

    ClearValuesResponse clearAccount =  sheetsService.spreadsheets().values()
      .clear(SPREADSHEET_ID, TAB_1, requestBody).execute();
    ClearValuesResponse clearVendor = sheetsService.spreadsheets().values()
      .clear(SPREADSHEET_ID, TAB_2, requestBody).execute();
  } 

  /** Gets every Account that is associated with any Vendor from the List. */
  public HashMap<String, ArrayList<Account>> getAllAccounts(ArrayList<Vendor> vendors) {
    HashMap<String, ArrayList<Account>> allAccounts =  new HashMap<String, ArrayList<Account>>();

    for(int i = 0; i < vendors.size(); i++) {
      allAccounts.put(vendors.get(i).getVendorID(), vendors.get(i).getAccounts());
    }

    return allAccounts; 
  }

  public List<List<Object>> buildAccountSheetBody(HashMap<String, ArrayList<Account>> accounts) {
    // TODO: @cfloeder Sort the accounts by some criteria x. 
    List<List<Object>> accountSheetData = new ArrayList<List<Object>>();

    for(Map.Entry<String, ArrayList<Account>> entry : accounts.entrySet()) { 
      ArrayList<Account> accountList = entry.getValue();
      for(int i = 0; i < accountList.size(); i++) {
        accountSheetData.add(new ArrayList<Object>(accountList.get(i).getAccountSheetsRow(
        entry.getKey())));
      }
    }

    return accountSheetData;
  }

  public List<List<Object>> buildVendorSheetBody(ArrayList<Vendor> vendors) {
    // TODO: @cfloeder Sort the vendors by some criteria x. 
    List<List<Object>> vendorSheetData = new ArrayList<List<Object>>();

    for(int i = 0; i < vendors.size(); i++) {
      vendorSheetData.add(new ArrayList<Object>(vendors.get(i).
        getVendorSheetsRow()));
    }

    return vendorSheetData;
  }
}
