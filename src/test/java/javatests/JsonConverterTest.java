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
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import data.JsonConverter;
import data.Vendor;
import data.Account;

/** Test the functionality of the JsonConverter class. */
@RunWith(JUnit4.class)
public final class JsonConverterTest {
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

  /** Test that updateFile() returns true. */
  @Test
  public void testUpdateFileMethod() throws IOException {
    JsonConverter converter = new JsonConverter();

    boolean expectedResponse = true;
    boolean actualResponse = converter.updateFile(vendor);

    assertTrue("updateFile() didn't return true when it should have.", 
      expectedResponse == actualResponse);
  }

  /** 
   * Test that getConfig() returns the expected file content. 
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test
  public void testGetConfigMethod() {
    JsonConverter converter = new JsonConverter();
    String vendorID = "vend_1";
    String expectedResponse = "{\"legacy_vendor_id\":legVend_27,\"next_gen_vendor_id\":17,\"accounts\":[]}";

    String actualResponse = converter.getConfig(vendorID);

    assertTrue("getConfig() didn't return the expected file content.",
      expectedResponse.equals(actualResponse));
  }

  /** 
   * Test that getConfig() returns null when faulty vendorID is passed in. 
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test 
  public void testGetConfigMethodWithFakeVendorID() {
    JsonConverter converter = new JsonConverter();
    String vendorID = "fakeVendorID";
    String expectedResponse = null;

    String actualResponse = converter.getConfig(vendorID);

    assertTrue("getConfig() didn't return null when it should have.",
      expectedResponse == actualResponse);
  }

  /** Test that writeFile() returns a File with the expected content. */
  @Test
  public void testWriteFile() {
    JsonConverter converter = new JsonConverter();
    vendor.addAccount(account);

    String expectedResponse = String.format("{\"legacy_vendor_id\":%s," +
      "\"next_gen_vendor_id\":%d,\"accounts\":[{\"legacy_account_id\":%s," +
      "\"next_gen_customer_id\":%d,\"settlement_attributes\":{" +
      "\"currency_code\":%s,\"direction\":%s,\"entity\":%s}," +
      "\"settlement_config\":{\"matching_mode\":%s},\"account_id\":%s," +
      "\"aggregation_mode\":%s}}]}", LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID,
      LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, CURRENCY, DIRECTION, ENTITY, 
      MATCHING_MODE, ACCOUNT_ID,AGGREGATION_MODE);
    String actualResponse = "";

    File file = converter.writeFile(vendor.getVendorID(), vendor.buildJsonConfig());
    Scanner input = null;
    try {
      input = new Scanner(file);
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }

    while(input.hasNextLine()) {
      actualResponse += input.nextLine();
    }

    assertTrue("writeFile() returned a file with incorrect contents.", 
      expectedResponse.equals(actualResponse));
  }
}
