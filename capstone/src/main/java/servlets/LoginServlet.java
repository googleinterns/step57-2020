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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.ResponseBuilder;
import java.io.PrintWriter;
import javax.servlet.http.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import util.UserAuthUtil;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html;";
  private static final String REDIRECT_LINK = "/Login";

  // Returns a URL to either login or logout.
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType(CONTENT_TYPE_TEXT_HTML);
    response.getWriter().println(ResponseBuilder.toJson(UserAuthUtil.isUserLoggedIn(), REDIRECT_LINK));
  }
}
