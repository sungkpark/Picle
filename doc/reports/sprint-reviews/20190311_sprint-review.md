# Sprint Review Week 5

## What has everyone been working on

Andreas: Has been working on fxml files. Found a library JFoenix with material design UI components. Has been working on the login and dashboard screen.

Alp: published mock test documentation, might add to that later about mock environments which he'll look into this week.
Has also started looking into serverside authentication. Then went back to testing and writing unit tests for the serverside which he's made a lot of progress one.

Alp: published mock test documentation, might add to that later about mock environments which he'll look into this week. 
Has also started looking into server-side authentication and into Spring classes that make authenticating easier by processing every request outside of the controller class. Then went back to testing and writing unit tests for the server which he's made a lot of progress on. Was able to get a mock environment working to test the web endpoints of the server. 
Personally I felt I had not contributed enough to the efforts during most of the previous week due to not having produced anything outside of the doc. However the last couple days proved that I was able to produce good tests and for the server.  

Jordy: Has been looking into spring beans to see how we needed to use them and if they could cause problems. Then went to to testing on client side but was having trouble with it.
After that he looked into spring beans again when Alp said he was having problems with it.


Omar: Switched from design to client code. Was able to implement the controller and model for activities. In addition, created an error handler, a custom error class, and added commonly used classes to the common package created. Just tested them so Omar is now able to keep up with the client code.

Sung: Used his previous branch to work on the activity API and modified it according to the doc API. It's structured in multiple layers. First is the database,
above that is DAO that connects to the database, above that is the service and above that is HTTP requests. For now it's a fake model to test and mariaDB will be added later.
What's missing now is HTTP response code and HTTP basic authentication. Sung also started working on testing after he was done.

Martijn: improved the CI set-up so it now implements checkstyle. Outside of that he has mostly been reviewing and giving feedback on merge requests.
He also helped Sung with understanding the API.

Just: Created a basic authentication controller on client back-end  which forms a request to verify user authentication. Tested all controllers using a mock and did the checkstyle report.


## Main problems Encountered

None.


## Adjustments from previous sprints



## Adjustments for next sprint

Sung: I will leave explanations and documentations for communication within the group for new codes I add to the project.
