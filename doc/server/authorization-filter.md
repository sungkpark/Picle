#Authorization through Spring Filters
An implementing class to the Spring interface ```Filter``` has been built that:
1. Wraps the incoming request using the ```HTTPServletRequestWrapper```
2. Gets the content of the "Authorization" header
3. Decodes the string and checks the validity of the supplied credentials
4. Either:
   * Discontinues the FilterChain if the credentials are invalid, returning a ```401 Unauthorized ``` HTTP response
   * Passes the response and request, unaltered, to the rest of the FilterChain*


**Changes to be made before the final release:** 
* The UserDao instance the Filter relies on to get User credentials is currently Autowired to the FakeUserDaoImpl class. This needs to be rewired to the MariaDB version.
* In the instance that more endpoints are added to controllers, those endpoints also need to be added to the configuration of the AuthorizationFilter under AuthorizationConfiguration, as the declaration of URL extensions can only be done additively. 
    * This is done in line 20: ```filterRegistrationBean.addUrlPatterns("/user/*","/activity/*");```, and as such currently runs on all extensions of "/user" and "/activity", but not on "/register"

_*Note that the FilterChain exists with or without AuthorizationFilter in it, and naturally leads to the request being delivered to the Controller class._

##References for basics:
* https://www.baeldung.com/securing-a-restful-web-service-with-spring-security (uses cookies)
* https://www.baeldung.com/spring-security-custom-filter
* https://www.baeldung.com/spring-boot-add-filter
* https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html


