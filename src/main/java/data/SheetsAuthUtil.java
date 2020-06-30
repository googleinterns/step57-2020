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
package data;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/** 
 * A class to create and send an AppIdentityCredential object that allows for Sheets API interaction 
 */
public class SheetsAuthUtil {
  public static AppIdentityCredential authorize() throws IOException, GeneralSecurityException {
         
    // Build GoogleClientSecrets from JSON file.
    InputStream in = SheetsAuthUtil.class.getResourceAsStream("/google-sheets-api.json");
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
      JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

    List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
 
    AppIdentityCredential credential = new AppIdentityCredential(scopes);

    return credential;
  }
}

