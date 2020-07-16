// Copyright 2019 Google LLC
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
package javatests;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import data.JsonConverter;
import data.Vendor;
import data.Account;
import data.ResponseBuilder;
import org.mortbay.jetty.Response;

/** Test the functionality of the JsonConverter class. */
@RunWith(JUnit4.class)
public final class ResponseBuilderTest {
  /* Allows local unit testing of cloud API
   * https://cloud.google.com/appengine/docs/standard/java/tools/localunittesting
   * (use the helper variable to set User settings).
   */
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
          new LocalUserServiceTestConfig()).setEnvIsAdmin(true).setEnvIsLoggedIn(true);
  private ResponseBuilder builder;

  @Before
  public void setUp() {
    helper.setUp();
    builder = new ResponseBuilder();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void validateLoggedInJsonOutput() {
    String json = builder.toJson(true, "/Login");
    try {
      new JSONObject(json);
    } catch (JSONException e) {
      throw new JSONException("JSONException has occurred. Failed to parse JSON string");
    }
    assertTrue(true);
  }

}
