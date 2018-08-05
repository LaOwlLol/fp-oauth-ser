package fauxpas.entities;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import fauxpas.utils.GDatastore;

public class ClientRecord {

    /**
     * client_id - client's identifier (possibly name).
     */
    private String client_id;

    public String getClient_id() {
        return client_id;
    }

    /**
     * refreshToken - the token issued to this.client_id for refreshing tokens.
     * Note** whether this is the hashed token or the clear text token matters! (TODO)
     */
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * The client's pass-phrase for authentication.  Must be kept encrypted in database.
     */
    private String secret;

    public String getSecret() {
        return secret;
    }

    /**
     * Client's chosen location to receive keys.
    */
    private String responseURI;

    public String getResponseURI() {
        return responseURI;
    }

    public ClientRecord(String client_id, String refreshToken, String secret, String responseURI) {
        this.client_id = client_id;
        this.refreshToken = refreshToken;
        this.secret = secret;
        this.responseURI = responseURI;
    }

    public ClientRecord(Entity clientEntity) {
        this.client_id = clientEntity.getKey().getName();
        this.refreshToken = clientEntity.getString("refreshToken");
        this.secret = clientEntity.getString("secret");
        this.responseURI = clientEntity.getString("responseURI");
    }

    /**
     * Turn this object in to a Google Cloud Datastore entity.
     * Note** whether this token is hashed or clear text matters! (TODO)
     *
     * @return this object as an Entity for storage.
     */
    public Entity toEntity(){
        Key sessionKey = GDatastore.getClientKey(this.client_id);

        return  Entity.newBuilder(sessionKey)
              .set("refreshToken", this.refreshToken)
              .set("secret", this.secret)
              .set("responseURI", this.responseURI)
              .build();
    }

    public String toString() {
        return "Client_ID: "+ this.client_id+"\n" +
              "refreshToken:" + this.refreshToken+"\n"+
              "secret: " + this.secret + "\n"+
              "responseURI: "+ this.responseURI;
    }
}
