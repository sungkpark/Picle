package picle.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ActivityController;
import picle.controller.ScoreController;
import picle.model.Activity;
import picle.model.Score;
import picle.model.User;

public class FoodActivityViewController {

    @FXML
    private Label username;
    @FXML
    private Pane root;
    @FXML
    private JFXToggleButton selectVegieMeal;
    @FXML
    private JFXToggleButton selectLocalProduce;
    @FXML
    private Label score;
    @FXML
    private ProgressBar levelBar;
    @FXML
    private Label levelLabel;

    /**
     * Initialize the scene by getting user's score and username.
     */
    public void initialize() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        ScoreController scoreController = new ScoreController(new User(name, pass));
        try {
            Score userScore = scoreController.getScore(name);
            double carbonScore = userScore.getScore();
            score.setText(String.format("%.2f %s", carbonScore, "kg"));
        } catch (HttpStatusCodeException exception) {
            Main.showError("Score could not be retrieved :(");
        }
        username.setText(name);
        updateLevel();
    }

    /**
     * Create the input dialog for the activity parameters.
     */
    public void showInputDialog() {
        if (selectLocalProduce.isSelected() || selectVegieMeal.isSelected()) {

            JFXTextField txtParam0 = new JFXTextField();
            txtParam0.getStylesheets().add("style/activity.css");
            txtParam0.getStyleClass().addAll("äpplyFont", "textField");

            JFXTextField txtParam1 = new JFXTextField();
            txtParam1.getStylesheets().add("style/activity.css");
            txtParam1.getStyleClass().addAll("äpplyFont", "textField");

            VBox vbox = new VBox();
            vbox.setPrefSize(430, 185);
            vbox.setSpacing(30);
            vbox.setPadding(new Insets(20, 0, 0, 0));

            HBox boxParam0 = new HBox();
            boxParam0.setAlignment(Pos.CENTER);
            boxParam0.setSpacing(20);
            boxParam0.setFillHeight(true);

            HBox boxParam1 = new HBox();
            boxParam1.setAlignment(Pos.CENTER);
            boxParam1.setSpacing(20);
            boxParam1.setFillHeight(true);

            HBox boxButtons = new HBox();
            boxButtons.setAlignment(Pos.CENTER);
            boxButtons.setSpacing(20);
            boxButtons.setFillHeight(true);

            Label labelParam0 = new Label();
            labelParam0.setText("Label1");
            labelParam0.getStylesheets().add("style/activity.css");
            labelParam0.getStyleClass().addAll("applyFont");

            Label labelParam1 = new Label();
            labelParam1.setText("Label2");
            labelParam1.getStylesheets().add("style/activity.css");
            labelParam1.getStyleClass().addAll("applyFont");

            JFXButton submitButton = new JFXButton();
            submitButton.setText("Submit");
            submitButton.getStylesheets().add("style/activity.css");
            submitButton.getStyleClass().addAll("applyFont", "inputButton");

            JFXButton cancelButton = new JFXButton();
            cancelButton.setText("Cancel");
            cancelButton.getStylesheets().add("style/activity.css");
            cancelButton.getStyleClass().addAll("applyFont", "inputButton");

            if (selectVegieMeal.isSelected()) {
                labelParam1.setText("Number of Portions:");
                boxParam0.setVisible(false);
            } else if (selectLocalProduce.isSelected()) {
                labelParam1.setText("Number of Products:");
                boxParam0.setVisible(false);
            }
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent exception) {
                    int param0 = Integer.parseInt(txtParam1.getText());
                    passParams(param0);
                }
            });

            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(615);
            stackPane.setLayoutY(299);

            boxParam0.getChildren().addAll(labelParam0, txtParam0);
            boxParam1.getChildren().addAll(labelParam1, txtParam1);
            boxButtons.getChildren().addAll(submitButton, cancelButton);
            vbox.getChildren().addAll(boxParam0, boxParam1, boxButtons);
            root.getChildren().add(stackPane);

            JFXDialog inputDialog = new JFXDialog(stackPane, vbox,
                    JFXDialog.DialogTransition.CENTER);
            inputDialog.show();

            cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent exception) {
                    inputDialog.close();
                }
            });
        } else {
            Main.showError("Please select an activity");
        }
    }

    /**
     * Pass the inputted parameter to the activity controller.
     * @param param0 User inputted value in dialog
     */
    public void passParams(int param0) {
        if (selectVegieMeal.isSelected()) {
            try {
                String name = Configuration.getInstance().username;
                String pass = Configuration.getInstance().password;
                User user = new User(name, pass);
                ActivityController controller = new ActivityController(user);
                Activity activity = new Activity(-1, 1, param0, -1, -1);
                controller.addActivity(activity);
                Main.activateScreen("home.fxml");
            } catch (HttpStatusCodeException exception) {
                Main.showError("Unable to add activity");
            } catch (NumberFormatException exception) {
                Main.showError("Number of portions must be a number");
            }
        } else if (selectLocalProduce.isSelected()) {
            try {
                String name = Configuration.getInstance().username;
                String pass = Configuration.getInstance().password;
                User user = new User(name, pass);
                ActivityController controller = new ActivityController(user);
                Activity activity = new Activity(-1, 2, param0, -1, -1);
                controller.addActivity(activity);
                Main.activateScreen("home.fxml");
            } catch (HttpStatusCodeException e) {
                Main.showError("Unable to add activity");
            } catch (NumberFormatException e) {
                Main.showError("Amount of local products must be a number");
            }
        }
    }

    /**
     * Update the users level on the dashboard.
     */
    public void updateLevel() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        ScoreController scoreController = new ScoreController(new User(name, pass));
        Score userScore = scoreController.getScore(name);
        levelLabel.setText(String.valueOf(userScore.getLevel()));
        double numScore = userScore.getScore();
        int numLevel = userScore.getLevel();
        double progress = (numScore * 1000 - numLevel * 1000) / (double) 1000;
        levelBar.setProgress(progress);
    }

    public void goBack() {
        Main.activateScreen("activity.fxml");
    }

    public void goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }
}
