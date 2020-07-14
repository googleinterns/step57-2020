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
  * Function that fetches vendor IDs from VendorServlet and adds them to the
  * Customer ID dropdown.
  */
async function populateCustomerList() {
  const response = await fetch('/VendorServlet');

  // response is a json array that is parsed into json.
  var customer_json = await response.json();

  // Loops over this array to make the options for the Customer ID dropdown,
  var html = '';
  for (var i = 0; i < customer_json.length; i++) {
    html += '<option value="' + customer_json[i] + '">'
    + customer_json[i] + '</option>';
  }

  // Adds the options to the page, allowing them to be viewed. 
  document.getElementById('customer-ids').innerHTML = html;
}

// TODO: Fetch method for putting the specified config to the page.
// TODO: Method to populate edit form.
// TODO: Method for enums: Check which of the tags has data under it {isLoggedIn:{loginURL},isLoggedOut:{logooutURL}}}

