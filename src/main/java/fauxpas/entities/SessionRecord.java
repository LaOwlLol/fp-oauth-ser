package fauxpas.entities;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import fauxpas.utils.GDatastore;

public class SessionRecord {

    /**
     * client_id - client this session was issued to.
     */
    private String client_id;

    public String getClient_id() {
        return client_id;
    }

    /**
     * accessToken - the token issued to this.client_id for access to resource API servers.
     * Note** whether this is the hashed token or the clear text token matters! (TODO)
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * issued - the time the accessToken was issued.
     * Note** when instantiating a new SessionRecord this value should be the System.currentTimeMillis() value.
     */
    private Long issued;

    public Long getIssued() {
        return issued;
    }

    /**
     * ttl - time to live or how long the token should be considered valid.
     * Note** this value is the number of milliseconds a record will live compared against System.currentTimeMillis().
     */
    private Long ttl;

    public Long getTtl() {
        return ttl;
    }

    //Constructors

    public SessionRecord(String client_id, String accessToken, long issued, long ttl) {
        this.client_id = client_id;
        this.accessToken = accessToken;
        this.issued = issued;
        this.ttl = ttl;
    }

    public SessionRecord(Entity sessionEntity) {
        this.client_id = sessionEntity.getKey().getName();
        this.accessToken = sessionEntity.getString("token");
        this.issued = sessionEntity.getLong("issued");
        this.ttl = sessionEntity.getLong("ttl");
    }

    //Helpers

    /**
     * Turn this object in to a Google Cloud Datastore entity.
     * Note** whether this token is hashed or clear text matters! (TODO)
     *
     * @return this object as an Entity for storage.
     */
    public Entity toEntity(){
        Key sessionKey = GDatastore.getSessionKey(this.client_id);

        return  Entity.newBuilder(sessionKey)
              .set("token", this.accessToken)
              .set("issued", this.issued)
              .set("ttl", this.ttl)
              .build();
    }

    public String toString() {
        return "Client_ID: "+ this.client_id+"\n" +
              "Token:" + this.accessToken+"\n"+
              "Issued: " + this.issued + "\n"+
              "ttl: "+ this.ttl;
    }

    /**
     * Check if this session has expired. Meaning the current system time minus session's issued time is less
     * than the sessions time to live.
     * @return true if this session is still alive. false if this session has expired.
     */
    public boolean hasExpired() {
        if (System.currentTimeMillis() - this.getIssued() > this.getTtl() ) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Get a Invalid Session object.
     * @return Invalid Session
     */
    public static SessionRecord Invalid() {
        return new SessionRecord("invalid", "null", 0, 0);
    }

}
