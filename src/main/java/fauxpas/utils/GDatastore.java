package fauxpas.utils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import fauxpas.entities.ClientRecord;
import fauxpas.entities.SessionRecord;

import java.util.Optional;

public class GDatastore {
    private static Datastore datastore = null;

    /**
     * Set the datastore object;
     */
    private static void init() {
        if (datastore == null) {
            datastore = DatastoreOptions.getDefaultInstance().getService();
        }
    }

    /**
     * Get a Session Key for a given client.
     * @param client_id - the client's identifier.
     * @return a key for the client's session record.
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
     * Get a Session Key for a given client.
     * @param client_id - the client's identifier.
     * @return a key for the client's record.
     */
    public static Key getClientKey(String client_id) {
        init();
        return datastore.newKeyFactory().setKind("Client").newKey(client_id);
    }

    /**
     * Get optional to a client record, if one exists.
     * @param client_id - client to retrieve.
     * @return Optional to the client's record or nullable.
     */
    public static Optional<ClientRecord> getClient(String client_id) {
        init();
        Entity result = datastore.get(getClientKey(client_id));
        if (result != null) {
            return Optional.of(new ClientRecord(result));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Upsert an Entity
     * Note**: Should remain private access to enforce no is allowed to upsert Entities other than Session (possibly client)
     * @param entity - the entity to upsert.
     */
    private static void upsertEntity(Entity entity) {
        init();
        datastore.put(entity);
    }

    public static void upsertSession(SessionRecord session) {
        upsertEntity(session.toEntity());
    }
}
