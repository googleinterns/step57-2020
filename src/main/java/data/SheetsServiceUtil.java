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

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import java.io.IOException;
import java.security.GeneralSecurityException;

/** A class to create a Sheets instance. */
public class SheetsServiceUtil {

  public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
    AppIdentityCredential credential = SheetsAuthUtil.authorize();

    return new Sheets.Builder(new UrlFetchTransport(), new JacksonFactory(), credential)
      .build();
  }
}