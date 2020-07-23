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

  // Adds the options to the page, allowing them to be viewed. 
  document.getElementById('customer-ids').innerHTML = vendorHtml;
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
  // TODO: Add query string support to sent to servlet.
  var vendorID = document.getElementById('customer-ids');
  
  // Fetch the json configuration and format it to print on the page. 
  fetch('/BillingConfig')
    .then(response => response.json())
    .then(data => document.getElementById('json-text').innerText = 
    JSON.stringify(data, undefined, 4));
}
