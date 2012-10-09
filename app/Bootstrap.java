/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-10-9
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        // Check if the database is empty
        if(User.count() == 0) {
            System.out.println("initial data");
            Fixtures.loadModels("initial-data.yml");
        }
    }

}
