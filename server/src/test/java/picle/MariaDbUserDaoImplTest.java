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
import picle.dao.MariaDbUserDaoImpl;
import picle.entity.RegisterUser;
import picle.entity.User;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.when;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@RunWith(SpringRunner.class)
public class MariaDbUserDaoImplTest {

    private EmbeddedDatabase db;
    private User baseUser1;
    private User baseUser2;

    @Mock
    DatabaseController dbController;

    @Mock
    DatabaseControllerBuilder dcBuilder;

    @InjectMocks
    MariaDbUserDaoImpl mariaDBUserDao;

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
                    + "level int NOT NULL,"
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
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score, level) VALUES (1, 'Sungyboi', 'walkinthepark', 0, 0);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score, level) VALUES (2, 'Alpyboi', 'shefique', 0, 0);");
            setupStm.execute("INSERT INTO activities (activity_id, user_id, activity_type_id, param0, param1, score) VALUES (1, 1, 1, 10, 100, 1000);");
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


    private static Object[] propertyArray(User user) {
        return new Object[] {user.getUserId(),
                user.getUsername(),
                user.getPassword()};
    }

    @Before
    public void setUp() throws SQLException {
        // creates an HSQL in-memory database populated from default scripts
        // classpath:schema.sql and classpath:data.sql
        baseUser1 = new User(1, "Sungyboi", "walkinthepark");
        baseUser2 = new User(2, "Alpyboi", "shefique");

        db = edbBuilder();
        MockitoAnnotations.initMocks(this);
        when(dcBuilder.buildDatabaseController()).thenReturn(dbController);
        when(dbController.getConn()).thenReturn(edbBuilder().getConnection());
    }

    /*
        The following tests test for functionality as expected
     */

    @Test
    public void getUserByIdTest() {
        User user = mariaDBUserDao.getUser(1);
        Assert.assertArrayEquals(propertyArray(baseUser1), propertyArray(user));
    }

    @Test
    public void getUserByUsernameTest() {
        User user = mariaDBUserDao.getUser("Alpyboi");
        Assert.assertArrayEquals(propertyArray(baseUser2), propertyArray(user));
    }

    @Test
    public void userExistsByIdTest() {
        boolean exists = mariaDBUserDao.userExists(2);
        Assert.assertTrue(exists);
    }

    @Test
    public void userExistsByUsernameTest() {
        boolean exists = mariaDBUserDao.userExists("Sungyboi");
        Assert.assertTrue(exists);
    }

    @Test
    public void deleteUserByIdTest() {
        boolean deleted = mariaDBUserDao.deleteUser(1);
        Assert.assertTrue(!deleted);
    }

    @Test
    public void deleteUserByUsernameTest() {
        boolean deleted = mariaDBUserDao.deleteUser("Sungyboi");
        Assert.assertTrue(!deleted);
    }

    @Test
    public void changeUsernameTest() {
        boolean changedName = mariaDBUserDao.changeUsername(2, "NewName");
        Assert.assertTrue(!changedName);
    }

    @Test
    public void registerUserTest() {
        RegisterUser newUser = new RegisterUser("Martijnyboi", "whatisastaal");
        boolean registered = mariaDBUserDao.register(newUser);
        Assert.assertTrue(registered);
    }

    /*
        The following tests test for querying non-existent tuples
     */

    @Test
    public void getNonexistentUserByIdTest() {
        User user = mariaDBUserDao.getUser(15);
        Assert.assertNull(user);
    }

    @Test
    public void getNonexistentUserByUsernameTest() {
        User user = mariaDBUserDao.getUser("bestUsername");
        Assert.assertNull(user);
    }

    @Test
    public void userDoesNotExistByIdTest() {
        boolean exists = mariaDBUserDao.userExists(4);
        Assert.assertFalse(exists);
    }

    @Test
    public void userDoesNotExistByUsernameTest() {
        boolean exists = mariaDBUserDao.userExists("nonExistentUser");
        Assert.assertFalse(exists);
    }
    @Test
    public void deleteNonexistentUserByIdTest() {
        boolean deleted = mariaDBUserDao.deleteUser(124);
        Assert.assertTrue(!deleted);
    }

    @Test
    public void deleteNonexistentUserByUsernameTest() {
        boolean deleted = mariaDBUserDao.deleteUser("BestUser");
        Assert.assertTrue(!deleted);
    }

    @Test
    public void changeNonexistentUsernameTest() {
        boolean changedName = mariaDBUserDao.changeUsername(2, "NewName");
        Assert.assertTrue(!changedName);
    }

    @Test
    public void registerExistingUserTest() {
        RegisterUser newUser = new RegisterUser("Sungyboi", "whyarethere4");
        boolean registered = mariaDBUserDao.register(newUser);
        Assert.assertFalse(registered);
    }

    /*
        The following tests test for catching thrown SQLExceptions (note that "Exists" methods call the "Get" methods
     */

    @Test
    public void getUserByIdExceptionTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        User user = mariaDBUserDao.getUser(1);
        Assert.assertNull(user);
    }

    @Test
    public void getUserByUsernameExceptionTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        User user = mariaDBUserDao.getUser("anyUsername");
        Assert.assertNull(user);
    }

    @Test
    public void registerUserExceptionTest() throws Exception {
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        RegisterUser newUser = new RegisterUser("Sungyboi", "whyarethere4");
        boolean registered = mariaDBUserDao.register(newUser);
        Assert.assertFalse(registered);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
