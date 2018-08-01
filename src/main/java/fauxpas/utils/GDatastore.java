package fauxpas.utils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import fauxpas.entities.SessionRecord;

import java.util.Optional;

public class GDatastore {
    private static Datastore datastore = null;

    /**
     * Set the datastore object;
     */
    private static void init() {
        if (datastore == null) {

            if (System.getenv("DATASTORE_LOCATION").equalsIgnoreCase("local")) {
                System.out.println("Connecting to datastore emulator...");
                datastore = DatastoreOptions.newBuilder()
                      .setHost("http://"+System.getenv("DATASTORE_EMULATOR_HOST"))
                      .setProjectId(System.getenv("DATASTORE_PROJECT_ID"))
                      .build().getService();
            }
            else {
                System.out.println("Connecting to default datastore...");
                datastore = DatastoreOptions.getDefaultInstance().getService();
            }
        }
    }

    /**
     * Get a Session Key for a given client.
     * @param client_id - the client's identifier.
     * @return a key for the client.
     */
    public static Key getSessionKey(String client_id) {
        init();
        return datastore.newKeyFactory().setKind("Session").newKey(client_id);
    }

    /**
     * Get optional to client's session, if one exists
     * @param client_id client to retrieve session for.
     * @return Optional to the client's session or nullable.
     */
    public static Optional<SessionRecord> getSession(String client_id) {
        init();
        Entity result = datastore.get(getSessionKey(client_id));
        if (result != null) {
            return Optional.of(new SessionRecord(result));
        }
        else {
            return Optional.empty();
        }

    }

    /**
     * Revoke a client's authorization session but deleting it from the datastore.
     * @param client_id - client who's session should be revoked.
     */
    public static void revokeSession(String client_id) {
        init();
        datastore.delete(getSessionKey(client_id));
    }

    /**
     * Upsert an Entity
     * @param entity - the entity to upsert.
     */
    public static void setEntity(Entity entity) {
        init();
        datastore.put(entity);
    }
}
