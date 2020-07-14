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

import data.FileParser;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/VendorServlet")
public class VendorServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // GET method --> returns a JSON array of existing Vendor IDs
    FileParser fileParser = new FileParser();
    ArrayList<String> vendorIDs = fileParser.getVendorIDs();

    Gson gson = new Gson();

    String dummyResponse = "[\"CADE01\",\"VINCENT01\",\"CHARLIE01\",\"JAKE01\",\"IAN01\"]";
    response.setContentType("text/html;");
    response.getWriter().println(dummyResponse);
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // DELETE method --> deletes vendor configuration directory
  }
}