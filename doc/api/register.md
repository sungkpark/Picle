# register

## POST /register
Tries to register a new account with the server. The body of a POST request will look
like this:
```
{
    "username" : username,
    "password" : password
}
```

The response of the server will be:

* The status code of the response will return a `201 Created` if the username and password gets registered. The `error_msg` is empty when status is `201 Created`. 
* The status code of the response will return a`400 Bad Request` if the given username is already registered, hence cannot be registered. In case of an `400 Bad Request`, it will return a error message saying 'User already exists'. 

Note that this is an request one can make without giving a valid `Authorization` field in
the header.

## GET /register/is_registered/{username}
Checks if a given username is already registered using username.

The request looks like this:
http://localhost:8080/activity/is_registered/Alp

The response of the server is either:
* A HTTP Response with HTTP code `200 OK` and no body: if the username exists in the database.
* A HTTP Response with HTTP code `404 Not Found` and no body: if the username does not exist in the database.

