# Faux Pas OAuth Server (fp-oauth-ser)

## Version:

Release: v0.01 Specs Only

Upcoming: tbd

## About:

A Client Credentials OAuth 2.0 grant type implementation as a Java server application and (tbd vendor) NoSQL datastores.
	
### Version 1.0 Goals:

- End User Requirement: Register Clients -> i.e. provide your own NoSQL datastore (vendor tbd), populated with at least one Client entity (scheme tbd, at minimum client_id, client_secret, and response_uri).  
- Implement: Session DAO with 'createSessionTable', 'revokeSessionToken'.
- Implement: ['Client Credentials Grant'](https://oauth.net/2/grant-types/client-credentials) endpoint which takes client_id and client_secret and compares them to the datastore values for authentication.  If valid insert new Session in table and return it's access token and refresh token to client's response_uri.  If invalid 403 status returned.
- Implement: ['OAuth 2.0 Token Introspection'](https://www.oauth.com/oauth2-servers/token-introspection-endpoint) endpoint which returns a JSON object with active member set true if requested token is in Sessions datastore and ttl has not expired.
