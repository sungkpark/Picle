package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.service.LevelCalculator;

public class LevelCalculatorTest {

    @Test
    public void calculateLevelTest() {
        Assert.assertEquals(2, LevelCalculator.calculateLevel(2430));
    }
}
