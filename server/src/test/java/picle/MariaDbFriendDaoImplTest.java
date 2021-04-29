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
import picle.dao.MariaDbFriendDaoImpl;
import picle.entity.Friend;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import static org.mockito.Mockito.when;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@RunWith(SpringRunner.class)
public class MariaDbFriendDaoImplTest {
    private EmbeddedDatabase db;
    private Friend baseFriend1;
    private Friend baseFriend2;

    @Mock
    DatabaseController dbController;

    @Mock
    DatabaseControllerBuilder dcBuilder;

    @InjectMocks
    MariaDbFriendDaoImpl mariaDBFriendDao;

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
            setupStm.execute("CREATE TABLE friends ("
                    + "friendship_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "user_id int NOT NULL,"
                    + "friend_id int NOT NULL,"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score) VALUES (1, 'Sungyboi', 'walkinthepark', 0);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score) VALUES (2, 'Alpyboi', 'shefique', 0);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score) VALUES (3, 'Martijnyboi', 'whatisastaal', 0);");
            setupStm.execute("INSERT INTO activities (activity_id, user_id, activity_type_id, param0, param1, score) VALUES (1, 1, 1, 10, 100, 1000);");
            setupStm.execute("INSERT INTO activity_types (activity_type_id, activity_type_name) VALUES (1, 'best activity');");
            setupStm.execute("INSERT INTO friends (user_id, friend_id) VALUES (1, 2)");
            setupStm.execute("INSERT INTO friends (user_id, friend_id) VALUES (1, 3)");
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

    @Before
    public void setUp() throws SQLException {
        // creates an HSQL in-memory database populated from default scripts
        // classpath:schema.sql and classpath:data.sql
        baseFriend1 = new Friend("Alpyboi");
        baseFriend2 = new Friend("Martijnyboi");

        db = edbBuilder();
        MockitoAnnotations.initMocks(this);
        when(dcBuilder.buildDatabaseController()).thenReturn(dbController);
        when(dbController.getConn()).thenReturn(edbBuilder().getConnection());
    }

    /*
        The following tests test for functionality as expected
     */

    @Test
    public void getAllFriendsTest() {
        Collection<Friend> friends = mariaDBFriendDao.getAllFriends("Sungyboi");
        Iterator<Friend> allFriends = friends.iterator();
        Friend next = allFriends.next();
        if(next.getUsername().equals(baseFriend1.getUsername())){
            Assert.assertEquals(baseFriend1.getUsername(), next.getUsername());
            Assert.assertEquals(baseFriend2.getUsername(), allFriends.next().getUsername());
        } else {
            Assert.assertEquals(baseFriend2.getUsername(), next.getUsername());
            Assert.assertEquals(baseFriend1.getUsername(), allFriends.next().getUsername());
        }
        Assert.assertEquals(2, friends.size());
    }

    @Test
    public void getFriendByUsernameTest() {
        Friend friend = mariaDBFriendDao.getFriendByUsername("Sungyboi", "Alpyboi");
        Assert.assertEquals(baseFriend1.getUsername(), friend.getUsername());
    }

    @Test
    public void deleteFriendByUsernameTest() {
        boolean deleted = mariaDBFriendDao.deleteFriendByUsername("Sungyboi", "Alpyboi");
        Assert.assertTrue(deleted);
    }

    @Test
    public void addFriendByUsernameTest() {
        boolean added = mariaDBFriendDao.addFriend("Martijnyboi", baseFriend1);
        Assert.assertTrue(added);
    }

    /*
        The following tests test for querying non-existent tuples
     */

    @Test
    public void getAllNonexistentFriendsTest() {
        Collection<Friend> friends = mariaDBFriendDao.getAllFriends("Lonelyboi");
        Assert.assertEquals(0, friends.size());
    }

    @Test
    public void getNonexistentFriendByUsernameTest() {
        Friend friend = mariaDBFriendDao.getFriendByUsername("Lonelyboi", "Alpyboi");
        Assert.assertNull(friend);
    }

    @Test
    public void deleteNonexistentFriendByUsernameTest() {
        boolean deleted = mariaDBFriendDao.deleteFriendByUsername("Lonelyboi", "Alpyboi");
        Assert.assertFalse(deleted);
    }

    @Test
    public void addNonexistentFriendByUsernameTest() {
        boolean added = mariaDBFriendDao.addFriend("Sungyboi", new Friend("newFriend"));
        Assert.assertFalse(added);
    }

    /*
        The following tests test for catching thrown SQLExceptions (note that "Exists" methods call the "Get" methods
     */

    @Test
    public void getAllFriendsExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Collection<Friend> friends = mariaDBFriendDao.getAllFriends("Sungyboi");
        Assert.assertEquals(0, friends.size());
    }

    @Test
    public void getFriendByUsernameExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Friend friend = mariaDBFriendDao.getFriendByUsername("Sungyboi", "Alpyboi");
        Assert.assertNull(friend);
    }

    @Test
    public void deleteFriendByUsernameExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        boolean deleted = mariaDBFriendDao.deleteFriendByUsername("Sungyboi", "Alpyboi");
        Assert.assertFalse(deleted);
    }

    @Test
    public void addFriendByUsernameExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        boolean added = mariaDBFriendDao.addFriend("Sungyboi", new Friend("newFriend"));
        Assert.assertFalse(added);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
