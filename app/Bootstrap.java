/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-10-9
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        // Check if the database is empty
        if(User.count() == 0) {
            System.out.println("initial data");
            //源程序没有这句话，最好再加载数据前先清空数据，否则会出现加载重复ID的问题
            Fixtures.deleteDatabase();
            Fixtures.loadModels("initial-data.yml");
        }
    }

}
