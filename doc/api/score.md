# Score
We will define a general structure for the /score path.

Note that for all these request, the user has to be authenticated successfully using
HTTP Basic Authentication. Otherwise, the server will respond with the code `401 Unauthorized`.

## GET /score
Get all the scores for the scoreboard display. 
```
{
  scores: [{ "username": username, "score": 100 "level": 1},
           { "username": username1, "score": 200 "level": 2},
           { "username": username2, "score": 300 "level": 3}]
}
```
and if the set of all scores is empty:
```
{
  scores: []
}
```
### HTTP status code
- `200 OK` if querying is successful
- `404 Not Found` if there an exception is thrown

## GET /score/{user}
Get the score of user with name {user}
```
{
  "username": user, 
  "score": 100 
  "level": 1
}
```
### HTTP status code
- `200 OK` if everything is fine 
- `404 Not Found` if there is no such score or an exception is caught

## PUT /score/{user}/{score}
Update score of the specified user with username {user} to be
equal to integer proved at {score}. Level is also adequately updated.  

In case of success:
```
{
  {user}'s score has been updated"
}
```
and in case of failure:
```
{
  "This username doesn't exist"
}
```
### HTTP status code
- `200 OK` f update successful
- `404 Not Found` if specified username (hence their score) doesn't exist
## Appendix

* https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
