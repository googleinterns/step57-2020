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

import data.Account;
import org.mockito.Mockito;
import util.FormIdNames;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public final class AccountTest {
  private Account account;
  private final String ACCOUNT_ID = "acc_12";
  private final String VENDOR_ID = "vend-21";
  private final String ENTITY = "shopper";
  private final String CURRENCY = "USD";
  private final String DIRECTION = "disbursement";
  private final String LEGACY_ACCOUNT_ID = "legAcc_53";
  private final int NEXT_GEN_ACCOUNT_ID = 17;
  private final String MATCHING_MODE = "straight";
  private final String AGGREGATION_MODE = "totalAgg";

  private final String NEW_ACCOUNT_ID = "new_acc_12";
  private final String NEW_VENDOR_ID = "new_vend-21";
  private final String NEW_ENTITY = "new_shopper";
  private final String NEW_CURRENCY = "new_USD";
  private final String NEW_DIRECTION = "new_disbursement";
  private final String NEW_LEGACY_ACCOUNT_ID = "new_legAcc_53";
  private final int NEW_NEXT_GEN_ACCOUNT_ID = 100;
  private final String NEW_MATCHING_MODE = "new_straight";
  private final String NEW_AGGREGATION_MODE = "new_totalAgg";

  /** Create an Account object. */
  @Before
  public void setUp() {
    account = new Account(ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION,
            LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, MATCHING_MODE, AGGREGATION_MODE);
  }

  /** Test that the constructor set the Vendor fields with the correct data. */
  @Test
  public void testAccountConstructorWithValidInput() {
    assertEquals(account.getAccountID(), ACCOUNT_ID);
    assertEquals(account.getVendorID(), VENDOR_ID);
    assertEquals(account.getEntity(), ENTITY);
    assertEquals(account.getCurrency(), CURRENCY);
    assertEquals(account.getDirection(), DIRECTION);
    assertEquals(account.getLegacyAccountID(), LEGACY_ACCOUNT_ID);
    assertEquals("Account Constructor incorrectly set nextGenAccountID field.", account.getNextGenAccountID(), NEXT_GEN_ACCOUNT_ID);
    assertEquals(account.getMatchingMode(), MATCHING_MODE);
    assertEquals(account.getAggregationMode(), AGGREGATION_MODE);
  }

  /** Test that toJson returns a String representation of an Account object. */
  @Test
  public void testToJsonMethod() {
    String expectedResponse = String.format("{\"legacy_account_id\":\"%s\"," +
                    "\"next_gen_account_id\":%d,\"settlement_attributes\":{" +
                    "\"currency_code\":\"%s\",\"direction\":\"%s\",\"entity\":\"%s\"}," +
                    "\"settlement_config\":{\"matching_mode\":\"%s\"},\"account_id\":\"%s\"," +
                    "\"aggregation_mode\":\"%s\"}", LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID,
            CURRENCY, DIRECTION, ENTITY, MATCHING_MODE, ACCOUNT_ID, AGGREGATION_MODE);
    String actualResponse = account.buildJsonConfig();

    // Construct JSON objects to ensure both strings are valid.
    JSONObject expected = new JSONObject(expectedResponse);
    JSONObject actual = new JSONObject(actualResponse);

    assertTrue(expected.similar(actual));
    assertEquals("toJson() doesn't match expected output.", expectedResponse, actualResponse);
  }
  
  /** Test JSON constructor sets every field accurately */
  @Test
  public void testConstructorFromJson() {
    String expectedResponse = String.format("{\"legacy_account_id\":\"%s\",\"" +
            "next_gen_account_id\":%d,\"settlement_attributes\":{\"currency_code\"" +
            ":\"%s\",\"direction\":\"%s\",\"entity\":\"%s\"},\"settlement_config\"" +
            ":{\"matching_mode\":\"%s\"},\"account_id\":\"%s\",\"aggregation_mode\"" +
            ":\"%s\"}",
            LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, CURRENCY, DIRECTION, ENTITY, MATCHING_MODE,
            ACCOUNT_ID, AGGREGATION_MODE);

    Account account = new Account(new JSONObject(expectedResponse));
    String actualResponse = account.buildJsonConfig();

    // Construct JSON objects to ensure both strings are valid.
    JSONObject expected = new JSONObject(expectedResponse);
    JSONObject actual = new JSONObject(actualResponse);

    assertTrue(expected.similar(actual));
    assertEquals("Failed to construct Account object from JSON", expectedResponse, actualResponse);
  }

  /** Test that getAccountSheetHeader returns the correct Account headings. */
  @Test
  public void testGetAccountSheetHeader() {
    String[] expectedArray = {
      FormIdNames.ACCOUNT_ID, 
      FormIdNames.VENDOR_ID, 
      FormIdNames.ENTITY, 
      FormIdNames.CURRENCY_CODE, 
      FormIdNames.DIRECTION,
      FormIdNames.LEGACY_ACCOUNT_ID,
      FormIdNames.NEXT_GEN_ACCOUNT_ID,
      FormIdNames.MATCHING_MODE,
      FormIdNames.AGGREGATION_MODE
    };
    List<String> expectedResponse = Arrays.asList(expectedArray);

    List<String> actualResponse = Account.getAccountSheetHeader();

    assertEquals(expectedResponse, actualResponse);
  }

  /** Test that getAccountSheetsRow returns a List representing its data. */
  @Test
  public void testGetAccountSheetsRowMethod() {
    String[] expectedArray = {
      ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION, LEGACY_ACCOUNT_ID,
      "" + NEXT_GEN_ACCOUNT_ID, MATCHING_MODE, AGGREGATION_MODE
    };
    List<String> expectedResponse = Arrays.asList(expectedArray);

    List<String> actualResponse = account.getAccountSheetsRow(VENDOR_ID);

    assertEquals(expectedResponse, actualResponse);
  }

  /** Confirm that edits made to an account are applied correctly. */
  @Test
  public void testUpdateExistingAccount() {
    // This request is mocked to return the data that would ordinarily be in
    // the form that is sent to update an existing account.
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    Mockito.when(request.getParameter(FormIdNames.VENDOR_ID))
            .thenReturn(NEW_VENDOR_ID);
    Mockito.when(request.getParameter(FormIdNames.ACCOUNT_ID))
            .thenReturn(NEW_ACCOUNT_ID);
    Mockito.when(request.getParameter(FormIdNames.LEGACY_ACCOUNT_ID))
            .thenReturn(NEW_LEGACY_ACCOUNT_ID);
    Mockito.when(request.getParameter(FormIdNames.NEXT_GEN_ACCOUNT_ID))
            .thenReturn(String.valueOf(NEW_NEXT_GEN_ACCOUNT_ID));
    Mockito.when(request.getParameter(FormIdNames.CURRENCY_CODE))
            .thenReturn(NEW_CURRENCY);
    Mockito.when(request.getParameter(FormIdNames.DIRECTION))
            .thenReturn(NEW_DIRECTION);
    Mockito.when(request.getParameter(FormIdNames.ENTITY))
            .thenReturn(NEW_ENTITY);
    Mockito.when(request.getParameter(FormIdNames.MATCHING_MODE))
            .thenReturn(NEW_MATCHING_MODE);
    Mockito.when(request.getParameter(FormIdNames.AGGREGATION_MODE))
            .thenReturn(NEW_AGGREGATION_MODE);

    account.updateExistingAccount(request);

    // The update method should not edit accountId, vendorId,
    // legacyAccountId, or nextGenAccountId
    Account expectedAccount = new Account(ACCOUNT_ID, VENDOR_ID,
            NEW_ENTITY, NEW_CURRENCY, NEW_DIRECTION, LEGACY_ACCOUNT_ID,
            NEXT_GEN_ACCOUNT_ID, NEW_MATCHING_MODE, NEW_AGGREGATION_MODE);

    assertEquals("Failed to update account data", account.buildJsonConfig(),
            expectedAccount.buildJsonConfig());
  }
}