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

import org.json.JSONObject;
import servlets.BillingConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import util.JsonKeys;
import util.FormIdNames;

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
                 int nextGenAccountID, String matchingMode,
                 String aggregationMode) {

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

  public Account(HttpServletRequest request) throws NumberFormatException {
    this.vendorID = request.getParameter(FormIdNames.VENDOR_ID);
    this.accountID = request.getParameter(FormIdNames.ACCOUNT_ID);
    this.legacyAccountID = request.getParameter(FormIdNames.LEGACY_ACCOUNT_ID);
    this.nextGenAccountID = Integer.parseInt(
            request.getParameter(FormIdNames.NEXT_GEN_ACCOUNT_ID));
    this.currency = request.getParameter(FormIdNames.CURRENCY_CODE);
    this.direction = request.getParameter(FormIdNames.DIRECTION);
    this.entity = request.getParameter(FormIdNames.ENTITY);
    this.matchingMode = request.getParameter(FormIdNames.MATCHING_MODE);
    this.aggregationMode = request.getParameter(FormIdNames.AGGREGATION_MODE);
  }

  /** Construct an Account object from JSON representation. */
  public Account(JSONObject account) {
    this.legacyAccountID = account.getString(JsonKeys.LEGACY_ACCOUNT_ID);
    this.nextGenAccountID = account.getInt(JsonKeys.NEXT_GEN_ACCOUNT_ID);

    JSONObject settlementAttributesObj = account.
            getJSONObject(JsonKeys.SETTLEMENT_ATTRIBUTES_OBJ);
    this.currency = settlementAttributesObj.getString(JsonKeys.CURRENCY_CODE);
    this.direction = settlementAttributesObj.getString(JsonKeys.DIRECTION);
    this.entity = settlementAttributesObj.getString(JsonKeys.ENTITY);

    JSONObject settlementConfigObj = account.
            getJSONObject(JsonKeys.SETTLEMENT_CONFIG_OBJ);
    this.matchingMode = settlementConfigObj.getString(JsonKeys.MATCHING_MODE);

    this.accountID = account.getString(JsonKeys.ACCOUNT_ID);
    this.aggregationMode = account.getString(JsonKeys.AGGREGATION_MODE);
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

  public static List<String> getAccountSheetHeader() {
    String[] accountData = {
      FormIdNames.ACCOUNT_ID,
      FormIdNames.VENDOR_ID,
      FormIdNames.ENTITY,
      FormIdNames.CURRENCY_CODE,
      FormIdNames.DIRECTION,
      FormIdNames.LEGACY_ACCOUNT_ID,
      FormIdNames.NEXT_GEN_ACCOUNT_ID,
      FormIdNames.MATCHING_MODE,
      FormIdNames.AGGREGATION_MODE
    };

    return Arrays.asList(accountData);
  }

  /** Builds one row filled with an Account's data for the Sheet. */
  public List<String> getAccountSheetsRow(String vendorID) {
    String[] accountData = {
      getAccountID(),
      vendorID,
      getEntity(),
      getCurrency(),
      getDirection(),
      getLegacyAccountID(),
      Integer.toString(getNextGenAccountID()),
      getMatchingMode(),
      getAggregationMode()
    };

    return Arrays.asList(accountData);
  }

  /** Return a JSON String representation of the fields. */
  public String buildJsonConfig() {
    return String.format("{\"legacy_account_id\":\"%s\"," +
      "\"next_gen_account_id\":%d,\"settlement_attributes\":{" +
      "\"currency_code\":\"%s\",\"direction\":\"%s\",\"entity\":\"%s\"}," +
      "\"settlement_config\":{\"matching_mode\":\"%s\"},\"account_id\":\"%s\"," +
      "\"aggregation_mode\":\"%s\"}", getLegacyAccountID(), getNextGenAccountID(),
      getCurrency(), getDirection(), getEntity(), getMatchingMode(),
      getAccountID(), getAggregationMode());
  }

  /**
   * Update existing account information.
   * Changes to legacyCustomerId, nextGenCustomerId, vendorId, and accountId
   *      are disabled in the UI and not propagated to the backend.
   *      TODO: Prevent changes from being made in the backend
   * @param request contains form data to edit existing account
   */
  public void updateExistingAccount(HttpServletRequest request) {
    this.currency = request.getParameter(FormIdNames.CURRENCY_CODE);
    this.direction = request.getParameter(FormIdNames.DIRECTION);
    this.entity = request.getParameter(FormIdNames.ENTITY);
    this.matchingMode = request.getParameter(FormIdNames.MATCHING_MODE);
    this.aggregationMode = request.getParameter(FormIdNames.AGGREGATION_MODE);
  }
}