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
package data;

import java.util.*;
import org.json.JSONObject;

/** A class representing a billing account object. */
public class Account {
  private String accountID;
  private String vendorID;
  private String entity;
  private String currency;
  private String direction;
  private String legacyAccountID;
  private int nextGenAccountID;
  private String matchingMode;
  private String aggregationMode;

  public Account(String accountID, String vendorID, String entity, 
    String currency, String direction, String legacyAccountID, 
    int nextGenAccountID, String matchingMode, String aggregationMode) {

    this.accountID = accountID;
    this.vendorID = vendorID;
    this.entity = entity;
    this.currency = currency;
    this.direction = direction;
    this.legacyAccountID = legacyAccountID;
    this.nextGenAccountID = nextGenAccountID;
    this.matchingMode = matchingMode;
    this.aggregationMode = aggregationMode;
  }

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

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
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

  public String getAggregationMode() {
    return aggregationMode;
  }

  public void setAggregationMode(String aggregationMode) {
    this.aggregationMode = aggregationMode;
  }

  // Create and return a JSON object that contains Account fields.
  public LinkedHashMap toJson() {
    // Use LinkedHashMap to preserve order.
    LinkedHashMap accountMap = new LinkedHashMap();
    LinkedHashMap settlementAttributesMap = new LinkedHashMap();

    // Create a JSON objects to contain Account fields.
    JSONObject settlementConfig = new JSONObject();

    // Populate settlementConfig JSON object. LEARN HOW TO PUT QUOTES AROUND A VARIABLE
    settlementConfig.put("Matching_Mode", getMatchingMode());

    // Populate settlementAttributes JSON object.
    settlementAttributesMap.put("Currency_Code", getCurrency());
    settlementAttributesMap.put("Direction", getDirection());
    settlementAttributesMap.put("Entity", getEntity());

    // Populate JSON account object.
    accountMap.put("Legacy_Account_ID", getLegacyAccountID());
    accountMap.put("Next_Gen_Customer_ID", getNextGenAccountID());
    accountMap.put("Settlement_Attributes", settlementAttributesMap);
    accountMap.put("Settlement_Config", settlementConfig);
    accountMap.put("Account_ID", getAccountID());
    accountMap.put("Aggregation_Mode", getAggregationMode());

    return accountMap;
  }
}