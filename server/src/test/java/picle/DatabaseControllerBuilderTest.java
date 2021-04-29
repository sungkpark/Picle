package picle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

@RunWith(SpringRunner.class)
public class DatabaseControllerBuilderTest {

    @Test
    public void DatabaseControllerBuildTest() {
        DatabaseController firstController = new DatabaseController();
        DatabaseControllerBuilder builder = new DatabaseControllerBuilder();
        DatabaseController secondController = builder.buildDatabaseController();
        Assert.assertNotEquals(firstController, secondController);
    }
}
