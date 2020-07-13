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
import org.json.JSONObject;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import data.Account;
import data.Vendor;

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
    assertTrue("Account Constructor incorrectly set nextGenAccountID field.", 
      account.getNextGenAccountID() == NEXT_GEN_ACCOUNT_ID);  
    assertEquals(account.getMatchingMode(), MATCHING_MODE);  
    assertEquals(account.getAggregationMode(), AGGREGATION_MODE);        
  }

  /** Test that toJson returns a String representation of an Account object. */
  @Test
  public void testToJsonMethod() {
    String expectedResponse = String.format("{\"legacy_account_id\":%s," +
      "\"next_gen_customer_id\":%d,\"settlement_attributes\":{" +
      "\"currency_code\":%s,\"direction\":%s,\"entity\":%s}," +
      "\"settlement_config\":{\"matching_mode\":%s},\"account_id\":%s," +
      "\"aggregation_mode\":%s}}", LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID,
      CURRENCY, DIRECTION, ENTITY, MATCHING_MODE, ACCOUNT_ID,AGGREGATION_MODE); 

    String actualResponse = account.toJson();

    assertEquals(expectedResponse, actualResponse);
  }
}