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
  // TODO: Implement functionality for hashmap parsing instead of simple array.
  var response = await fetch('/VendorServlet');

  // Response is a json array that is parsed into json.
  var customerJson = await response.json();
  // Loops over this array to make the options for the Customer ID dropdown.
  var vendorHtml = '';
  for (var i = 0; i < customerJson.length; i++) {
    html += '<option value="' + customerJson[i] + '">'
    + customerJson[i] + '</option>';
  }
  document.getElementById('vendor-id').innerHTML = vendorHtml;
}

/**
 * Fetch a json configuration as a string and formats it to be shown on
 * the readfile page.
 */
async function addConfigToPage() {
  const servletUrl = "/BillingConfig";
  const queryString = "?vendorID=" + document.getElementById('customer-ids').value;
  // + "&accountID=" + document.getElementById('account-id').value;

  // Fetch the json configuration and format it to print on the page. 
  fetch(servletUrl + queryString)
    .then(response => response.json())
    .then(data => document.getElementById('json-text').innerText = 
    JSON.stringify(data, undefined, 4));
}

/**
 * Build the edit form's action attribute and populates text from existing configuration
 */
function buildEditForm() {
  const editForm = document.getElementById('edit-config-form');
  editForm.action = buildQueryString();
}

function buildQueryString() {
  const selectedVendorId = document.getElementById('customer-ids').value;
  const selectedAccountId = document.getElementById('account-ids').value;
  return `/BillingConfig?vendorID=${selectedVendorId}&accountID=${selectedAccountId}`;
}

/**
 * Return the form validity. If all checks pass, this function returns true and allows the post request
 * to occur.
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

// TODO: Method to populate edit form.
async function populateEditForm() {

}
// TODO: Method for enums: Check which of the tags has data under it {isLoggedIn:{loginURL},isLoggedOut:{logooutURL}}}
async function checkLoginStatus() {
  
}

// TODO: Delete config function. /BillingConfig --> doDelete
async function deleteConfiguration() {

}

// TODO: Create config and redirect/populate edit form. 
async function createConfiguration() {

}
