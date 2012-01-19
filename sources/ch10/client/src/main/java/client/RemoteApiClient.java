package client;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import java.io.IOException;

public class RemoteApiClient {
    public static void main(String[] args) {
        try {

            String username = "test";
            String password = "test";

            RemoteApiOptions options = new RemoteApiOptions()
                    .server("localhost", 8085)
                    .remoteApiPath("/remote-api")
                    .credentials(username, password);
            RemoteApiInstaller installer = new RemoteApiInstaller();
            installer.install(options);

            DatastoreService datastoreService =
                DatastoreServiceFactory.getDatastoreService();

            for(int i = 0; i < 10; i++) {
                Entity entity = new Entity("AutoCount");
                entity.setProperty("ColA", "a" + i);
                entity.setProperty("ColB", "b" + i);
                entity.setProperty("ColC", "c" + i);
                entity.setProperty("ColD", "d" + i);
                entity.setProperty("ColE", "e" + i);
                entity.setProperty("ColF", "f" + i);
                datastoreService.put(entity);
            }

            installer.uninstall();;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
