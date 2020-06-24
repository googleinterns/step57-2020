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
package com.google.data;

import java.util.*;

/**
 * A class representing a billing vendor object.
 */
public class Account {
    private final String vendorID;
    private final String legacyVendorID;
    private final int nextGenVendorID;
    private final ArrayList<Account> accountList;

    public Account(String vendorID, String legacyVendorID, int nextGenVendorID) {
        this.vendorID = vendorID;
        this.legacyVendorID = legacyVendorID;
        this.nextGenVendorID = nextGenVendorID;
    }

    public void addAccount(Account account) } {
        accountList.add(account);
    }

    // Create getter and setter methods for each field.
    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getLegacyVendorID() {
        return legacyVendorID;
    }

    public void setLegacyVendorID(String legacyVendorID) {
        this.legacyVendorID = legacyVendorID;
    }

    public int getNextGenVendorID() {
        return nextGenVendorID;
    }

    public void setNextGenVendorID(int nextGenVendorID) {
        this. nextGenVendorID = nextGenVendorID;
    }

    public ArrayList<Account> getAccounts() {
        return accountList;
    }
}