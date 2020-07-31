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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import data.JsonConverter;
import data.Vendor;
import data.Account;

/** Test the functionality of the JsonConverter class. */
@RunWith(JUnit4.class)
public final class JsonConverterTest {
  private static final String TEST_PATH_BASE = "src/test/resources/";

  private Vendor vendor;
  private Account account;
  private JsonConverter converter;
  private final String VENDOR_ID = "vend_0";
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
  private final String FAKE_VENDOR_ID = "fake_id";


  /** Create a Vendor and Account object. */
  @Before
  public void setUp() {
    converter = new JsonConverter(TEST_PATH_BASE);
    vendor = new Vendor(VENDOR_ID, LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID);
    account = new Account(ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION,
            LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, MATCHING_MODE, AGGREGATION_MODE);
    File file = converter.writeFile(VENDOR_ID, vendor.buildJsonConfig());
  }

  /** Test that updateFile() returns true. */
  @Test
  public void testUpdateFileMethod() throws IOException {
    boolean expectedResponse = true;
    boolean actualResponse = converter.updateFile(vendor);

    assertEquals("updateFile() refused to update existing file.",
            expectedResponse, actualResponse);
  }

  @Test
  public void testUpdateNonexistentFile() {
    boolean expected = false;
    boolean actual = converter.updateFile(new Vendor(FAKE_VENDOR_ID,
            LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID));
    assertEquals("updateFile() updated file that does not exist",
            expected, actual);
  }

  /**
   * Test that getConfig() returns the expected file content.
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test
  public void testGetConfigMethod() throws FileNotFoundException {
    String expectedResponse = "{\"legacy_customer_id\":\"legVend_27\"," +
            "\"next_gen_customer_id\":17,\"accounts\":[]}";
    String actualResponse = converter.getConfigText(VENDOR_ID);

    assertEquals(removeWhitespace(expectedResponse),
            removeWhitespace(actualResponse));
  }

  /**
   * Test that getConfig() throws exception when faulty vendorID is passed in.
   * The vendorID must match an existing filepath in the filesystem.
   */
  @Test(expected = FileNotFoundException.class)
  public void testGetConfigMethodWithFakeVendorID() throws FileNotFoundException {
    String vendorID = "fakeVendorID";
    String expectedResponse = null;
    String actualResponse = converter.getConfigText(vendorID);
  }

  /** Test that writeFile() returns a File with the expected content. */
  @Test
  public void testWriteFile() throws FileNotFoundException {
    vendor.addAccount(account);

    String expectedResponse = String.format("{\"legacy_customer_id\":\"%s\"," +
                    "\"next_gen_customer_id\":%d,\"accounts\":[{\"legacy_account_id\":\"%s\"," +
                    "\"next_gen_account_id\":%d,\"settlement_attributes\":{" +
                    "\"currency_code\":\"%s\",\"direction\":\"%s\",\"entity\":\"%s\"}," +
                    "\"settlement_config\":{\"matching_mode\":\"%s\"},\"account_id\":\"%s\"," +
                    "\"aggregation_mode\":\"%s\"}]}", LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID,
            LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, CURRENCY, DIRECTION, ENTITY,
            MATCHING_MODE, ACCOUNT_ID, AGGREGATION_MODE);
    String actualResponse = "";

    File file = converter.writeFile(vendor.getVendorID(), vendor.buildJsonConfig());
    Scanner input = null;
    input = new Scanner(file);

    while(input.hasNextLine()) {
      actualResponse += input.nextLine();
    }

    assertEquals(expectedResponse, actualResponse);
  }

  /**
   * This test depends on the test resources folder having 4 files (vend_0 - vend_3).
   * vend_1 through vend_3 should be as written in the resources directory, and vend_0 is created
   * by an earlier method.
   */
  @Test
  public void testGetConfigMap() throws IOException {
    String expectedResponse = "{\"vend_2\":[\"account1\",\"account2\"],\"vend_3\":[\"account1\"," +
            "\"account2\",\"account3\"],\"vend_0\":[],\"vend_1\":[\"account1\"]}";
    String actualResponse = converter.getConfigMap();
    assertEquals(expectedResponse, actualResponse);
  }

  private String removeWhitespace(String in) {
    // Uses regex '\\s+' to remove all internal whitespace
    return in.replaceAll("\\s+", "").trim();
  }

  /** Test that the desired file gets deleted.*/
  @Test
  public void testDeleteFile() {
    String vendorID = vendor.getVendorID();
    File file = converter.writeFile(VENDOR_ID, vendor.buildJsonConfig());

    // Test that the file exists.
    ArrayList<String> vendorIDs = converter.getVendorIDs();
    assertTrue("The file didn't exist when it should have.", 
      vendorIDs.contains(VENDOR_ID));
      
    converter.deleteFile(VENDOR_ID);

    // Test that the file is gone.
    vendorIDs = converter.getVendorIDs();
    assertFalse("The file existed when it shouldn't have.", 
      vendorIDs.contains(VENDOR_ID));
  }

  /** Test that the correct Vendor IDs are returned. */
  @Test
  public void testGetVendorIDs() {
    ArrayList<String> expectedVendorIDs = new ArrayList<String>();
    expectedVendorIDs.add("vend_0");
    expectedVendorIDs.add("vend_1");
    expectedVendorIDs.add("vend_2");
    expectedVendorIDs.add("vend_3");

    ArrayList<String> actualVendorIDs = converter.getVendorIDs();
    Collections.sort(actualVendorIDs);

    assertTrue(expectedVendorIDs.equals(actualVendorIDs));
  }

  @Test
  public void testAccountExists() {
    vendor.addAccount(account);
    boolean actualResponse = converter.accountExists(vendor, ACCOUNT_ID);
    boolean expectedResponse = true;
    assertEquals(actualResponse, expectedResponse);
  }
}