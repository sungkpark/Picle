package picle.view;

import animatefx.animation.BounceInDown;
import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.AuthController;
import picle.model.User;

//import java.io.ObjectInputFilter;

public class LoginViewController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView picle;
    @FXML
    private Pane root;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;


    /**
     * Initializes the login screen.
     */
    public void initialize() {

        AnimationController animate = new AnimationController();

        new FadeIn(root).setSpeed(0.6).play();

        animate.slideX(0.7, username, 0);
        animate.slideX(0.7, password, 0.5);
        animate.slideX(0.7, loginButton, 0);
        animate.slideX(0.7, registerButton, 0.4);
        animate.slideX(0.6, usernameLabel, 0);
        animate.slideX(0.6, passwordLabel, 0.2);

        new BounceInDown(picle).setSpeed(0.8).play();

    }

    /**
     * Performs a request to /auth with the user credentials in the authorization header.
     */
    @FXML
    public void login() {
        String name = username.getText();
        String pass = password.getText();

        try {
            User user = new User(name, pass);
            AuthController ac = new AuthController(user);
            ac.login();
            Configuration.getInstance().storeCredentials(name, pass);
            Main.activateScreen("home.fxml");
        } catch (HttpStatusCodeException e) {
            Main.showError("Wrong username/password combination");
        }

    }

    /**
     * Perform API call to register a user.
     */
    @FXML
    public void register() {
        String name = username.getText();
        String pass = password.getText();

        try {
            User user = new User(name, pass);
            AuthController ac = new AuthController(user);
            ac.register(user);
            Configuration.getInstance().storeCredentials(name, pass);
            Main.activateScreen("home.fxml");
        } catch (HttpStatusCodeException e) {
            Main.showError("Try registering again");
        }
    }

}
