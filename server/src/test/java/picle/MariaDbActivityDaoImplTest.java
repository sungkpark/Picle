package picle;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import picle.dao.MariaDbActivityDaoImpl;
import picle.entity.Activity;
import picle.entity.PostActivity;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import static org.mockito.Mockito.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;


@RunWith(SpringRunner.class)
public class MariaDbActivityDaoImplTest {

    private EmbeddedDatabase db;
    private Activity baseActivity1;
    private Activity baseActivity2;

    @Mock
    DatabaseController dbController;

    @Mock
    DatabaseControllerBuilder dcBuilder;


    @InjectMocks
    MariaDbActivityDaoImpl mariaDBActivityDao;

    private EmbeddedDatabase edbBuilder() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
        try {
            Connection conn = db.getConnection();
            Statement setupStm = conn.createStatement();
            setupStm.execute("CREATE TABLE users ("
                    + "user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "username varchar(32) NOT NULL,"
                    + "hashed_password varchar(256) NOT NULL,"
                    + "total_score int NOT NULL,"
                    + "UNIQUE KEY username (username));");
            setupStm.execute("CREATE TABLE activity_types ("
                    + "activity_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "activity_type_name varchar(64) NOT NULL);");
            setupStm.execute("CREATE TABLE activities ("
                    + "activity_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "user_id int NOT NULL,"
                    + "activity_type_id int NOT NULL,"
                    + "param0 int,"
                    + "param1 int,"
                    + "score int NOT NULL,"
                    + "time timestamp,"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score) VALUES (1, 'user', 'password', 0);");
            setupStm.execute("INSERT INTO activities (activity_id, user_id, activity_type_id, param0, param1, score) VALUES (1, 1, 1, 10, 100, 1000);");
            setupStm.execute("INSERT INTO activities (activity_id, user_id, activity_type_id, param0, param1, score) VALUES (2, 1, 1, 20, 50, 345);");
            setupStm.execute("INSERT INTO activity_types (activity_type_id, activity_type_name) VALUES (1, 'best activity');");
        } catch(Exception e){
            System.out.print("Problem occurred whilst initializing in-memory test database.");
        }
        return db;
    }

    private EmbeddedDatabase emptyEdbBuilder() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
        return db;
    }

    private EmbeddedDatabase emptyTableEdbBuilder() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
        try {
            Connection conn = db.getConnection();
            Statement setupStm = conn.createStatement();
            setupStm.execute("CREATE TABLE users ("
                    + "user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "username varchar(32) NOT NULL,"
                    + "hashed_password varchar(256) NOT NULL,"
                    + "total_score int NOT NULL,"
                    + "UNIQUE KEY username (username));");
            setupStm.execute("CREATE TABLE activity_types ("
                    + "activity_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "activity_type_name varchar(64) NOT NULL);");
            setupStm.execute("CREATE TABLE activities ("
                    + "activity_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "user_id int NOT NULL,"
                    + "activity_type_id int NOT NULL,"
                    + "param0 int,"
                    + "param1 int,"
                    + "score int NOT NULL,"
                    + "time timestamp,"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE);");
        } catch(Exception e){
            System.out.print("Problem occurred whilst initializing in-memory test database.");
        }
        return db;
    }

    private static Object[] propertyArray(Activity activity) {
        return new Object[] {activity.getActivityId(),
                activity.getUsername(),
                activity.getActivityTypeId(),
                activity.getParam1(),
                activity.getParam2(),
                activity.getScore()};
    }

    @Before
    public void setUp() throws SQLException {
        // creates an HSQL in-memory database populated from default scripts
        // classpath:schema.sql and classpath:data.sql
        baseActivity1 = new Activity(1, "user", 1, 10, 100, 1000);
        baseActivity2 = new Activity(2, "user", 1, 20, 50 ,345);

        db = edbBuilder();
        MockitoAnnotations.initMocks(this);
        when(dcBuilder.buildDatabaseController()).thenReturn(dbController);
        when(dbController.getConn()).thenReturn(edbBuilder().getConnection());
    }

    /*
        The following are tests for correct conditions
     */

    @Test
    public void getAllActivitiesTest() {
        Collection<Activity> allActivities= mariaDBActivityDao.getAllActivities("user");
        Iterator<Activity> returnedActivities = allActivities.iterator();
        if (returnedActivities.next().getActivityId() == 1){
            Assert.assertArrayEquals(propertyArray(baseActivity2), propertyArray(returnedActivities.next()));
        } else {
            Assert.assertArrayEquals(propertyArray(baseActivity1),
                    propertyArray(returnedActivities.next()));
        }
        Assert.assertEquals(2, allActivities.size());

    }

    @Test
    public void addActivityTest() {
        boolean added = mariaDBActivityDao.addActivity(new PostActivity(1, 30, 25), "user", 0);
        Activity returnedActivity = mariaDBActivityDao.getActivityById(3, "user");
        Assert.assertArrayEquals(propertyArray(new Activity(3, "user", 1, 30, 25, 0)),
                propertyArray(returnedActivity));
        Assert.assertTrue(added);
    }

    @Test
    public void deleteActivityTest() {
        boolean deleted = mariaDBActivityDao.deleteActivityById(2);
        Collection<Activity> allActivities= mariaDBActivityDao.getAllActivities("user");
        Iterator<Activity> returnedActivities = allActivities.iterator();
        Assert.assertTrue(deleted);
        Assert.assertEquals(1, allActivities.size());
        Assert.assertArrayEquals(propertyArray(baseActivity1), propertyArray(returnedActivities.next()));
    }

    @Test
    public void updateByIdTest() {
        // TODO currently the method  just returns false; this should be changed in the future
        boolean updated = mariaDBActivityDao.updateActivityByActivityId(
                1, new PostActivity(1, 1, 1), "egh", 99);
        Assert.assertFalse(updated);
    }

    /*
        The following tests test for querying non-existent tuples
     */

    @Test
    public void getNonExistingUserTest() {
        Activity nonExistant = mariaDBActivityDao.getActivityById(15, "user");
        Assert.assertNull(nonExistant);
    }

    @Test
    public void getActivityWrongUserTest() {
        Activity notYourActivity = mariaDBActivityDao.getActivityById(1, "NoSuchUser");
        Assert.assertNull(notYourActivity);
    }

    @Test
    public void getNonExistingAllTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyTableEdbBuilder().getConnection());
        Collection<Activity> allActivities= mariaDBActivityDao.getAllActivities("user");
        Assert.assertEquals(0, allActivities.size());
    }

    @Test
    public void deleteNonExistingActivityTest() {
        // aim for int res = 0 at line 113
        boolean deleted = mariaDBActivityDao.deleteActivityById(5);
        Assert.assertFalse(deleted);
    }

    /*
        The following tests test for catching thrown SQLExceptions (note that "Exists" methods call the "Get" methods
     */

    @Test
    public void getAllExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Collection<Activity> allActivities = mariaDBActivityDao.getAllActivities("user");
        Assert.assertNull(allActivities);
    }

    @Test
    public void getbyIdExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Activity activity = mariaDBActivityDao.getActivityById(1, "user");
        Assert.assertNull(activity);
    }

    @Test
    public void deleteActivityExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        boolean deleted = mariaDBActivityDao.deleteActivityById(1);
        Assert.assertFalse(deleted);
    }

    @Test
    public void addActivityExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        boolean added = mariaDBActivityDao.addActivity(new PostActivity(1, 1, 1), "user", 10);
        Assert.assertFalse(added);
    }



    @After
    public void tearDown() {
        db.shutdown();
    }

}