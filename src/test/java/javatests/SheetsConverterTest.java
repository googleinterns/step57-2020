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
package javatests;

import java.util.*;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import java.security.GeneralSecurityException;
import java.io.IOException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import data.SheetsConverter;
import data.SheetsServiceUtil;

@RunWith(JUnit4.class)
public final class SheetsConverterTest {
 private static final String SPREADSHEET_ID = "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";

  @Test
  public void testWriteToSheetMethod() throws GeneralSecurityException, IOException {
    // Sheets sheetsService = sheetsService = SheetsServiceUtil.getSheetsService();

    // ValueRange body = new ValueRange().setValues(Arrays.asList(
    //   Arrays.asList("Expenses January"), 
    //   Arrays.asList("books", "30"), 
    //   Arrays.asList("pens", "10"),
    //   Arrays.asList("Expenses February"), 
    //   Arrays.asList("clothes", "20"),
    //   Arrays.asList("shoes", "5")));
    // UpdateValuesResponse result = sheetsService.spreadsheets().values()
    //   .update(SPREADSHEET_ID, "A1", body)
    //   .setValueInputOption("RAW")
    //   .execute();
  }
}