package picle.view;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationController {

    /**
     * Animates the node horizontally from the border of the scene.
     *
     * @param duration Duration of the animation
     * @param node     Node to animate
     * @param delay    Delay before the execution of the animation
     */
    public static void slideX(double duration, Node node, double delay) {

        TranslateTransition animation = new TranslateTransition(Duration.seconds(duration), node);
        animation.setToX(node.getLayoutX());
        animation.setDelay(Duration.seconds(delay));
        node.setLayoutX(0);
        animation.play();
        node.setLayoutX(node.getLayoutX());

    }

    /**
     * Adds a rotation animation to the node.
     * @param duration Duration of the animation
     * @param node Node to animate
     * @param delay Delay between restarts
     */
    public static void rotate(double duration, Node node, double delay) {
        RotateTransition animation = new RotateTransition(Duration.seconds(duration), node);
        animation.setDelay(Duration.seconds(delay));
        animation.setByAngle(360);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
    }

    /**
     * Adds a grow and shrink animation to the node.
     * @param duration Duration of the animation
     * @param node Node to animate
     * @param delay Delays between restarts
     * @param xscale Factor to scale by on the x-axis
     * @param yscale Factor to scale by on the y-axis
     */
    public static void grow(double duration, Node node,
                            double delay, double xscale, double yscale) {
        ScaleTransition animation = new ScaleTransition(Duration.seconds(duration), node);
        animation.setDelay(Duration.seconds(delay));
        animation.setByX(xscale);
        animation.setByY(yscale);
        animation.setAutoReverse(true);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

}
