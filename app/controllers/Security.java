package controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-10-9
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
import models.*;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        return User.connect(username, password) != null;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static boolean check(String profile) {
        return "admin".equals(profile) && User.find("byEmail", connected()).<User>first().isAdmin;
    }
}
