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
package servlets;

import data.JsonConverter;
import data.Vendor;
import data.Account;
import data.SheetsConverter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.security.GeneralSecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json;";
  public static final String REDIRECT_EDITFILE = "/editfile.html";

  public static final String VENDOR_ID = "vendor-id";
  public static final String ACCOUNT_ID = "account-id";

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // POST method --> creates a new configuration file with a pre-made json config 
    // with the given vendor id and account id and all other values as null.
    

  }
}


// TODO: Create endpoint makes a file with a jason with all ids that are null 
// except for the vendor/account id 