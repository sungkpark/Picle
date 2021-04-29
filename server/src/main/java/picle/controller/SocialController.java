package picle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import picle.entity.Friend;
import picle.entity.FriendList;
import picle.service.SocialService;

import java.util.Collection;

/**
 * Handles HTTP methods.
 */
@RestController
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    /**
     * GET method to get all friends for a user.
     * http://localhost:8080/social
     * @return a list of all friends.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<FriendList> getAllFriends(
            @RequestAttribute("X-Picle-Username") String username) {
        Collection<Friend> allFriends = socialService.getAllFriends(username);
        if (allFriends == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FriendList list = new FriendList(allFriends);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET method to get a single friend.
     * http://localhost:8080/social/{friendUsername}/
     * @param friendUsername identifies which friend to get
     * @return a single friend.
     */
    @RequestMapping(value = "/{friendUsername}", method = RequestMethod.GET)
    public ResponseEntity<Friend> getFriendByUsername(
            @PathVariable("friendUsername") String friendUsername,
            @RequestAttribute("X-Picle-Username") String username) {
        Friend friend = socialService.getFriendByUsername(username, friendUsername);
        if (friend == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    /**
     * DELETE method to delete a single friend of a user.
     * http://localhost:8080/social/{friendUsername}
     * @param friendUsername identifies friend to delete.
     */
    @RequestMapping(value = "/{friendUsername}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFriendByUsername(
            @PathVariable("friendUsername") String friendUsername,
            @RequestAttribute("X-Picle-Username") String username) {
        boolean flag = socialService.deleteFriendByUsername(username, friendUsername);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST method to add a new friend to the user.
     * http://localhost:8080/social/
     * This method needs a JSON value passed on in the form of Friend.
     * Client passes in the params required which are the fields of Friend.
     * @param friend (friendName)
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFriend(
            @RequestBody Friend friend,
            @RequestAttribute("X-Picle-Username") String username) {
        boolean flag = socialService.addFriend(username, friend);
        if (!flag) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This friend has been added", HttpStatus.CREATED);
    }

}
