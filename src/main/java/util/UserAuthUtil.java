package util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthUtil {
  /**
   * Returns a boolean for the user's login status.
   * @return user login status.
   */
  public static boolean isUserLoggedIn() {
    return UserServiceFactory.getUserService().isUserLoggedIn();
  }

  public static User getUser() {
    return UserServiceFactory.getUserService().getCurrentUser();
  }

  /**
   * Determines whether a user is authorized to use the requested resource.
   * @return true when the user's email domain is "google.com".
   */
  public static boolean isUserAuthorized() {
    return getDomainName().equals("google.com");
  }

  /**
   * @return domain name from a user's email address.
   */
  public static String getDomainName() {
    String email = getUser().getEmail();
    return email.substring(email.indexOf('@') + 1);
  }
}