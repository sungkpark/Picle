# picle

_Go Green, Go Picle_

People Interactively Creating a Liveable Environment, or PICLE for short, is an application
which aims to stimulate people to live a more earth friendly life by gamification.

The application consists of a central server and a client.

## Compilation
The server and client `.jar` executable files are compiled using maven:
```
mvn package
```
Then you will find the `.jar` executables in `client/target/` and `server/target`
respectively.

## Running the client
Simply execute the `.jar` file in `client/target/` to launch the client. Do note that
you should not move the client jar file, because then it will crash because it can't
find the intro video.

## Running the server
Simply execute the `.jar` file in `server/target/` to launch the server. If it is the
first time you run the PICLE server, you need to prepare a database as described in
`doc/sql`. We use MariaDB as our SQL server. You might also need to edit the
`DatabaseController.java` and the corresponding test to make sure that the PICLE server
uses the correct database credentials.

## Organizational

| Name | netid |
|------|:-----:|
| Achilleos, Andreas |
| Capanoglu, Alp | 
| Koemans, Jordy | 
| Omar Samir Abdelmoaty Mostafa Sheasha, Omar |
| Park, Sung |
| Staal, Martijn | 
| van der Veeken, Just |

## Personal Development Plans

### Andreas
My main learning goals for this course are:
* Learn to work efficiently in a group environment by dividing workloads according to personal strengths and previous experiences.
* Further deepen my knowledge in software engineering since I have minimal prior knowledge.
* Work with tools as git to structure our team developing plan and spread the amount of work to each one of our members.
* Get accustomed with presenting my ideas and current progress in a group of developers to get feedback and suggestions.
* I want to aim for an 8 or higher for our project.

Having experienced very different project management and programming scenarios in the past, this course will provide me with a lot more
tools to use in my future career as a computer scientist and translate this to a real world working environment.

I will be trying to achieve my goals by:
* Researching unknown tools and methods on my own so I can provide what my team expects from me.
* Be present in all team meetings and participate in all group debates.
* Meet all deadlines and keep up with the team's level.
* Accept all feedback from my teamates and work towards achieving a result we are all pleased with.
* Using all available resources that the course administration team provides along with the help of some more experienced team members in some sectors.


### Alp
My main learning goals for this course are:
* I expect to learn about time and project management techniques.
* I wish to better my ability to work continuously in a structured and timely manner.
* I wish to learn to use tools used in the world of software engineering to their basic or intermediate extents.
* I wish to receive a grade of 8 or above from this class.

As someone with minimal prior experience both in terms of programming and that of project management, I expect the OOP Project to cast me outside of my comfort zone time and again. I have so far kept up with the bachelors well and progressed far, but I have much to learn still. It is important to me to grasp the skills necessary to perform in a real world environment.

I will be trying to achieve my goals by:
* Using resources given by the course to educate myself on tools and protocols. I have no trouble asking people for help or guidance, which I expect I will have to do often.
* Doing research on my own time into relevant topics throughout the project to make sure I can keep up and learn relevant skills in the process.
* Doing my part as a team member be it simple tasks like arriving to meetings in time or complex ones like producing good code and participating in organization.


### Jordy
My main learning goals for this course are:
* Learn how to efficiently work in a group and how to divide the work efficiently.
* Learn how to plan partial deadlines to create a planning that will help reach the final product succesfully in time.
* Get more used to using java and obtain more knowledge about programming in general
* Learn what steps to follow to succesfully implement a complete application.

As I've had some experience with both programming and group management I have noticed that I tend to get overwhelmed by the size of project which leads
me to losing the oversight in how to start and continue untill the project is finished. I expect that this course will help me get a good feeling of how this is
done so I can use this is following projects and in potential career paths.

I will be trying to achieve my goals by:
* Studying the course material and the lectures to get a good basic knowledge of the subject matter
* Looking up information when I feel I'm not understanding something well enough or aksing for help if feel like I need it.
* Listening to feedback from my teammembers to help me do my part of the project better.
* Working hard to do my part in the project and help others as I hope they'll help me.


### Omar
My main learning goals for this course are:
* Learning how to coherently cooperate in a team
* Learn how to present reports and ideas effectively and appropriately
* Learn how all the different components play a role in reaching our goal

This is important as it is one of the many obstacles I will face when tackling a real job and a great learning opportunity for that as it is something I have never faced or prepared for.

I will have succeeded when I am able to finish my tasks sufficiently on time,
work on tasks that might not be of my interest but compromise to aid in
reaching our goal, and able of effective communication within a team. 

I will be trying to achieve these goals by:
* taking all criticism and feedback from my teammates
* learning to tackle tasks which are not of my desire
* studying and reading online on how all the pieces should fit together
* not holding back on reaching out for help as well as offering help when possible
* finishing tasks on their desired time


### Sung
My main learning goals for this course are:
* See how a large project is done, as this is my first time doing it.
* Work in a part of a team effectively
* Learn what to code and find and use whatever resource is out there for use

All of these goals are goals for a having good teamwork. These are important as working in a team means that there are responsibilities that should not be let down for the group to work out, and that is my main goal.

I will be seeing progress in my goal, I guess, somewhere around when we are getting half way through our project. We might struggle in the beginning because it is our first time but as we work for a bit longer, I think we will be able to see that we are working as a team and we have a bit of result.

I will be trying to achieve these goals by:
* Listening and having good ideas spoken well to the group
* Keep up to tasks
* Research whatever I can do more in
* Receive and give feedbacks
* Be active in discussions and brainstorming


### Martijn
My main learning goals for this course are:
* How to work in a group in an effective way.
* Design and implement an application based on given requirements.
* Get used with technologies like git, maven, etc.
This all is not only for my professional development as a computer scientist, but also
for the more short term effectiveness in coming group projects in other courses and
for hobby projects with friends.

I think that I will have succeeded in achieving this goal if I get a good grade for this
project, while maintaining a good working environment within the group.

I will be trying to achieve these goals by:
* being on time for all our meetings
* meeting deadlines
* trying to help my groupmates whenever I can
* asking for help on time, not last minute
* sharing knowledge
* doing research

### Just
My main learning goals for this course are:
* Learn how to turn an idea into a concrete software product.
* Learn how to work together in a team.
* Learn to find a balance in keeping all team members satisfied
  with their tasks while still matching all formal requirements.

This is important because the project reflects a professional setting and provides us with the
necessary experience to collaborate with other software engineers in the future.

I will have succeeded when we succesfully created the desired product
in such a way that all team members have experienced it to be an effective team effort.

I will be trying to achieve these goals by:
* Being supportive towards, and asking support from, team mates. 
* Making sure everyone on the team gets to say what is on his mind.
* Asking for feedback from the other team members.
* Passing deadlines.
* Studying the necessary concepts. 


