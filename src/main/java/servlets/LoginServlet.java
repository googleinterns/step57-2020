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
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import util.UserAuthUtil;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;";
  private static final String REDIRECT_LINK = "/Login";
  private final String CLIENT_ID = "client_id=150737768611-svndjtlklolq53g4ass4r3sqal2i31p5.apps.googleusercontent.com";
  private final String SCOPE = "scope=https://www.googleapis.com/auth/spreadsheets";
  private final String REDIRECT_URI = "redirect_uri=";
  private final String OAUTH_LOGIN_URI = "https://accounts.google.com/o/oauth2/v2/auth";
  private final String RESPONSE_TYPE = "response_type=code";
  private final String OAUTH_CALLBACK_SERVLET = "/api/oauth/callback/sheets";
  private final String ENVIRONMENT_VARIABLE = "DOMAIN";

  // Returns a URL to either login and get OAuth tokens or to logout.
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    PrintWriter out = response.getWriter();
    if (UserAuthUtil.isUserLoggedIn()) {
      out.println(UserAuthUtil.getLogoutURL(REDIRECT_LINK));
    } else {
      // Send user to OAuth consent page.
      response.sendRedirect(getOAuthRedirectURL());
    }
  }

  /** Build the OAuth consent page redirect URL. */
  private String getOAuthRedirectURL() {
    String redirectUri = REDIRECT_URI + getRedirectUri();
    
    return String.format("%s?%s&%s&%s&%s", OAUTH_LOGIN_URI, CLIENT_ID,
      RESPONSE_TYPE, redirectUri, SCOPE); 
  }

  // Build a valid redirect URI to the OAuthCallbackServlet.
  private String getRedirectUri() {
    try {
      URI domainUri = URI.create(System.getenv().get(ENVIRONMENT_VARIABLE));
      return domainUri.resolve(OAUTH_CALLBACK_SERVLET).toString();
    } catch (NullPointerException e) {
      LOGGER.severe("The DOMAIN environment variable is not set.");
      throw e;
    }
  }
}
