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
 * Builds a query string containg the vendor, legacy customer, 
 * next gen customer IDs.
 */
function buildQueryString() {
  const vendorID = document.getElementById('vendor-id').value;
  const legacyCustomerID = document.getElementById('legacy-customer-id').value;
  const nextGenCustomerID = document.getElementById('next-gen-customer-id').value;

  // Check if any of the required feilds have not been filled. 
  if (vendorID == '' || legacyCustomerID == '' || nextGenCustomerID == '') {
    window.alert('One or more required fields have not been set!')
  } else {
    var rawString = `/CreateServlet?vendorID=${vendorID}&legacyCustomerID=${legacyCustomerID}
      &nextGenCustomerID=${nextGenCustomerID}`;
      
    // Cleans any whitespace that may be in the query string.
    var queryString = rawString.replace(/\s+/g, ' ').trim();
    return queryString;
  }
}

// Call the doPost method in CreateServlet to create a new file.
async function postNewConfig() {
  const queryString = buildQueryString();
  
  var response = await fetch(queryString, {method: 'POST'});
  var responseJson = await response.json();

  // Redirect to the add account page when user confirms the alert. 
  // Confirm method shows an alert for confimration by the user. 
  if (confirm(responseJson)) {
    window.location = 'add-account.html';
  }
}

