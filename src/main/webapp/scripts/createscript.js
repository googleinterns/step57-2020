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


var accountIdNumber = 1;
/**
  * Adds additional account ID inputs for the user. 
  */
function addNewAccount() {
  var form = document.getElementById('create-form');
  
  var label = document.createElement('label');
  label.innerText = 'Account ID ' + accountIdNumber + ': ';

  var input = document.createElement('input');
  input.type = 'text';
  input.id = 'account-id-' + accountIdNumber;

  var lineBreak = document.createElement('br');
  form.appendChild(label);
  form.appendChild(input);
  form.appendChild(lineBreak);
  
  accountIdNumber += 1;
}

function buildQueryString() {
  const legacyCustomerID = document.getElementById('legacy-customer-id').value;
  const nextGenCustomerID = document.getElementById('next-gen-customer-id').value;
  return `/CreateServlet?legacycustomerID=${legacyCustomerID}&nextgencustomerID=${nextGenCustomerID}`;
}

async function postNewConfig() {
  var postResponse = await fetch(buildQueryString(), {
    method: 'POST',
  });

  var responseJson = await postResponse.json();

  if (confirm(responseJson)) {
    window.location.reload();
  }

  // TODO: Alerts for errors. 


}