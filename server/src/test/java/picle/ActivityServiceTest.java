package picle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;
import picle.dao.ActivityDao;
import picle.dao.ScoreDao;
import picle.entity.Activity;
import picle.entity.PostActivity;
import picle.entity.Score;
import picle.service.ActivityService;
import picle.service.ScoreService;

import java.util.ArrayList;
import java.util.Collection;

public class ActivityServiceTest {

    @Mock
    private ActivityDao activityDao;

    @Mock
    private ScoreDao scoreDao;

    @InjectMocks
    private ActivityService activityService;

    private Collection<Activity> expectedActivities = new ArrayList<>();
    private Activity one = new Activity(1, 1, 1, 1, 10);
    private PostActivity three = new PostActivity(3, 1, 1);
    private String username = "username";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activityService = new ActivityService(activityDao);
        expectedActivities.add(one);
    }

    private void assertActivityId1Fields(Activity id1) {
        Assert.assertEquals(1, id1.getActivityId());
        Assert.assertEquals(1, id1.getActivityTypeId());
        Assert.assertEquals(1, id1.getParam1());
        Assert.assertEquals(1, id1.getParam2());
        Assert.assertEquals(10, id1.getScore());
    }

    @Test
    public void getAllActivitiesTest() {

        given(activityDao.getAllActivities(username)).willReturn(expectedActivities);

        Collection<Activity> allActivities = activityService.getAllActivities(username);

        Assert.assertEquals(1, allActivities.size());
        Assert.assertEquals(expectedActivities, allActivities);
    }

    @Test
    public void getActivityByIdTest() {

        given(activityDao.getActivityById(1, username)).willReturn(one);

        Activity id1 = activityService.getActivityById(1, username);

        Assert.assertTrue(id1.equals(((ArrayList<Activity>) expectedActivities).get(0)));

        assertActivityId1Fields(id1);
    }

    @Test
    public void deleteActivityByExistingIdTest() {
        // given
        expectedActivities.remove(one);

        // when
        when(activityDao.getAllActivities(username)).thenReturn(expectedActivities);
        when(activityDao.deleteActivityById(1)).thenReturn(true);
        boolean actual = activityService.deleteActivityById(1);
        Collection<Activity> result = activityService.getAllActivities(username);

        //then
        Assert.assertEquals(true, actual);
        Assert.assertEquals(expectedActivities, result);
    }

    @Test
    public void deleteActivityByNonExistingIdTest() {
        // when
        when(activityDao.getAllActivities(username)).thenReturn(expectedActivities);
        when(activityDao.deleteActivityById(2)).thenReturn(false);
        boolean actual = activityService.deleteActivityById(2);
        Collection<Activity> result = activityService.getAllActivities(username);

        // then
        Assert.assertEquals(false, actual);
        Assert.assertEquals(expectedActivities, result);
    }

//    @Test
//    public void addActivityTest() {
//        // given
//        Activity activityThree = new Activity(1, three.getActivityTypeId(), three.getParam1(), three.getParam2(), 10);
//        expectedActivities.add(activityThree);
//
//        // when
//        when(activityDao.getAllActivities(username)).thenReturn(expectedActivities);
//        when(activityDao.addActivity(any(), anyString(), anyInt())).thenReturn(true);
//        when(scoreDao.getScoreByUsername("username")).thenReturn(new Score("username", 0, 0));
//        when(scoreDao.updateScoreByUsername(anyString(), anyInt(), anyInt())).thenReturn(true);
//        boolean actual = activityService.addActivity(three, username);
//        Collection<Activity> result = activityService.getAllActivities(username);
//
//        // then
//        Assert.assertEquals(true, actual);
//        Assert.assertEquals(expectedActivities, result);
//
//    }

    @Test
    public void updateActivityWithExistingIdTest() {
        // given
        Activity activityThree = new Activity(1, three.getActivityTypeId(), three.getParam1(), three.getParam2(), 10);
        expectedActivities.remove(one);
        expectedActivities.add(activityThree);

        // when
        Mockito.when(activityDao.getAllActivities(username)).thenReturn(expectedActivities);
        Mockito.when(activityDao.updateActivityByActivityId(anyInt(),any(), anyString(), anyInt())).thenReturn(true);
        boolean actual = activityService.updateActivityByActivityId(1, three, username);
        Collection<Activity> result = activityService.getAllActivities(username);

        // then
        Assert.assertEquals(true, actual);
        Assert.assertEquals(expectedActivities, result);
    }

    @Test
    public void updateActivityWithNonExistingIdTest() {
        // when
        Mockito.when(activityDao.getAllActivities(username)).thenReturn(expectedActivities);
        Mockito.when(activityDao.updateActivityByActivityId(anyInt(),any(), anyString(), anyInt())).thenReturn(false);
        boolean actual = activityService.updateActivityByActivityId(3, three, username);
        Collection<Activity> result = activityService.getAllActivities(username);

        // then
        Assert.assertEquals(false, actual);
        Assert.assertEquals(expectedActivities, result);
    }

}
