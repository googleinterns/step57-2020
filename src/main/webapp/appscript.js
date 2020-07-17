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
  const response = await fetch('/VendorServlet');

  // VendorServlet returns a hashmap/JSON dictionary. 

  // Response is a json array that is parsed into json.
  var customerJson = await response.json();

  console.log(customerJson);

  // Loops over this array to make the options for the Customer ID dropdown.
  var html = '';
  for (var i = 0; i < customerJson.length; i++) {
    html += '<option value="' + customerJson[i] + '">'
    + customerJson[i] + '</option>';
  }

  // Adds the options to the page, allowing them to be viewed. 
  document.getElementById('customer-ids').innerHTML = html;
}

/**
 * Fetch a json configuration as a string and formats it to be shown on
 * the readfile page.
 */
async function addToPage() {
  fetch('/BillingConfig').then(response => 
  response.json()).then(data => console.log(data));
}
//   // TODO: Fix fetch query to get the json. 
//   console.log('Hello World...')
//   var vendorID = document.getElementById('customer-ids');

//   const response = await fetch('/BillingConfig');
//   // + "vendor-id=" 
//   //   + vendorID

//   // Response is a JSON as a string.
//   let config_json = await response.json();

//   console.log(config_json.toString());

//   var html = document.getElementById('json-box');

//   // Stringify converts the json into a string.
//   // html.innerHTML = JSON.stringify(config_json, undefined, 4);
//   html.innerHTML = config_json.toString();

//   // TODO: Add various ways to display the json.
// }
// TODO: Method to populate edit form.
async function populateEditForm() {

}
// TODO: Method for enums: Check which of the tags has data under it {isLoggedIn:{loginURL},isLoggedOut:{logooutURL}}}
async function checkLoginStatus() {
  
}

// TODO: Delete config function. /BillingConfig --> doDelete
async function delteConfiguration() {

}

// TODO: Create config and redirect/populate edit form. 
async function createConfiguration() {

}

// function doGetRequest(var endpointUrl, var params) {
// if (params != '{}') {
// var requestString = endpointUrl + "?";
// for (const [key, value] of Object.entries(params)) {
// requestString += key + "=" + value +"&";
// }
// fetch(requestString);
// }
// }
// 
