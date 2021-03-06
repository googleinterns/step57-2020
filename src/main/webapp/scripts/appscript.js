// Copyright 2020 Google LLC
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

/**
  * Fetch vendor IDs from VendorServlet and add them to the
  * Customer ID dropdown.
  */
async function populateCustomerList() {
  // Fetches json dictionary from VendorServlet.
  var response = await fetch('/VendorServlet');
  var vendorJson = await response.json();
  // Loops over this array to make the options for the Customer ID dropdown.
  var vendorHtml = '';
  for (var key in vendorJson) {
    vendorHtml += '<option value="' + key + '">'
    + key + '</option>';
  }
  document.getElementById('customer-ids').innerHTML = vendorHtml;
  // Populate account list in text box following loading the customers.
  populateAccountList();
}

/**
 * Fetch account IDs from VendorServlet and add them to the Account ID
 * dropdown.
 */
async function populateAccountList() {
  // Fetches json dictionary from VendorServlet.
  var response = await fetch('/VendorServlet');

  var vendorJson = await response.json();

  // Takes the value of the Vendor ID <select> element.
  var vendorValue = document.getElementById('customer-ids').value;

  // Loops over the dictionary to find the corresponding value in the dictionary
  // and adds them to the Account ID <select> element.
  var accountHtml = '';
  var array = vendorJson[vendorValue];
  for (i = 0; i < array.length; i++) {
    accountHtml += '<option value="' + array[i] + '">'
    + array[i] + '</option>';
  }

  // Adds the options to the page, allowing them to be viewed.
  document.getElementById('account-ids').innerHTML = accountHtml;
}

/**
 * Fetch a json configuration as a string and formats it to be shown on
 * the readfile page.
 */
async function addConfigToPage() {
  const queryString = buildQueryString();
  // Fetch the json configuration and format it to print on the page. 
  fetch(queryString)
    .then(response => response.json())
    .then(data => document.getElementById('json-text').innerText = 
    JSON.stringify(data, undefined, 4));
}

/**
 * Builds the edit form's action attribute and populates text from existing configuration
 */
function buildEditForm() {
  const editForm = document.getElementById('edit-config-form');
  editForm.action = buildQueryString();
  populateEditForm();
  showForm();
}

/**
 * Builds the form to add a new account by setting the action attribute and copying the accountID.
 */
function buildAddForm() {
  const form = document.getElementById('add-account-form');
  form.action = buildQueryString();
  document.getElementById('account-id').value = document.getElementById('account-ids').value;
  populateEditForm();
  showForm();
}

/**
 * Builds a query string specific to the edit form to fetch an entire configuration
 * @return {string}   contains "vendorID" and "entireConfig", where vendorID is the associated ID and
 *                    entireConfig is a boolean that should be true, returning the entire config text
 */
function buildQueryStringEditForm() {
  const selectedVendorId = document.getElementById('customer-ids').value;
  return `/BillingConfig?vendorID=${selectedVendorId}&entireConfig=true`;
}

/**
 * Builds a generic query string containing vendor and account IDs
 * entireConfig=false denotes the response should only contain the specified account
 */
function buildQueryString() {
  const selectedVendorId = document.getElementById('customer-ids').value;
  const selectedAccountId = document.getElementById('account-ids').value;
  // check for space in accountID.
  if (selectedAccountId == "") {
    window.alert("A vendor ID and account ID have not been set yet!");
  } else {
    return `/BillingConfig?vendorID=${selectedVendorId}&accountID=${selectedAccountId}&entireConfig=false`;
  }
}

function showForm() {
  const selectedAccountId = document.getElementById('account-ids').value;
  if(selectedAccountId == "") {
    // Do nothing.
  } else if(document.getElementById('add-account') != null) {
    document.getElementById('add-account').style.display = 'block';
  } else if(document.getElementById('edit-form') != null) {
    document.getElementById('edit-form').style.display = 'block';
  }
}

/**
 * Validates the fields in the edit form input and returns true iff all fields have a valid format.
 */
function validateEditFormInput() {
  const form = document.getElementById('edit-config-form');
  if (!form.hasAttribute('action')) {
    // Prevent submission without first selecting vendor and account.
    window.alert("A vendor ID and account ID have not been set!");
    return false;
  } else if (isNaN(document.getElementById('next-gen-customer-id').value)) {
    // Require integer for next-gen-customer-id.
    window.alert("Next Gen Customer ID must be an integer");
    return false;
  } else if (isNaN(document.getElementById('next-gen-account-id').value)) {
    // Require integer for next-gen-account-id.
    window.alert("Next Gen Account ID must be an integer");
    return false;
  } else {
    // No problems found with form, so continue to redirect.
    // TODO: A dialogue box could appear here to confirm changes
    return true;
  }
}

async function populateEditForm() {
  const queryString = buildQueryStringEditForm();
  const selectedAccountId = document.getElementById('account-ids').value;

  fetch(queryString).then(response => {
    return response.json();
  }).then(data => {
    document.getElementById('legacy-customer-id').value = data.legacy_customer_id;
    document.getElementById('next-gen-customer-id').value = data.next_gen_customer_id;
    return data.accounts.find(acc => acc.account_id === selectedAccountId);
  }).then(account => {
    if (account === undefined) {
      // When a user chooses to add a new account, the function shouldn't populate fields that don't exist.
      return;
    }
    document.getElementById('legacy-account-id').value = account.legacy_account_id;
    document.getElementById('next-gen-account-id').value = account.next_gen_account_id;
    document.getElementById('currency-code').value = account.settlement_attributes.currency_code;
    document.getElementById('direction').value = account.settlement_attributes.direction;
    document.getElementById('entity').value = account.settlement_attributes.entity;
    document.getElementById('matching-mode').value = account.settlement_config.matching_mode;
    document.getElementById('account-id').value = account.account_id;
    document.getElementById('aggregation-mode').value = account.aggregation_mode;
  });
}

// TODO: Method for enums: Check which of the tags has data under it {isLoggedIn:{loginURL},isLoggedOut:{logooutURL}}}
async function checkLoginStatus() {
  
}