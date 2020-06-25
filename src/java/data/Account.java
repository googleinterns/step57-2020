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

/**
 * A class representing a billing account object.
 */
public class Account {
    private final String accountID;
    private final String vendorID;
    private final String entity;
    private final String profileID;
    private final String legacyAccountID;
    private final int nextGenAccountID;
    private final String matchingMode;
    private final String agregationMode;

    public Account(String accountID, String vendorID, String entity, String profileID, 
        String legacyAccountID, int nextGenAccountID, String matchingMode, String agregationMode) {
        this.accountID = accountID;
        this.vendorID = vendorID;
        this.entity = entity;
        this.profileID = profileID;
        this.legacyAccountID = legacyAccountID;
        this.nextGenAccountID = nextGenAccountID;
        this.matchingMode = matchingMode;
        this.agregationMode = agregationMode;
    }

    // Create getter and setter methods for each field.
    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getVendorID() {
        return vendorID;  
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID
    }

    public String getLegacyAccountID() {
        return legacyAccountID;
    }

    public void setLegacyAccountID(String legacyAccountID) {
        this.legacyAccountID = legacyAccountID;
    }

    public int getNextGenAccountID() {
        return nextGenAccountID;
    }

    public void setNextGenAccountID(int nextGenAccountID) {
        this.nextGenAccountID = nextGenAccountID;
    }

    public String getMatchingMode() {
        return matchingMode;
    }

    public void setMatchingMode(String matchingMode) {
        this.matchingMode = matchingMode;
    }

    public String getAgregationMode() {
        return agregationMode;
    }

    public void setAgregationMode(String agregationMode) {
        this.agregationMode = agregationMode;
    }
}