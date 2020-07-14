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

console.log('Hello World');

// Fetch methods to search endpoint
async function populateCustomerList() {
  console.log('Hello function');
  const response = await fetch('/VendorServlet');

  var customer_json = await response.json();

  let customer_string = customer_json.toString();

  console.log(customer_string);

  for ( var i = 0; i < customer_json.length; i++) {
    console.log(customer_json[i]);
  }

  // var html = '';
  // for (var i = 0; i < customer_json.length; i++) {
  //   html += '<option value="' + customer_json[i] + '">'
  //   + customer_json[i] + '</option>';
  // }

  // document.getElementById('customer-ids').innerHTML = html;
}

// // Fetch method for putting the specified config to the page
// async function getVendorJson() {

//   // BillingConfig returns the WHOLE configuration 

//   // This fetch gives a JSON as a string, rather than an array.
//   const response = await fetch('/BillingConfig');

//   let config_string = await response.text();

//   let textbox = document.getElementById('textbox');

// }


// Editfile calls BillingConfig

// Method to populate edit form 

// TODO: Method for enums: Check which of the tags has data under it {isLoggedIn:{loginURL},isLoggedOut:{logooutURL}}}

