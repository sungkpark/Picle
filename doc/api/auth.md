# auth

For authentication, we use HTTP Basic Authentication. With every request to the server,
a valid `Authorization` header field should be provided. This header contains a base64
encoded username and password. These are checked against the data in the database of the
server. If they match, the server will process the request further, else, it will respond
with a `401 Unauthorized` response.

# GET /auth
Although there is no actual logging in, because we use HTTP Basic Authentication, we still
implement the `/auth` endpoint. The client can use the `/auth` endpoint to verify that the
credentials are correct, without doing anything else with the API.

The response of the server is either:
* A HTTP Response with HTTP code `401 Unauthorized` and no body: if the request is not authorized
* A HTTP Response with HTTP code `200 OK` and no body: if the request is properly authorized