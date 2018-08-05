# Faux Pas OAuth Server (fp-oauth-ser)

## Version:

Release: v0.01 (Specs Only)
gcds-Dev: v0.1  (dao v1)

Upcoming: approx gcds-Dev 0.2

## About:

A Client Credentials OAuth 2.0 grant type implementation as a Java server application.  This branch (or build) is for use with Google Cloud Datastore (I'll abrivaite as gcd or gcds).

Read more about gcds at the documentation (site)[https://cloud.google.com/docs/].

### Version 1.0 Goals:

- End User Requirement: Register Clients -> i.e. provide your own Google Cloud Datastore, populated with at least one Client entity (scheme tbd, at minimum client_id, client_secret, and response_uri).
- Implement: GDatastore DAO with public interface 'getSessionKey(client_id)', 'getClientKey(client_id)', 'getSession(client_id)', 'getClient(client_id)', 'revokeSession(client_id)', and 'upsertSession(Session)'.
- Implement: ['Client Credentials Grant'](https://oauth.net/2/grant-types/client-credentials) endpoint which takes client_id and client_secret and compares them to the datastore values for authentication.  If valid insert new Session in table and return it's access token and refresh token to client's response_uri.  If invalid 403 status returned.
- Implement: ['OAuth 2.0 Token Introspection'](https://www.oauth.com/oauth2-servers/token-introspection-endpoint) endpoint which returns a JSON object with active member set true if requested token is in Sessions datastore and ttl has not expired.

### Setup Info:

Setup will vary depending on if you are running the gcds emulator, running this server application on the Compute Engine or App Engine, or as a service account application.
These instructions apply to using 'service account credentials' and have been tested using a local gcds emulator.

On launch, the application will use environment variables to establish a connection to the gcds by using the standard Java client API call:

```
Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
```

- You will need service account credentials, even if you want to use a local gcds emulator. [Read more about them here](https://cloud.google.com/iam/docs/granting-roles-to-service-accounts)
- You will also need to set credentials [Learn how to set credentials here](https://cloud.google.com/docs/authentication/production)

You should at least acquire credentials, be sure to secure them, and set the following environment variables.

```
export GOOGLE_APPLICATION_CREDENTIALS=<path>
export $DATASTORE_EMULATOR_HOST=<host>:<port>
export $DATASTORE_PROJECT_ID=<project-id>
```



