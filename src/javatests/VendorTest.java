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
import org.junitAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class VendorTest {
    @Test
    public void testVendorConstructorWithValidInput() {
        // Create Vendor fields.
        String vendorID = "vend_1";
        String legVendID = "legVend_27";
        int nextGenVendorID = 17;

        Vendor vendor = new Vendor(vendorID, legacyVendorID, nextGenVendorID);

        // Test that the constructor set the Vendor fields with the correct data.66
        assertTrue("Vendor Constructor incorrectly set vendorID field.", 
            vendor.getVendorID().equals(vendorID));
        assertTrue("Vendor Constructor incorrectly set legacyVendorID field.", 
            vendor.getLegacyVendorID().equals(legVendID));
        assertTrue("Vendor Constructor incorrectly set nextGenVendorID field.", 
            vendor.getNextGenVendorID().equals(nextGenVendorID));   
        assertTrue("Vendor Constructor incorrectly initialize accountList.", 
            vendor.getAccounts().equals(nextGenVendorID));             
    }

    public void testGetAccountsMethodWithNoElements() {
        Vendor vendor = new Vendor();

        // Test that the accountList is null/empty.
        assertTrue("The accountList wasn't empty when it should have been.", 
            vendor.getAccounts().size() == 0);
    }

    public void testAddAccountMethod() {
        // Create Account feilds.
        String accountID = "acc_12";
        String vendorID = "vend-21";
        String entity = "shopper";
        String currency = "USD";
        String direction = "disbursement";
        String legacyAccountID = "legAcc_53";
        int nextGenAccountID = 17;
        String matchingMode = "straight";
        String aggregationMode = "totalAgg"; 

        Account account = new Account(accountID, vendorID, entity, currency, 
            direction, legacyAccountID, nextGenAccountID, matchingMode, aggregationMode);
        Vendor vendor = new Vendor();

        vendor.addAccount(account);

        // Test that the account was correctly added to the Vendor.
        assertTrue("The account wasn't added to the Vendor when it was supposed to.",
            vendor.getAccounts().size() == 1);
        assertTrue("A different account then expected was added to the Vendor.",
            vendor.getAccounts().get(0).getAccountID().equals(accountID));
    }
}
