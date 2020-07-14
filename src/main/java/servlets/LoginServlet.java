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
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import util.UserAuthUtil;
import util.OAuthConstants;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;";
  private static final String REDIRECT_LINK = "/Login";

  // Returns a URL to either login and get OAuth tokens or to logout.
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Create a state token to prevent request forgery.
    String state = new BigInteger(130, new SecureRandom()).toString(32);
    HttpSession session = request.getSession(true);

    session.setAttribute(OAuthConstants.SHEETS_SESSION_KEY, state);

    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    PrintWriter out = response.getWriter();

    if (UserAuthUtil.isUserLoggedIn()) {
      out.println(UserAuthUtil.getLogoutURL(REDIRECT_LINK));
    } else {
      // Send user to OAuth consent page.
      response.sendRedirect(getOAuthRedirectURL(state));
    }
  }

  /** Build the OAuth consent page redirect URL. */
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
