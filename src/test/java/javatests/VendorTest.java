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
import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import data.Account;
import data.Vendor;

@RunWith(JUnit4.class)
public final class VendorTest {
  private Vendor vendor;
  private Account account;
  private final String VENDOR_ID = "vend_1";
  private final String LEGACY_VENDOR_ID = "legVend_27";
  private final int NEXT_GEN_VENDOR_ID = 17;
  private final String ACCOUNT_ID = "acc_12";
  private final String ENTITY = "shopper";
  private final String CURRENCY = "USD";
  private final String DIRECTION = "disbursement";
  private final String LEGACY_ACCOUNT_ID = "legAcc_53";
  private final int NEXT_GEN_ACCOUNT_ID = 17;
  private final String MATCHING_MODE = "straight";
  private final String AGGREGATION_MODE = "totalAgg"; 
     
  /** Create a Vendor and Account object. */
  @Before
  public void setUp() {
    vendor = new Vendor(VENDOR_ID, LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID);
    account = new Account(ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION, 
      LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, MATCHING_MODE, AGGREGATION_MODE);
  }

  /** Test that the constructor set the Vendor fields with the correct data. */
  @Test
  public void testVendorConstructorWithValidInput() {
    assertEquals(vendor.getVendorID(), VENDOR_ID);
    assertEquals(vendor.getLegacyVendorID(), LEGACY_VENDOR_ID);
    assertTrue("Vendor Constructor incorrectly set nextGenVendorID field.", 
      vendor.getNextGenVendorID() == NEXT_GEN_VENDOR_ID);   
    assertTrue("Vendor Constructor incorrectly initialize accountList.", 
      vendor.getAccounts().size() == 0);             
    }

  /** Test that the accountList starts null/empty. */
  @Test
  public void testGetAccountsMethodWithNoElements() {
    Vendor vendor = new Vendor();

    assertTrue("The accountList wasn't empty when it should have been.", 
      vendor.getAccounts().size() == 0);
    }

  /** Test that the account was correctly added to the Vendor. */
  @Test
  public void testAddAccountMethod() {
    vendor.addAccount(account);

    assertTrue("The account wasn't added to the Vendor when it should have.",
      vendor.getAccounts().size() == 1);
    assertEquals(vendor.getAccounts().get(0).getAccountID(), ACCOUNT_ID);
  }

  /** Test that buildJsonConfig() returns a billing configs String contents. */
  @Test
  public void testBuildJsonConfig() {
    vendor.addAccount(account);
    String expectedResponse = String.format("{\"legacy_vendor_id\":%s," +
      "\"next_gen_vendor_id\":%d,\"accounts\":[{\"legacy_account_id\":%s," +
      "\"next_gen_customer_id\":%d,\"settlement_attributes\":{" +
      "\"currency_code\":%s,\"direction\":%s,\"entity\":%s}," +
      "\"settlement_config\":{\"matching_mode\":%s},\"account_id\":%s," +
      "\"aggregation_mode\":%s}}]}", LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID,
      LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, CURRENCY, DIRECTION, ENTITY, 
      MATCHING_MODE, ACCOUNT_ID,AGGREGATION_MODE);
    String actualResponse = vendor.buildJsonConfig();

    assertEquals(expectedResponse, actualResponse);
  }
}
