# activity
We will define a general structure for the /activity path.

Note that for all these request, the user has to be authenticated successfully using
HTTP Basic Authentication. Otherwise, the server will respond with the code `401 Unauthorized`.

## GET /activity
Gets all activities that correspond to the user credentials.
The general successful response for multiple activities will look like this:
```
{ "activities": [
  {
    "activityId": 9,
    "username": "gebruiker01",
    "activityTypeId": 1,
    "param1": 43,
    "param2": 23,
    "score": 432
  },
  {
    "activityId": 3,
    "username": "gebruiker01",
    "activityTypeId": 1,
    "param1": 32,
    "param2": 4123,
    "score": 32
  }]
}
```
OR if the user has no activities, reponse body will look like:
```
{ "activities": [
  ]
}
```

### HTTP status code
- `200 OK` if querying is successful
- `400 Bad Request` if something goes wrong

## GET /activity/1
Gets the activity with ID 1 that corresponds with the user credentials.
For a single activity we will use the same structure with length 1:
```
{
  "activityId": 1,
  "username": "gebruiker01",
  "activityTypeId": 1,
  "param1": 32,
  "param2": 43,
  "score": 32
}
```

### HTTP status code
- `200 OK` if everything is fine
- `404 Not Found` if the given activityId is not found or not associated with the authorized user.

## POST /activity
Registers an activity to the server.
A general POST request will look like this:
```
{
        "activityTypeId" : type_id,
        "param1" : param,
        "param2" : param
}
```
For user credentials match the activity to the user.

### HTTP status code
- `200 OK` if everything is fine
- `400 Bad Request` if the client tries to post an invalid activity (for example, when the
activity type needs 1 argument, but param2 is not null).
- `500 Internal Server Error` if something went wrong on the server.

## PUT /activity/1
Updates activity with id 1.
The request and response will be the same as with a normal POST. However, PUT will replace/update
an existing activity while POST would have created a new one.

### HTTP status code
- `200 OK` if everything is fine
- `400 Bad Request` if the client tries to post an invalid activity (for example, when the
activity type needs 1 argument, but param2 is not null).
- `500 Internal Server Error` if something went wrong on the server.

## DELETE /activity/1
Deletes activity with id 1.
If the activity is deleted successfully response should by 200 ("OK").

### HTTP status code
- `200 OK` if everything is fine
- `404 Not Found` if the given activityId is not found or not associated with the authorized user.

## Error handling
In case a request was not successful we define a simple format for handling error messages.
Every error message comes with an error type and an error message plus a http status code in the 400 range. The general structure will look like this:
```
{"type": error_type, "message": error_message}
```
The error type and message should be understandable for the user.

## Appendix

* https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
