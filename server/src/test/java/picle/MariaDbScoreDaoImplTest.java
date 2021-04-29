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
import picle.dao.MariaDbScoreDaoImpl;
import picle.entity.Score;
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
public class MariaDbScoreDaoImplTest {
    private EmbeddedDatabase db;
    private Score baseScore1;
    private Score baseScore2;

    @Mock
    DatabaseController dbController;

    @Mock
    DatabaseControllerBuilder dcBuilder;


    @InjectMocks
    MariaDbScoreDaoImpl mariaDBScoreDao;

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
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score, level) VALUES (1, 'Sungyboi', 'walkinthepark', 1234, 12);");
            setupStm.execute("INSERT INTO users (user_id, username, hashed_password, total_score, level) VALUES (2, 'Alpyboi', 'shefique', 5678, 56);");
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

    private EmbeddedDatabase emptyTablesEdbBuilder() {
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
        } catch(Exception e){
            System.out.print("Problem occurred whilst initializing in-memory test database.");
        }
        return db;
    }

    private static Object[] propertyArray(Score score) {
        return new Object[] {score.getScore(), score.getUsername()};
    }

    @Before
    public void setUp() throws SQLException {
        // creates an HSQL in-memory database populated from default scripts
        // classpath:schema.sql and classpath:data.sql
        baseScore1 = new Score("Sungyboi", 1234, 12);
        baseScore2 = new Score("Alpyboi", 5678, 56);

        db = edbBuilder();
        MockitoAnnotations.initMocks(this);
        when(dcBuilder.buildDatabaseController()).thenReturn(dbController);
        when(dbController.getConn()).thenReturn(edbBuilder().getConnection());
    }

    /*
        The following tests test for functionality as expected
     */

    @Test
    public void getAllScoresTest() {
        Collection<Score> returnedScores = mariaDBScoreDao.getAllScores();
        Iterator<Score> allScores = returnedScores.iterator();
        Assert.assertEquals(2, returnedScores.size());
        Score nextScore = allScores.next();
        if (nextScore.getScore() == 1234) {
            Assert.assertArrayEquals(propertyArray(nextScore), propertyArray(baseScore1));
            Assert.assertArrayEquals(propertyArray(allScores.next()), propertyArray(baseScore2));
        } else {
            Assert.assertArrayEquals(propertyArray(nextScore), propertyArray(baseScore2));
            Assert.assertArrayEquals(propertyArray(allScores.next()), propertyArray(baseScore1));
        }

    }

    @Test
    public void getScoreByUsernameTest() {
        Score returnScore = mariaDBScoreDao.getScoreByUsername("Sungyboi");
        Assert.assertArrayEquals(propertyArray(baseScore1), propertyArray(returnScore));
    }

    /*
        The following tests test for querying non-existent tuples
     */

    @Test
    public void getAllScoresEmptyTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyTablesEdbBuilder().getConnection());
        Collection<Score> scores = mariaDBScoreDao.getAllScores();
        Assert.assertEquals(0, scores.size());
    }

    @Test
    public void getScoreByUsernameEmptyTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyTablesEdbBuilder().getConnection());
        Score returnScore = mariaDBScoreDao.getScoreByUsername("Sungyboi");
        Assert.assertNull(returnScore);
    }



    /*
        The following tests test for catching thrown SQLExceptions (note that "Exists" methods call the "Get" methods
     */

    @Test
    public void getAllScoresExceptionTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Collection<Score> scores = mariaDBScoreDao.getAllScores();
        Assert.assertEquals(0, scores.size());
    }

    @Test
    public void getScoreByUsernameExceptionTest() throws Exception{
        when(dbController.getConn()).thenReturn(emptyEdbBuilder().getConnection());
        Score returnScore = mariaDBScoreDao.getScoreByUsername("Sungyboi");
        Assert.assertNull(returnScore);
    }


    @After
    public void tearDown() {
        db.shutdown();
    }

}
