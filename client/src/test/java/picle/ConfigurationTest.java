package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.common.Configuration;

public class ConfigurationTest {

    @Test
    public void getConfigUsernameTest() {
        String name = Configuration.getInstance().username;
        Assert.assertNotNull(name);
    }

    @Test
    public void getConfigPasswordTest() {
        String password = Configuration.getInstance().password;
        Assert.assertNotNull(password);
    }

    @Test
    public void getConfigUrlTest() {
        String url = Configuration.getInstance().url;
        Assert.assertNotNull(url);
    }


}
