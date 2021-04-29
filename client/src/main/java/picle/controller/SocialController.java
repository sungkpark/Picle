package picle.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.common.SocialErrorHandler;
import picle.model.Friend;
import picle.model.FriendList;
import picle.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the /social api.
 */
public class SocialController {

    private RestTemplate restTemplate;

    /**
     * Constructor sets up rest template to automatically authenticate for each request.
     */
    public SocialController(User user) {
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(user);
        this.restTemplate = new RestTemplateBuilder()
                .additionalInterceptors(authInterceptor)
                .errorHandler(new SocialErrorHandler())
                .build();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Performs API GET call to /social to get list of friends.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public List<Friend> getFriends() {
        String reqUrl = Configuration.getInstance().url + "/social";
        ResponseEntity<FriendList> res = restTemplate.getForEntity(
                reqUrl, FriendList.class);
        List<Friend> friends = new ArrayList<>();
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                friends = res.getBody().getFriends();
            }
        }
        return friends;
    }

    /**
     * Performs API GET call to /social/{friendName} to get a single activity.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public Friend getFriend(String friendName) {
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friendName);
        ResponseEntity<Friend> res = restTemplate.getForEntity(reqUrl, Friend.class);
        Friend friend = null;
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                friend = res.getBody();
            }
        }
        return friend;
    }


    /**
     * Performs API POST call to /social to add a friend.
     * Note that the BasicAuthInterceptor used in the constructor
     * sets the username and password.
     */
    public boolean addFriend(Friend newFriend) {
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(newFriend.toJsonString() ,headers);

        ResponseEntity<String> res = restTemplate.postForEntity(reqUrl,
                entity, String.class);

        return res.getStatusCode() == HttpStatus.CREATED;
    }


    /**
     * Performs API DELETE call to /social/{friendName} to delete the friend with that username.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public void deleteFriend(String friendName) {
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friendName);
        restTemplate.delete(reqUrl);
    }
}
