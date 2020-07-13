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

import servlets.BillingConfig;

import javax.servlet.http.HttpServletRequest;
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

  public Account(HttpServletRequest request, String accountID, String vendorID) {
    this.vendorID = vendorID;
    this.accountID = request.getParameter(BillingConfig.ACCOUNT_ID);
    this.legacyAccountID = request.getParameter(BillingConfig.LEGACY_ACCOUNT_ID);
    this.nextGenAccountID = Integer.parseInt(
            request.getParameter(BillingConfig.NEXT_GEN_ACCOUNT_ID));
    this.currency = request.getParameter(BillingConfig.CURRENCY_CODE);
    this.direction = request.getParameter(BillingConfig.DIRECTION);
    this.entity = request.getParameter(BillingConfig.ENTITY);
    this.matchingMode = request.getParameter(BillingConfig.MATCHING_MODE);
    this.aggregationMode= request.getParameter(BillingConfig.AGGREGATION_MODE);
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

  /** Return a JSON String representation of the fields. */
  public String toJson() {
    return String.format("{\"legacy_account_id\":%s," +
      "\"next_gen_customer_id\":%d,\"settlement_attributes\":{" +
      "\"currency_code\":%s,\"direction\":%s,\"entity\":%s}," +
      "\"settlement_config\":{\"matching_mode\":%s},\"account_id\":%s," +
      "\"aggregation_mode\":%s}}", getLegacyAccountID(), getNextGenAccountID(), 
      getCurrency(), getDirection(), getEntity(), getMatchingMode(), 
      getAccountID(), getAggregationMode());

  }
}