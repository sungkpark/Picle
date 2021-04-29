package picle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.controller.ActivityController;
import picle.controller.SocialController;
import picle.entity.*;
import picle.service.ActivityService;
import picle.service.SocialService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class SocialControllerTest {

    private Friend one;
    private Friend two;
    private Collection<Friend> friends;
    private FriendList allFriends;

    @Mock
    private SocialService mockSocialService;

    @InjectMocks
    private SocialController socialController;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    private String username = "username";

    @Before
    public void setup() {
        one = new Friend("friend1");
        two = new Friend("friend2");
        friends = new ArrayList<>();
        friends.add(one);
        allFriends = new FriendList(friends);
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(socialController).build();
    }

    @Test
    public void getFriendByUsernameSuccess() throws Exception {
        when(mockSocialService.getFriendByUsername(username, "friend1")).thenReturn(one);
        this.mvc.perform(get("/social/friend1").requestAttr("X-Picle-Username", username))
                .andExpect(content().json("{\"username\":\"friend1\"}"));
    }

    @Test
    public void getFriendByUsernameFailure() throws Exception {
        when(mockSocialService.getFriendByUsername(username, "friend5")).thenReturn(null);
        this.mvc.perform(get("/social/friend5").requestAttr("X-Picle-Username", username)).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllFriendsSuccessTest() throws Exception {
        friends.add(two);
        allFriends.setFriends(friends);
        when(mockSocialService.getAllFriends(username)).thenReturn(friends);
        this.mvc.perform(get("/social").requestAttr("X-Picle-Username", username))
                .andExpect(content().json("{\"friends\":[{\"username\":\"friend1\"},{\"username\":\"friend2\"}]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFriendsFailureTest() throws Exception {
        when(mockSocialService.getAllFriends(username)).thenReturn(new ArrayList<>());
        this.mvc.perform(get("/social").requestAttr("X-Picle-Username", username))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"friends\":[]}"));
    }

    @Test
    public void deleteByUsernameSuccess() throws Exception {
        when(mockSocialService.deleteFriendByUsername(username,"friend1")).thenReturn(true);
        this.mvc.perform(delete("/social/friend1").requestAttr("X-Picle-Username", username)).andExpect(status().isOk());
    }

    @Test
    public void deleteByUsernameFailure() throws Exception {
        when(mockSocialService.deleteFriendByUsername(username, "friend5")).thenReturn(false);
        this.mvc.perform(delete("/social/friend5").requestAttr("X-Picle-Username", username)).andExpect(status().is4xxClientError());
    }

    @Test
    public void addFriendSuccessTest() throws Exception {
        String json = mapper.writeValueAsString(one);

        when(mockSocialService.addFriend(eq(username), anyObject())).thenReturn(true);
        this.mvc.perform(post("/social").requestAttr("X-Picle-Username", username).contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void addFriendFailureTest() throws Exception {
        String json = mapper.writeValueAsString(one);

        when(mockSocialService.addFriend(eq(username), anyObject())).thenReturn(false);
        this.mvc.perform(post("/social").requestAttr("X-Picle-Username", username).contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isBadRequest());
    }

}
