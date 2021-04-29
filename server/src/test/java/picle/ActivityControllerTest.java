package picle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.controller.ActivityController;
import picle.entity.Activity;
import picle.entity.ActivityList;
import picle.entity.PostActivity;
import picle.service.ActivityService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ActivityControllerTest {

    private Activity one;
    private Activity two;
    private PostActivity postTwo;
    private Collection<Activity> activities;
    private ActivityList allActivities;

    @Mock
    private ActivityService mockActivityService;

    @InjectMocks
    private ActivityController activityController;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    private String username = "username";

    @Before
    public void setup() {
        one = new Activity(1, 1, 1, 1, 1);
        two = new Activity(2, 2, 2, 2, 2);
        postTwo = new PostActivity(2, 2, 2);
        activities = new ArrayList<>();
        activities.add(one);
        allActivities = new ActivityList(activities);
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(activityController).build();
    }

    @Test
    public void getActivityByExistingIdTest() throws Exception {
        when(mockActivityService.getActivityById(1, username)).thenReturn(one);
        this.mvc.perform(get("/activity/1").requestAttr("X-Picle-Username", username))
                .andExpect(content().json("{\"activityId\":1,\"activityTypeId\":1,\"param1\":1,\"param2\":1,\"score\":1}"));
    }

    @Test
    public void getNonExistingActivityIdTest() throws Exception {
        when(mockActivityService.getActivityById(5, "username")).thenReturn(null);
        this.mvc.perform(get("/activity/5").requestAttr("X-Picle-Username", username)).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllActivitiesTest() throws Exception {
        activities.add(two);
        allActivities.setActivities(activities);
        when(mockActivityService.getAllActivities(username)).thenReturn(new HashSet<>());
        this.mvc.perform(get("/activity").requestAttr("X-Picle-Username", username))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"activities\": []}"));
    }

    @Test
    public void getAllActivitiesFailTest() throws Exception {
        when(mockActivityService.getAllActivities(username)).thenReturn(null);
        this.mvc.perform(get("/activity").requestAttr("X-Picle-Username", username))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getEmptyAllActivitiesTest() throws Exception {
        activities.remove(one);
        allActivities.setActivities(activities);
        when(mockActivityService.getAllActivities(eq(username))).thenReturn(activities);
        this.mvc.perform(get("/activity").requestAttr("X-Picle-Username", username)).andExpect(status().isOk());
    }

    @Test
    public void deleteByExistingIDTest() throws Exception {
        when(mockActivityService.deleteActivityById(1)).thenReturn(true);
        this.mvc.perform(delete("/activity/1")).andExpect(content().string("Activity ID 1 has been deleted")).andExpect(status().isOk());
    }

    @Test
    public void deleteByNonExistingIDTest() throws Exception {
        when(mockActivityService.deleteActivityById(2)).thenReturn(false);
        this.mvc.perform(delete("/activity/2")).andExpect(content().string("This activity id does not exist")).andExpect(status().is4xxClientError());
    }

    @Test
    public void addActivityTest() throws Exception {
        String json = mapper.writeValueAsString(postTwo);

        when(mockActivityService.addActivity(anyObject(), eq("username"))).thenReturn(true);
        this.mvc.perform(post("/activity").requestAttr("X-Picle-Username", username).contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void addActivityFailTest() throws Exception {
        String json = mapper.writeValueAsString(postTwo);
        when(mockActivityService.addActivity(anyObject(), eq("failUser"))).thenReturn(false);
        this.mvc.perform(post("/activity").requestAttr("X-Picle-Username", "failUser").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isBadRequest()).andExpect(content().string("Failed"));
    }

    @Test
    public void updateExistingActivityIdTest() throws Exception {
        String json = this.mapper.writeValueAsString(postTwo);

        when(mockActivityService.updateActivityByActivityId(anyInt(), anyObject(), anyString())).thenReturn(true);
        this.mvc.perform(put("/activity/1").requestAttr("X-Picle-Username", username).contentType(MediaType.APPLICATION_JSON_VALUE).content(json).header("X-Picle-Username", "username")).andExpect(status().isOk());
    }

    @Test
    public void updateNonExistingActivityIdTest() throws Exception {
        String json = mapper.writeValueAsString(postTwo);

        when(mockActivityService.updateActivityByActivityId(anyInt(), anyObject(), eq("failUser"))).thenReturn(false);
        this.mvc.perform(put("/activity/5").requestAttr("X-Picle-Username", "failUser").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isBadRequest()).andExpect(content().string("This activity id doesn't exists"));
    }
}
