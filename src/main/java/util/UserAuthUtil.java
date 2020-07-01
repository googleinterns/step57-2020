package util;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthUtil {
  public static boolean isUserLoggedIn() {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.isUserLoggedIn();
  }
  public static String getLoginURL(String redirect) {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.createLoginURL(redirect);
  }
  public static String getLogoutURL(String redirect) {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.createLogoutURL(redirect);
  }
  public static String getUser() {

  }
}