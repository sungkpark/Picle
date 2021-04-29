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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ActivityController;
import picle.controller.ScoreController;
import picle.model.Activity;
import picle.model.Score;
import picle.model.User;


public class EnergyActivityViewController {

    @FXML
    private Label username;
    @FXML
    private Pane root;
    @FXML
    private JFXToggleButton selectHouseTemp;
    @FXML
    private JFXToggleButton selectSolarPanels;
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
        if (selectHouseTemp.isSelected() || selectSolarPanels.isSelected()) {

            JFXTextField txtParam1 = new JFXTextField();
            txtParam1.getStylesheets().add("style/activity.css");
            txtParam1.getStyleClass().addAll("äpplyFont");

            ChoiceBox houseChoice = new ChoiceBox();
            houseChoice.getStylesheets().add("style/activity.css");
            houseChoice.getStyleClass().addAll("äpplyFont");
            houseChoice.getItems().addAll(
                    "Apartment",
                    "Rowhouse",
                    "Corner House",
                    "2 Under 1 Roof",
                    "Single House");

            VBox vbox = new VBox();
            vbox.setPrefSize(430, 205);
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

            if (selectHouseTemp.isSelected()) {
                labelParam0.setText("Select House Type:");
                labelParam1.setText("House Temperature (°C):");
            } else if (selectSolarPanels.isSelected()) {
                labelParam1.setText("Monthly\nEnergy Produced (kWh):");
                labelParam1.setWrapText(true);
                labelParam1.setTextAlignment(TextAlignment.CENTER);
                boxParam0.setVisible(false);
            }
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent exception) {
                    int param0 = Integer.parseInt(txtParam1.getText());
                    String house = "";
                    if (selectHouseTemp.isSelected()) {
                        house = houseChoice.getSelectionModel().getSelectedItem().toString();
                    }
                    passParams(param0, house);
                }
            });

            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(615);
            stackPane.setLayoutY(299);

            boxParam0.getChildren().addAll(labelParam0, houseChoice);
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
     * Passes the inputed user values into the activity controller.
     * @param num Temperature/Energy inputted number
     * @param name House type name
     */
    public void passParams(int num, String name) {
        if (selectHouseTemp.isSelected()) {
            try {
                String username = Configuration.getInstance().username;
                String pass = Configuration.getInstance().password;
                User user = new User(username, pass);
                ActivityController controller = new ActivityController(user);
                int param0 = getOptionNumber(name);
                Activity activity = new Activity(-1, 5, param0, num, -1);
                controller.addActivity(activity);
                Main.activateScreen("home.fxml");
            } catch (HttpStatusCodeException exception) {
                Main.showError("Unable to add activity");
            } catch (NumberFormatException exception) {
                Main.showError("House temperature must be a number");
            }
        } else if (selectSolarPanels.isSelected()) {
            try {
                String username = Configuration.getInstance().username;
                String pass = Configuration.getInstance().password;
                User user = new User(username, pass);
                ActivityController controller = new ActivityController(user);
                Activity activity = new Activity(-1, 6, num, -1, -1);
                controller.addActivity(activity);
                Main.activateScreen("home.fxml");
            } catch (HttpStatusCodeException e) {
                Main.showError("Unable to add activity");
            } catch (NumberFormatException e) {
                Main.showError("Energy produced must be a number");
            }
        }
    }

    /**
     * Turns the house name option from the user input into a
     * number that can be passed into an activity.
     * @param name Name of the house option
     * @return number that corresponds to the activity house type
     */
    public int getOptionNumber(String name) {
        int param0 = 0;
        switch (name) {
            default:
                param0 = 1;
                break;
            case "Apartment":
                param0 = 1;
                break;
            case "Rowhouse":
                param0 = 2;
                break;
            case "Corner House":
                param0 = 3;
                break;
            case "2 Under 1 Roof":
                param0 = 4;
                break;
            case "Single House":
                param0 = 5;
                break;
        }
        return param0;
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

    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    public void goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }

}
