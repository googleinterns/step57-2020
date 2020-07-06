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

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import util.UserAuthUtil;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;";
  private static final String REDIRECT_LINK = "/Login";       // TODO @Vincent

  // Returns whether user is logged in or logged out, with a URL to either login or logout
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    PrintWriter out = response.getWriter();
    if (UserAuthUtil.isUserLoggedIn()) {
      out.println(UserAuthUtil.getLogoutURL(REDIRECT_LINK));
    } else {
      out.println(UserAuthUtil.getLoginURL(REDIRECT_LINK));
    }
  }
}