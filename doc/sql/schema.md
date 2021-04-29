Database scheme
===============

__Note:__ Please keep in mind that all integer types specified here are based on preliminary assumptions and hence open to change.

The score is the amount of CO2(-equivalent) saved by the specified activity based on
the activity type and the parameters.

## users

| column | type | constraints
| -------| --- | ---
| `user_id` | `int` | `NOT_NULL PRIMARY KEY` |
| `username` | `varchar(32)` | `UNIQUE` |
| `hashed_password` | `varchar(256)` | |
| `total_score` | `int` | |
| `level` | `int` | |


### Query
```
CREATE TABLE users (
    user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(32) NOT NULL,
    hashed_password varchar(256) NOT NULL,
    total_score int NOT NULL,
    level int NOT NULL,
    UNIQUE KEY username (username)
);
```

## activity_types (open to name suggestions; descriptions?)

| column | type | constraints |
| --- | --- | --- |
| `activity_type_id` | `int` | `PRIMARY KEY` |
| `activity_type_name` | `varchar(64)` | |

### Query
```
CREATE TABLE activity_types (
    activity_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    activity_type_name varchar(64) NOT NULL
);
```

### Table data

| activity_name | activity_id |
| --- | --- |
| `vegetarian_meal` | 1 |
| `local_produce` | 2 |
| `using_bike` | 3 |
| `public_transport` | 4 |
| `lowering_temperature` | 5 |
| `solar_panels` | 6 |

## activities

| column | type | constraints |
| --- | --- | --- |
| `activity_id` | `int` | `PRIMARY KEY` |
| `user_id` | `int` | `FOREIGN KEY` |
| `activity_type_id` | `int` | `FOREIGN KEY` |
| `param0` | `int` | |
| `param1` | `int` | |
| `score` | `int` | |
| `timestamp` | `timestamp` | |

### Query
```
CREATE TABLE activities (
    activity_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    activity_type_id int NOT NULL,
    param0 int,
    param1 int,
    score int NOT NULL,
    time timestamp,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

## friends

| column | type | constraints |
| --- | --- | --- |
| `friendship_id` | `int` | `PRIMARY KEY` |
| `user_id` | `int` | `FOREIGN KEY` |
| `friend_id` | `int` | `FOREIGN KEY` |

Note: This table represents a many to many friends relationship between users.

### Query
```
CREATE TABLE friends (
    friendship_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    friend_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

#### Possible parameters of activities

Eating a vegetarian meal
* Parameter: servings (portions)

Buying a local produce
* Parameter: amount of products (number)

Using bike instead of a car
* Parameter: distance (km)

Using public transport instead of a car
* Parameter: distance (km)

Lowering the temperature of your home
* Parameter: temperature (celsius)
* Parameter: size (m^2)

Installing solar panels
* Parameter: energy produced per month (J)


## Achievements
In order to stimulate the user to do more CO2 saving activities we aim for a minimalistic approach.
Therefore this will be contained in existing tables. 

The users table is altered with a new column which keeps track of the level of the user.
In order to reach a certain level a user should save more than a specific amount of CO2.
We define these amounts to be: 1000kg for level 1, then 10.000kg, 20.000kg, 30.000kg etc. 

Upon reaching such an amount of CO2 savings the level of the user will be increased.
Furthermore, the user gets 10% of the CO2 they have saved added to their current savings. 
In order to show this to the user a new activity is automatically added (without parameters) and the additional CO2 savings they earned by reaching that level as the score.
This way reaching a new level will show up on the dashboard of the user under activities.

