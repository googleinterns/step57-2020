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
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import util.UserAuthUtil;

import static org.junit.Assert.assertTrue;
import java.security.GeneralSecurityException;
import java.io.IOException;


/** Test the functionality of the SheetsConverter class. */
@RunWith(JUnit4.class)
public final class UserAuthUtilTest {

  private static final String UNAUTHORIZED_EMAIL = "example@foobar.com";
  private static final String UNAUTHORIZED_DOMAIN = "foobar.com";
  private static final String AUTHORIZED_EMAIL = "example@google.com";
  private static final String AUTHORIZED_DOMAIN = "google.com";
  private static final String CONSTRUCTOR_DOMAIN = "gmail.com";

  @Test
  public void testGetDomainName() {
    String emailAddress = "example@foobar.com";
    String expectedDomainName = "foobar.com";
//    String actualDomainName = UserAuthUtil.getDomainName();
  }

  @Test
  public void testUnauthorizedUser() {
    // Check that unauthorized users are correctly identified
    User user = new User(UNAUTHORIZED_EMAIL, CONSTRUCTOR_DOMAIN);
    String expectedDomain = UNAUTHORIZED_DOMAIN;
    String actualDomain = UserAuthUtil.getDomainName();
    System.out.println(actualDomain);
  }

  @Test
  public void testAuthorizedUser() {

  }
}