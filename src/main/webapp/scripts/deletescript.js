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

// Initialize the modal.
$('#deleteForm').on('submit', function(e){
  $('#deleteModal').modal('show');
  e.preventDefault();
});


// Call the doDelete method in the VendorServlet to delete the given vendorID.
async function deleteVendor(form) {
  const vendorID = form.vendorID.value;
  const response = await fetch('/VendorServlet?vendorID=' + vendorID, 
    { method: "DELETE"});
  const deleteMessage = await response.json();

  // Show an alert if there was a problem during the deletion process.
  if(confirm(deleteMessage)){
    window.location.reload();  
  }
}

