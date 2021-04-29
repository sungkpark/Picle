package picle.view;

import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller which provides an abstraction for switching
 * between different FXML views.
 */
public class SceneController {

    private Stage main;
    private Pane pane;

    public SceneController(Stage main, Pane pane) {
        this.main = main;
        this.pane = pane;
    }

    /**
     * Activates new screen.
     * @param name Name of FXML file in views folder.
     *     Eg. activate("login.fxml").
     */
    public void activate(String name) {
        try {
            Pane pane = FXMLLoader.load(getClass().getClassLoader().getResource("view/" + name));
            main.setScene(new Scene(pane));
            this.pane = pane;
            animateScene();
        } catch (IOException ex) {
            System.out.println("Failed to load screen");
        }
    }

    /**
     * Pick a random slide in transition from one of four sides of the screen.
     */
    public void animateScene() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 4 + 1;
        int randomInt = (int) randomDouble;
        switch (randomInt) {
            default:
                new SlideInLeft(pane).setSpeed(1.4).play();
                break;
            case 1:
                new SlideInLeft(pane).setSpeed(1.4).play();
                break;
            case 2:
                new SlideInUp(pane).setSpeed(1.4).play();
                break;
            case 3:
                new SlideInDown(pane).setSpeed(1.4).play();
                break;
            case 4:
                new SlideInRight(pane).setSpeed(1.4).play();
                break;
        }
    }

    /**
     * Show a specified error message.
     * @param message the error message to show
     */
    public void showError(String message) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.setPrefWidth(350);
        snackbar.getPopupContainer().setStyle("-fx-text-alignment: center");
        snackbar.setStyle("-fx-text-alignment: center");
        snackbar.getStylesheets().add("style/login.css");
        snackbar.show(message, 4000);
    }


}
