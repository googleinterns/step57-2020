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
import data.Account;
import data.Vendor;

@RunWith(JUnit4.class)
public final class AccountTest {
    @Test
    public void testAccountConstructorWithValidInput() {
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

        // Test that the constructor set the Vendor fields with the correct data.
        assertTrue("Account Constructor incorrectly set accountID field.", 
            account.getAccountID().equals(accountID));
        assertTrue("Account Constructor incorrectly set vendorID field.", 
            account.getVendorID().equals(vendorID));
        assertTrue("Account Constructor incorrectly set entity field.", 
            account.getEntity().equals(entity));
        assertTrue("Account Constructor incorrectly set currency field.", 
            account.getCurrency().equals(currency));   
        assertTrue("Account Constructor incorrectly set direction field.", 
            account.getDirection().equals(direction));       
        assertTrue("Account Constructor incorrectly set legacyAccountID field.", 
            account.getLegacyAccountID().equals(legacyAccountID));  
        assertTrue("Account Constructor incorrectly set nextGenAccountID field.", 
            account.getNextGenAccountID() == nextGenAccountID);  
        assertTrue("Account Constructor incorrectly set matchingMode field.", 
            account.getMatchingMode().equals(matchingMode));  
        assertTrue("Account Constructor incorrectly set aggregationMode field.", 
            account.getAggregationMode().equals(aggregationMode));        
    }
}