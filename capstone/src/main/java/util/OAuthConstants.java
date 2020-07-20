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
package util;

/** Store varaible constants for the OAuthCallback and Login Servlets. */
public final class OAuthConstants {
  public static final String CLIENT_ID = "client_id=150737768611-svndjtlklolq53g4ass4r3sqal2i31p5.apps.googleusercontent.com";
  public static final String ENVIRONMENT_VARIABLE = "DOMAIN";
  public static final String OAUTH_CALLBACK_SERVLET = "/api/oauth/callback/sheets";
  public static final String SCOPE = "scope=https://www.googleapis.com/auth/spreadsheets";
  public static final String OAUTH_LOGIN_URI = "https://accounts.google.com/o/oauth2/v2/auth";
  public static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
  public static final String SHEETS_SESSION_KEY = "oauth-service-sheets";
  public static final String SHEETS_SESSION_TOKEN_KEY = "oauth-access-token-sheets";
  public static final String GRANT_TYPE = "grant_type=authorization_code";
  public static final String REDIRECT_URI = "redirect_uri=";
  public static final String RESPONSE_TYPE = "response_type=code";
  public static final String CLIENT_SECRET = "client_secret=";
  public static final String AUTH_CODE = "code=";
  public static final String STATE = "state=";  
}