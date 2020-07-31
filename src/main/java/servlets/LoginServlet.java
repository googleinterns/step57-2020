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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
  private static final String OAUTH_URL = "/OAuth";
  private static final String LOG_PAGE_URL = "/login.html";
  private static final String TEXT_TYPE = "text/html";
  private final String LOGOUT_URL = "logoutURL";
  private final String LOGIN_URL = "loginURL";
  private final String LOGIN_STATUS = "loginStatus";
  private final String TRUE = "true";
  private final String FALSE = "false";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(TEXT_TYPE);
    UserService userService = UserServiceFactory.getUserService();
    HashMap<String, String> loginObject = new HashMap<String, String>();

    if(userService.isUserLoggedIn()) {
      loginObject.put(LOGOUT_URL, userService.createLogoutURL(LOG_PAGE_URL)); 
      loginObject.put(LOGIN_STATUS, TRUE); 
    } else {
      loginObject.put(LOGIN_URL, userService.createLoginURL(OAUTH_URL));
      loginObject.put(LOGIN_STATUS, FALSE); 
    }

    // Convert the URL to a JSON String.
    String jsonMessage = messageListAsJson(loginObject);

    // Send the JSON message as the response.
    response.setContentType(TEXT_TYPE);
    response.getWriter().println(jsonMessage);
  }

  /**
  * Converts a Java HashMap into a JSON string using Gson.  
  */
  private String messageListAsJson(HashMap<String, String> output) {
    return new Gson().toJson(output);
  }
}
