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
  // TODO: Implement functionality for hashmap parsing instead of simple array.
  var response = await fetch('/VendorServlet');

  var vendorJson = await response.json();

  // Loops over this array to make the options for the Customer ID dropdown.
  var vendorHtml = '';

  for (var key in vendorJson) {
    console.log(key);
    vendorHtml += '<option value="' + key + '">'
    + key + '</option>';
  }

  // Adds the options to the page, allowing them to be viewed. 
  document.getElementById('customer-ids').innerHTML = vendorHtml;
}

// TODO: Add dependent drop-down 
function populateAccountList() {

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
