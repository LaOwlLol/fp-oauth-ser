package fauxpas.utils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Key;

public class GDatastore {
    private static Datastore datastore = null;

    private static void init() {
        if (datastore == null) {
            datastore = DatastoreOptions.getDefaultInstance().getService();
        }
    }

    public static Key getSessionKey(String client_id) {
        return datastore.newKeyFactory().setKind("Session").newKey(client_id);
    }
}
