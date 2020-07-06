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
package javatests;

import java.util.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.apache.http.auth.AUTH;
import org.junit.After;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import util.UserAuthUtil;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

import java.security.GeneralSecurityException;
import java.io.IOException;

import static org.junit.Assert.*;


/** Test the functionality of the SheetsConverter class. */
@RunWith(JUnit4.class)
public final class UserAuthUtilTest {

  private static final String UNAUTHORIZED_EMAIL = "example@foobar.com";
  private static final String UNAUTHORIZED_DOMAIN = "foobar.com";
  private static final String AUTHORIZED_EMAIL = "example@google.com";
  private static final String AUTHORIZED_DOMAIN = "google.com";
  private static final String CONSTRUCTOR_DOMAIN = "gmail.com";

  /* Allows local unit testing of cloud API
   * https://cloud.google.com/appengine/docs/standard/java/tools/localunittesting
   * (use the helper variable to set User settings)
   */
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
          new LocalUserServiceTestConfig()).setEnvIsAdmin(true).setEnvIsLoggedIn(true);

  @Before
  public void setUp() {
    helper.setUp();
    helper.setEnvAuthDomain(CONSTRUCTOR_DOMAIN);
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void testGetDomainName() {
    // Ensure that domain name is properly extracted from the email address.
    helper.setEnvEmail(UNAUTHORIZED_EMAIL);
    String actualDomain = UserAuthUtil.getDomainName();
    assertEquals("Domain name was incorrectly sliced", UNAUTHORIZED_DOMAIN, actualDomain);
  }

  @Test
  public void testUnauthorizedUser() {
    // Check that unauthorized users are correctly identified.
    helper.setEnvEmail(UNAUTHORIZED_EMAIL);
    boolean isAuthorized = UserAuthUtil.isUserAuthorized();
    assertFalse("User should be marked as unauthorized", isAuthorized);
  }

  @Test
  public void testAuthorizedUser() {
    // Check that authorized users are correctly identified.
    helper.setEnvEmail(AUTHORIZED_EMAIL);
    boolean isAuthorized = UserAuthUtil.isUserAuthorized();
    assertTrue("User should be marked as unauthorized", isAuthorized);
  }
}