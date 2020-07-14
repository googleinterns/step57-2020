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

import com.google.gson.JsonParser;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import util.OAuthConstants;


/** A servlet to request access and refresh access tokens for the Sheets API.*/
@WebServlet("/api/oauth/callback/sheets")
public class OAuthCallbackServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
  private final String SECRET_FILEPATH = "../../src/main/resources/secret.txt";
  private final String CODE_PARAM = "code";
  private final String ERROR_PARAM = "error";
  private final String STATE_PARAM = "state";
  private final String ACCESS_TOKEN_PARAM = "access_token";
  private final String HEADER_TYPE = "Content-Type";
  private final String HEADER_FORM = "application/x-www-form-urlencoded";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String error = request.getParameter(ERROR_PARAM);
    String authCode = request.getParameter(CODE_PARAM);
    String state = request.getParameter(STATE_PARAM);

    // Print any error the OAuth provider gave us.
    if (error != null && !error.isEmpty()) {
      response.getWriter().print(error);
      response.setStatus(401);
      return;
    }

    // Check that the OAuth provider gave us the authorization code.
    if (authCode == null || authCode.isEmpty()) {
      response.setStatus(400);
      return;
    }
        
    String sessionOauthState = (String) request.getSession().getAttribute(OAuthConstants.SHEETS_SESSION_KEY);

    if (!state.equals(sessionOauthState)) {
      response.setStatus(401);
      return;
    }

    System.out.println(state);


    // Build the access token request.
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest tokenRequest = buildTokenRequest(authCode); 

    // Request the access tokens.
    HttpResponse tokenResponse = httpClient.sendAsync(tokenRequest, 
      BodyHandlers.ofString()).join();
    String tokenResponseBody = tokenResponse.body().toString();

    // Parse to find access token.
    JsonElement accessToken = parseAccessToken(tokenResponseBody); 

    response.setContentType("text/html");
    response.getWriter().printf("<h1>the access token for the Sheets API is %s</h1>", accessToken);

    // Store access token in a session.
    HttpSession session = request.getSession();
    session.setAttribute("accessToken", accessToken.toString());
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

  private String getClientSecret() {
    String clientSecret = OAuthConstants.CLIENT_SECRET;
    File secret = new File(SECRET_FILEPATH);
 
    Scanner input;
    try {
      input = new Scanner(secret);
    } catch(FileNotFoundException e) {
      return null;
    }
 
    while(input.hasNextLine()) {
      clientSecret += input.nextLine();
    }
    return clientSecret;
  }

  private HttpRequest buildTokenRequest(String authCode) {
    String tokenRequestBody = String.format("%s&%s&%s&%s&%s", 
      OAuthConstants.GRANT_TYPE, OAuthConstants.AUTH_CODE + authCode, 
      getRedirectUri(), OAuthConstants.CLIENT_ID, getClientSecret());

    return HttpRequest.newBuilder(URI.create(OAuthConstants.TOKEN_URI))
      .header(HEADER_TYPE, HEADER_FORM).POST(BodyPublishers.ofString(
      tokenRequestBody)).build();
  }

  private JsonElement parseAccessToken(String tokenResponseBody) {
    JsonObject tokenResponseObj = JsonParser.parseString(tokenResponseBody)
      .getAsJsonObject();

    return tokenResponseObj.get(ACCESS_TOKEN_PARAM);
  }
}