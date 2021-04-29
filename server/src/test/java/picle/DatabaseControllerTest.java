package picle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import picle.sql.DatabaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import static org.mockito.Matchers.any;
//import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(DriverManager.class)
@RunWith(SpringRunner.class)
public class DatabaseControllerTest {

    private DatabaseController databaseController;
    private Connection connection;
    private Connection generateConnection() {
        if (connection == null) {
            try {
                EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                        .generateUniqueName(true)
                        .setType(H2)
                        .setScriptEncoding("UTF-8")
                        .ignoreFailedDrops(true)
                        .build();
                connection = db.getConnection();
                return connection;
            } catch (SQLException e) {
                System.out.print(e.toString());
                return null;
            }
        } else {
            return connection;
        }
    }

    @Before
    public void setup() throws Exception{
//        PowerMockito.mockStatic(DriverManager.class);
//        when(DriverManager.getConnection(any(), any(), any())).thenReturn(generateConnection());
        databaseController = new DatabaseController();

    }

    @Test
    public void getConnTest() {
        Assert.assertEquals(null, databaseController.getConn());
    }

    @Test
    public void getHostTest() {
        Assert.assertEquals("localhost", databaseController.getDbHost());
    }

    @Test
    public void getUserTest() {
        Assert.assertEquals("root", databaseController.getDbUser());
    }

    @Test
    public void closeNullExceptionTest() {
        boolean thrown = false;
        try{
            databaseController.close();
        } catch(NullPointerException e){
            thrown = true;
        }
        Assert.assertTrue(thrown);
    }
}
