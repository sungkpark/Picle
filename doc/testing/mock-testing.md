Mock Testing Guide
==================

__Note:__ As of the first version of this document, this is not a fully comprehensive guide to mocking/mock testing, but a guide to get you started. This could change in the future according to the team's needs.

## What is a mock?
"Mocking" is an approach to unit testing where classes, objects or sometimes entire environments outside of the class or code that is being tested are made to work to our needs. This is useful for:
* Helping isolate the code we want to run unit tests on.
* Giving the tester control of external variables.
* Reducing the complexity of the tests (unit tests are way faster than integration tests – we will get there). 

## Mockito
Mockito is a mock framework that is often used in conjunction with unit testing frameworks, and is integrated in Spring.
We will be using methods from the Mockito API for basic mocking (mocking environments, web endpoints etc. are done outside of Mockito).

In the following short sections the following class _MyList_ will be used as the collaborator to be mocked:
```java
public class MyList extends AbstractList<String> {
    @Override
    public String get(int index) {
        return null;
    }
 
    @Override
    public int size() {
        return 1;
    }
}
```

### Simple mocking with Mockito (example):
The simplest variant of the _mock_ method has one parameter, and that is the class to be mocked. The definition of the method looks like:
``` java
public static <T> T mock(Class<T> classToMock)
```
This would be applied to our class _MyList_ as:
``` java
MyList listMock = mock(MyList.class);
```
And then, the behaviour of this instance can be defined as such:
``` java
when(listMock.add(anyString())).thenReturn(false);
```
At this point, the instance `listMock` of the class _MyList_ will act in the way we set it up to act, meaning when the add method is called with it (with anyString passed as its input), the method will return the boolean value false.

__Note:__ _The class we are currently mocking is pretty simple, but imagine mocking a class with complicated functionality to have a method always return `true` or `3` because that would be the correct or expected output of said methods. _That_ is the reason we would mock stuff._

In this example, we would then call the mocked method with the mocked object as such:
``` java
boolean added = listMock.add(randomAlphabetic(6));
```
and expect the behaviour we just defined.

 To test that, we can write:
 ``` java
 verify(listMock).add(anyString());
 assertThat(added, is(false));
 ```
 * `verify` is a clause supplied by Mockito that verifies (wow!) that the method `add` has been called on the object `listMock` (more on verify later).
 * `assertThat` is simply a way to create unit test assertions; in this case we assert that the variable added, which stores the output of `listMock.add(randomAlphabetic(6))`, is equal to `false`.

### `verify` and `assertThat`
####`verify` 
in Mockito can be called an assertion that the method of a mock object has been called (with that object).
This is how it was used in the example above:
``` java
 verify(listMock).add(anyString());
 ```
 In the context of the example, the condition would hold, and the test would pass.
 
 `verify` can also be used to verify the _number of times_ of which that method was called, in which case the method has two parameters used as such:
 ``` java
 verify(listMock, times(3)).add(anyString());
 ```
 This code simply cares if the add method is called _three times_.
 
####`assertThat`
in JUnit is a flexible assertion method that lets you assert equalities, inequalities, either-or cases, set membership etc. It's nice. Look it up.

### Naming your mock objects
There exists a `mock` method with two parameters, in which the second attribute passed is a String to act as the name of your mock object. This functionality apparently comes in handy when debugging/reading error messages (to be honest I don't exactly understand why the given name of the object would not act this way).

Here is an example:
``` java
MyList listMock = mock(MyList.class, "martijn");
```
Now the object is called martijn, because why not.

_The clearest example I've found online use "a JUnit implementation of the __testRule__ interface, called __ExpectedException__". I don't know exactly how that works, so it won't be in this doc for now._

This named object could produce (hypothetically) an exception message could look like:
```
org.mockito.exceptions.verification.TooLittleActualInvocations:
martijn.add(<any>);
Wanted 2 times:
at com.baeldung.mockito.MockitoMockTest
  .whenUsingMockWithName_thenCorrect(MockitoMockTest.java:...)
but was 1 time:
at com.baeldung.mockito.MockitoMockTest
  .whenUsingMockWithName_thenCorrect(MockitoMockTest.java:...)
```


## Building a simple test using Mockito in Spring

### `@Mock` annotation
If written within the test class before the class to be mocked and the name of the object, this annotation will create a mock object usable in any tests within the class:
```
public class SomeMockTest { 
    @Mock
    MyList listMock;
    ...
```
The behaviour of this mock object can then be determined in each individual test case:
```
@Test
public void testAddMethod() {
    when(listMock.add(anyString())).thenReturn(false);
    ...
```
The code above is equivalent to what was shown on the simple example in the beginning.

### Building a test
Writing a JUnit test _with_ Mockito is almost the same as doing it without. In any case, here is a simple example of one:
```java
import org.junit.*;
import org.mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SomeBusinessMockAnnotationsTest {

	@Mock
	MyList listMock;

	@Test
	public void testSomeFunctionality() {
	//...
	}
}
```
Within the test, one would use `when` clauses and other afformentioned unit testing tools such as assertions and `verify` to create the individual test. 
Of course one could aso bypass the `@Mock` annotation and create the mock object within the test too.

## Documentation
### In this doc:
1. [Basic example from](https://www.baeldung.com/mockito-mock-methods)
2. [__VERY__ basic guide to mock and unit testing in case youre lost](http://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit)
### Further reading:
_The links here are for the curious and the brave. I think none of these even talk about actual simple mock testing._
1. [A more general outtake of testing in Spring](https://www.baeldung.com/spring-boot-testing)
2. [Probably the testing bible of Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)



 