/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-10-8
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
package models;

import javax.persistence.Entity;
import java.util.*;
import play.db.jpa.*;


@Entity

public class User extends Model{
    public String email;
    public String password;
    public String fullname;
    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
}
