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
package servlets;

import java.net.URI;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.ResponseBuilder;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import util.UserAuthUtil;
import util.OAuthConstants;

@WebServlet("/OAuth")
public class OAuthConsentServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

  // Handle the OAuth consent flow.
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Create a state token to prevent request forgery.
    String state = new BigInteger(130, new SecureRandom()).toString(32);
    HttpSession session = request.getSession(true);
    session.setAttribute(OAuthConstants.SHEETS_SESSION_KEY, state);

    // Redirect user to OAuthConsentPage.
    response.sendRedirect(getOAuthRedirectURL(state));
  }

  /** 
   * Build the OAuth consent page redirect URL. 
   * 
   * ex URL: https://accounts.google.com/o/oauth2/v2/auth?client_id=150737768611
   * -svndjtlklolq53g4ass4r3sqal2i31p5.apps.googleusercontent.com&scope=https://
   * www.googleapis.com/auth/spreadsheets&response_type=code&redirect_uri=https:
   * //8080-479d0277-462e-4e1c-8481-71f13e508859.us-central1.cloudshell.dev/api/
   * oauth/callback/sheets&state=907bnrlkufr2cf43ukdq6s97j4
   */
  private String getOAuthRedirectURL(String state) {
    return String.format("%s?%s&%s&%s&%s&%s", OAuthConstants.OAUTH_LOGIN_URI, 
      OAuthConstants.CLIENT_ID, OAuthConstants.SCOPE, OAuthConstants.RESPONSE_TYPE, 
      getRedirectUri(), OAuthConstants.STATE + state); 
  }

  // Build a valid redirect URI to the OAuthCallbackServlet.
  private String getRedirectUri() {
    try {
      URI domainUri = URI.create(System.getenv().get(
        OAuthConstants.ENVIRONMENT_VARIABLE));
      return OAuthConstants.REDIRECT_URI + domainUri.resolve(
        OAuthConstants.OAUTH_CALLBACK_SERVLET).toString();
    } catch (NullPointerException e) {
      LOGGER.severe("The DOMAIN environment variable is not set.");
      throw e;
    }
  }
}
