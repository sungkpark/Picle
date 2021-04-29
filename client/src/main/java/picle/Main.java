package picle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
//import javafx.scene.text.Font;
import javafx.stage.Stage;
import picle.common.Configuration;
import picle.view.SceneController;

/**
 * Main sets up he first view and initializes the SceneController
 * with the other views so that we can switch between them easily.
 * Eg. Main.activateScreen("home") will activate the home screen.
 */
public class Main extends Application {

    private static SceneController sceneController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("view/intro.fxml"));
        sceneController = new SceneController(primaryStage, root);
        primaryStage.setTitle("Picle");
        primaryStage.getIcons().add(new Image("images/appIcon.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        if (name != null && pass != null) {
            if (!name.isEmpty() && !pass.isEmpty()) {
                Main.activateScreen("home.fxml");
            }
        }
    }

    /**
     * Static method such that we can switch screens anywhere in code.
     * @param name Name of the screen you wish to switch to. See Main.start().
     */
    public static void activateScreen(String name) {
        sceneController.activate(name);
    }

    /**
     * Static method such that we can show errors from anywhere in code.
     * @param message Message you want to display to the user.
     */
    public static void showError(String message) {
        sceneController.showError(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
