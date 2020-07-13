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

/** A servlet to request access and refresh tokens. */
@WebServlet("/api/oauth/callback/sheets")
public class OAuthCallbackServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
  private final String CLIENT_ID = "client_id=150737768611-svndjtlklolq53g4ass4r3sqal2i31p5.apps.googleusercontent.com";
  private final String GRANT_TYPE = "grant_type=authorization_code";
  private final String TOKEN_URI = "https://oauth2.googleapis.com/token";
  private final String CODE = "code=";
  private final String CLIENT_SECRET = "client_secret=";
  private final String REDIRECT_URI = "redirect_uri=";
  private final String SECRET_FILEPATH = "../../src/main/resources/secret.txt";
  private final String OAUTH_CALLBACK_SERVLET = "/api/oauth/callback/sheets";
  private final String ENVIRONMENT_VARIABLE = "DOMAIN";


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String error = request.getParameter("error");
    String code = request.getParameter("code");

    System.out.println("CODE: "+ code);

    // Print any error the OAuth provider gave us.
    if (error != null && !error.isEmpty()) {
      response.getWriter().print(error);
      response.setStatus(401);
      return;
    }

    // OAuth provider should have given us a code, otherwise something is wrong here
    if (code == null || code.isEmpty()) {
      response.setStatus(400);
      return;
    }

    String tokenRequestBody = String.format("%s&%s&%s&%s&%s", GRANT_TYPE, CODE + code,
      getRedirectUri(), CLIENT_ID, getClientSecret());

    // Request the tokens.
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest tokenRequest = HttpRequest.newBuilder(URI.create(TOKEN_URI))
      .header("Content-Type", "application/x-www-form-urlencoded")
      .POST(BodyPublishers.ofString(tokenRequestBody)).build();

    HttpResponse tokenResponse = httpClient.sendAsync(tokenRequest, BodyHandlers.ofString()).join();
    String tokenResponseBody = tokenResponse.body().toString();

    // Parses to find access token.
    JsonObject tokenResponseObj = JsonParser.parseString(tokenResponseBody).getAsJsonObject();
    JsonElement accessToken = tokenResponseObj.get("access_token");

    response.setContentType("text/html");
    response.getWriter().printf("<h1>the access token for the Sheets API is %s</h1>", accessToken);
  }

  // Build a valid redirect URI to the OAuthCallbackServlet.
  private String getRedirectUri() {
    try {
      URI domainUri = URI.create(System.getenv().get(ENVIRONMENT_VARIABLE));
      return REDIRECT_URI + domainUri.resolve(OAUTH_CALLBACK_SERVLET).toString();
    } catch (NullPointerException e) {
      LOGGER.severe("The DOMAIN environment variable is not set.");
      throw e;
    }
  }

  private String getClientSecret() {
    String clientSecret = CLIENT_SECRET;
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
}