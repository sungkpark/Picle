Discussion about the implementation of lowering house temperature
==================

Since lowering the temperature in your house is something that saves CO2 every day that you've 
lowered the temperature the implementation is probably going to be more complex than we first thought.
For this reason I've come up with a few ideas we could implement this which changes the parameters and other aspects that might need to be covered in the calculation.
Please look through the following proposals and give feedback or maybe add another option that you feel might work better.

## Option 1
The first option is to have the user only input the degrees they put their heating at and the size of their house when they change it.
The implementation would then calculate the amount of degrees below 21 degrees (normal room temperature) this is and use some kind of 
local weather source to check if the heating is on each day. Then the program would either add score to the activity each day that the you actually save CO2
or create a new activity each day that you save CO2.

This implementation is probably the most user-friendly since the user only needs to give data when they change the temperature.
The implementation is probably the most difficult though because I don't know the viability of having the program that checks local weather to see if the heating is on
and because the server would need to automatically calculate the values each day and add them to an activity on create an activity on itself.

#Input parameters
1. House temperature (degrees celsius)
2. Size of house (m^2)


## Option 2
The second option is to not use a weather checking program and just calculate and average amount of days a year that it's warmer than 21 degrees and use that as a
multiplier to calculate a value that's just added each day no matter what.

This makes the implementation easier and is also just as user-friendly. It does however make the calculation more off since warmer countries 
and locations have a lot more days above 21 degrees and would therefor have different values. It also makes the system much more exploitable since you could, for instance,
set the heating to 0 degrees in the summer to get a lot of points even though you're not saving CO2 cause the heating wouldn't have been on anyway.

#Input parameters
1. House temperature (degrees celsius)
2. Size of house (m^2)


## Option 3
The third option is to have the user fill-in for how long they have had the heating on a certain temperature and how many days it was below 21 degrees outside.

This would make calculation just returning a simple value and adding the score in one go. It is however less user-friendly because 
the user needs to keep track of the temperature each day in order to get points and needs to keep track of the amount of days that have passed.

#Input parameters
1. House temperature (degrees celsius)
2. Size of house (m^2)
3. Amount of days passed (days)
4. Amount of days it was below 21 degrees outside (days)


## Option 4
The last option I came up with is to have the user add an activity each day that the heating is on which the server will then just add points for based on a simple calculation
like Constant X (21 - temperature).

This will be the easiest to implement but is probably also the most user-unfriendly option because they get a forced daily task and will become a repetitive 
annoyance if you just gotta fill the same things each day just to get points.


#Input parameters
1. House temperature (degrees celsius)
2. Size of house (m^2)
