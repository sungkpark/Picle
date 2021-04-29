This is a model I used to structure this project. It contains 4 layers which interact from each adjacent layer to another.

![N-tier architecture](doc/pictures/N-tier architecture.png)

The first layer is the database layer where the all the datas are stored in a database. We will be using MariaDB for this. This layer will be accessed by the DAO layer which queries or modifies the data here, then after the job is done, it returns the data access back.

The second layer is the DAO (data access object) layer, used for accessing the database from the database layer (e.g. implementing queries).This layer is called by the service layer and returns the requested method called back to be processed with the business logic.

The third layer is the service layer where the business logic is implemented (e.g. calculating score).

The fourth layer is the Controller layer where all HTTP request methods (such as GET, PUT, …) are implemented to communicate with the client. When a HTTP request is called, the controller layer calls the method to the service layer, then after everything is processed along with the business logic, it retrieves a message back to send back to the client.

Having layers make the structure much more organized by separating each functions into layers.

The youtube tutorial I used for a motivation is this -> https://www.youtube.com/watch?v=Ke7Tr4RgRTs