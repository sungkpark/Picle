package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.service.ScoreCalculator;

public class ScoreCalculatorTest {
    @Test
    public void testVegeterianMeal() {
        Assert.assertEquals(
                ScoreCalculator.vegetarianMeal(1),
                2310);
    }

    @Test
    public void testCalculateScoreVegeterianMeal() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(1,1,0),
                ScoreCalculator.vegetarianMeal(1)
        );
    }

    @Test
    public void testCalculateScoreVegeterianMealWithUselessSecondParam() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(1,1,2423),
                ScoreCalculator.vegetarianMeal(1)
        );
    }

    @Test
    public void testBuyLocalProduct() {
        Assert.assertEquals(
                ScoreCalculator.buyLocalProduct(1),
                329);
    }

    @Test
    public void testCalculateScoreBuyLocalProduct() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(2,1,0),
                ScoreCalculator.buyLocalProduct(1)
        );
    }

    @Test
    public void testBikeInsteadOfCar() {
        Assert.assertEquals(
                ScoreCalculator.bikeInsteadOfCar(1),
                251);
    }

    @Test
    public void testCalculateScoreBikeInsteadOfCar() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(3,1,0),
                ScoreCalculator.bikeInsteadOfCar(1)
        );
    }

    @Test
    public void testLoweringHouseTempNoTempDiff() {
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(1,21),
                0);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(2,21),
                0);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(3,21),
                0);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(4,21),
                0);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(5,21),
                0);
    }

    @Test
    public void testLoweringHouseTempHigherTemp() {
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(1,23),
                -618);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(2, 23),
                -835);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(3,23),
                -977);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(4,23),
                -1113);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(5,23),
                -1490);
    }

    @Test
    public void testLoweringHouseTempLowerTemp() {
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(1,19),
                546);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(2, 19),
                737);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(3,19),
                863);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(4,19),
                983);
        Assert.assertEquals(
                ScoreCalculator.loweringHouseTemp(5,19),
                1316);
    }

    @Test
    public void testCalculateScoreLoweringHouseTemp() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(5,1,21),
                ScoreCalculator.loweringHouseTemp(1,21)
        );
    }

    @Test
    public void testPublicTransportInsteadOfCar() {
        Assert.assertEquals(
                ScoreCalculator.publicTransportInsteadOfCar(1),
                185);
    }

    @Test
    public void testCalculateScorePublicTransportInsteadOfCar() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(4,1,0),
                ScoreCalculator.publicTransportInsteadOfCar(1)
        );
    }

    @Test
    public void testInstallingSolarPanels() {
        Assert.assertEquals(
                ScoreCalculator.installingSolarPanels(1),
                579);
    }

    @Test
    public void testCalculateScoreInstallingSolarPanels() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(6,1,0),
                ScoreCalculator.installingSolarPanels(1)
        );
    }

    @Test
    public void testUnknownActivityId() {
        Assert.assertEquals(
                ScoreCalculator.calculateScore(43242,2,34),
                0
        );
    }
}
