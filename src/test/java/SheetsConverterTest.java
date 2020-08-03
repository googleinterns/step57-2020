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

import java.util.*;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.security.GeneralSecurityException;
import java.io.IOException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import data.SheetsConverter;
import data.SheetsServiceUtil;
import data.Vendor;
import data.Account;

/** Test the functionality of the SheetsConverter class. */
@RunWith(JUnit4.class)
public final class SheetsConverterTest {
  private static final String SPREADSHEET_ID = "1QnVlh-pZHycxzgQuk0MN2nWOY6AGu9j4wGZaGzi_W9A";
  private static final String TEST_PATH_BASE = "src/test/resources/";
  private Vendor vendor;
  private Account account;
  private SheetsConverter converter;
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
    converter = new SheetsConverter();
    vendor = new Vendor(VENDOR_ID, LEGACY_VENDOR_ID, NEXT_GEN_VENDOR_ID);
    account = new Account(ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION,
            LEGACY_ACCOUNT_ID, NEXT_GEN_ACCOUNT_ID, MATCHING_MODE, AGGREGATION_MODE);
  }
 
  /** Check that the getAllAccounts method returns all vendors' accounts. */
  @Test
  public void testGetAllAccounts() throws GeneralSecurityException, IOException{
    vendor.addAccount(account);
    ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    vendors.add(vendor);
 
    HashMap<String, ArrayList<Account>> expectedResponse = new 
       HashMap<String, ArrayList<Account>>();
    expectedResponse.put(VENDOR_ID, vendor.getAccounts());
 
    HashMap<String, ArrayList<Account>> actualResponse = converter.
      getAllAccounts(vendors);
    
    assertEquals(expectedResponse, actualResponse);
  }
 
  /** Check that buildAccountSheetBody returns a List with expected data. */
  @Test
  public void testBuildAccountSheetBody() throws GeneralSecurityException, IOException{
    vendor.addAccount(account);
    ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    vendors.add(vendor);
 
    Object[] expectedArray = {
      ACCOUNT_ID, VENDOR_ID, ENTITY, CURRENCY, DIRECTION, LEGACY_ACCOUNT_ID,
      Integer.toString(NEXT_GEN_ACCOUNT_ID), MATCHING_MODE, AGGREGATION_MODE
    };
    List<List<Object>> expectedResponse = new ArrayList<List<Object>>();
    expectedResponse.add(new ArrayList<Object>(Account.getAccountSheetHeader()));
    expectedResponse.add(new ArrayList<Object>(Arrays.asList(expectedArray)));
 
    List<List<Object>> actualResponse = converter.buildAccountSheetBody(
      converter.getAllAccounts(vendors));
      
    assertEquals(expectedResponse, actualResponse);
  }
  
  /** Check that buildVendorSheetBody returns a List with expected data. */
  @Test
  public void testBuildVendorSheetBody() throws GeneralSecurityException, IOException{
    ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    vendors.add(vendor);
 
    Object[] expectedArray = {
      VENDOR_ID, LEGACY_VENDOR_ID, Integer.toString(NEXT_GEN_VENDOR_ID)
    };
    List<List<Object>> expectedResponse = new ArrayList<List<Object>>();
    expectedResponse.add(new ArrayList<Object>(Vendor.getVendorSheetHeader()));
    expectedResponse.add(new ArrayList<Object>(Arrays.asList(expectedArray)));
 
    List<List<Object>> actualResponse = converter.buildVendorSheetBody(vendors);
      
    assertEquals(expectedResponse, actualResponse);
  }

  /** Test that the sortKeys method sorts a Map's keys. */
  @Test
  public void testSortKeys() throws GeneralSecurityException, IOException{
    vendor.addAccount(account);
    Vendor vendor2 = new Vendor("zfte", "legID", 14);
    Vendor vendor3 = new Vendor("adg", "legID", 14);

    ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    vendors.add(vendor2);
    vendors.add(vendor);
    vendors.add(vendor3);   
 
    ArrayList<String> actualKeys = converter.sortKeys(converter.
      getAllAccounts(vendors));

    ArrayList<String> expectedKeys = new ArrayList<String>();
    expectedKeys.add("adg");
    expectedKeys.add(VENDOR_ID);
    expectedKeys.add("zfte");

    
    assertEquals(actualKeys, expectedKeys);
  }
}

