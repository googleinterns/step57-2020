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

// Fetch methods to search endpoint

// Fetch method
async function getVendorJson() {

  // BillingConfig returns the WHOLE configuration 

  // This fetch gives a JSON as a string, rather than an array.
  const response = await fetch('/BillingConfig');


  var textbox = document.getElementById('textbox');
  var json_string = response_text;

  textbox.innerHTML = JSON.stringify(json_string);

}


function getVendors() {
  // VendorServlet returns array of vendor IDs
  // This should be onLoad()
  // No account search... yet
  fetch('/VendorServlet').then(response => response.json())
  .then((VendorConfigs) => {

    // Make a for loop here
    for (var i = 0; i < response.length; i++) {
      var option = '<option value=' + response[i] + '>' + response[i] + '</option>';

    }

  })  
}


// Editfile calls BillingConfig

// Method to populate edit form 

