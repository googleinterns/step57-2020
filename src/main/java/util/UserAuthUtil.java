package util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthUtil {
  /**
   * Returns a boolean for the user's login status
   * @return user login status
   */
  public static boolean isUserLoggedIn() {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.isUserLoggedIn();
  }

  /**
   * @param redirect URL for webpage to return to after login
   * @return URL for user to click to login
   */
  public static String getLoginURL(String redirect) {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.createLoginURL(redirect);
  }

  /**
   * @param redirect URL for webpage to return to after logout
   * @return URL for user to click to logout
   */
  public static String getLogoutURL(String redirect) {
    return UserServiceFactory.getUserService().createLogoutURL(redirect);
  }

  /**
   * Helper method to return a User object
   */
  public static User getUser() {
    return UserServiceFactory.getUserService().getCurrentUser();
  }

  /**
   * Determines whether a user is authorized to use the requested resource
   * @return true when the user's email domain is "google.com"
   */
  public static boolean isUserAuthorized() {
    return getDomainName().equals("google.com");
  }

  /**
   * @return domain name from a user's email address
   */
  public static String getDomainName() {
    String email = getUser().getEmail();
    return email.substring(email.indexOf('@') + 1);
  }
}