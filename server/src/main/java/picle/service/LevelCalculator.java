package picle.service;

public class LevelCalculator {

    /**
     * Calculates level of the user.
     * @param totalScore total score is used
     *                   because level is calculated from total score
     * @return rounded down total score over a hundred
     *      This can be changed later depending on how we give achievements.
     */
    public static int calculateLevel(int totalScore) {
        return (int) Math.floor(totalScore / 1000);
    }
}
