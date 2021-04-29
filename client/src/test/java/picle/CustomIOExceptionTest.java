package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.common.CustomIoException;

public class CustomIOExceptionTest {

    @Test
    public void emptyCustomIOExceptionTest(){
        CustomIoException exception = new CustomIoException();
        Assert.assertNull(exception.getType());
        Assert.assertNull(exception.getMessage());
    }

    @Test
    public void newCustomIOExceptionTest(){
        CustomIoException exception = new CustomIoException("type", "message");
        Assert.assertEquals(exception.getType(), "type");
        Assert.assertEquals(exception.getMessage(), "message");
    }
}
