# Social
We will define a general structure for the /social path.

Note that for all these request, the user has to be authenticated successfully using
HTTP Basic Authentication. Otherwise, the server will respond with the code `401 Unauthorized`.

## GET /social
Get all friends of the authenticated user.
The response then will be:
```
{
  friends: [{ "username": username},
           { "username": username1},
           { "username": username2}]
}
```
if Authenticating user has no Friend relations, result looks like:
```
{
  friends: []
}
```

## GET /social/friendname
Get a single friend of the user with name "friendname".
The response will be:
```
{
    "username": friendname
}
```


## POST /social
Registers a friend for the authenticated user. 
The body of the post request will be:
```
{
    "username": friendname
}
```

## DELETE /social/friendname
Removes friend with name "friendname"for the authenticated user.


### HTTP status code
- `200 OK` if everything is fine/empty set is returned
- `404 Not Found` if something goes wrong

## Appendix

* https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
