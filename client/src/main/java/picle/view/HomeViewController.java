package picle.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ActivityController;
import picle.controller.ScoreController;
import picle.model.Activity;
import picle.model.Score;
import picle.model.User;

import java.util.List;


public class HomeViewController {

    @FXML
    private Label username;
    @FXML
    private Label score;
    @FXML
    private VBox feed;
    @FXML
    private Label levelLabel;
    @FXML
    private ProgressBar levelBar;


    /**
     * Initialize Home View.
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
        showActivities();
        updateLevel();
    }

    /**
     * Perform API call to get Activities and show them in view.
     */
    public void showActivities() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        User user = new User(name, pass);

        ActivityController controller = new ActivityController(user);

        try {
            List<Activity> list = controller.getActivities();

            for (Activity activity : list) {
                showActivity(activity);
            }
        } catch (HttpStatusCodeException e) {
            Main.showError("Could not retrieve activities");
        }

    }

    /**
     * Construct and show UI elements for a single activity.
     * @param activity Activity to construct.
     */
    public void showActivity(Activity activity) {
        Label desc = new Label();
        String activityIconPath = "";
        switch (activity.getType_id()) {
            default:
                activityIconPath = "";
                break;
            case 1:
                desc.setText(String.format("Ate %d Vegetarian Meals", activity.getParam0()));
                activityIconPath = "images/VegieMealIcon.png";
                break;
            case 2:
                desc.setText(String.format("Bought %d local products", activity.getParam0()));
                activityIconPath = "images/LocalProduceIcon.png";
                break;
            case 3:
                desc.setText(String.format("Travelled %dKm by bike", activity.getParam0()));
                activityIconPath = "images/UseBikeIcon.png";
                break;
            case 4:
                desc.setText(String.format("Travelled %dKm via public transport",
                        activity.getParam0()));
                activityIconPath = "images/PublicTransportIcon.png";
                break;
            case 5:
                desc.setText("Lowered House Temperature");
                activityIconPath = "images/LowerHouseTempIcon.png";
                break;
            case 6:
                desc.setText(String.format("Produced %dKWh using solar panels",
                        activity.getParam0()));
                activityIconPath = "images/SolarPanelsIcon.png";
                break;
        }

        desc.setWrapText(true);
        desc.getStylesheets().add("style/home.css");
        desc.getStyleClass().add("feedLabel");

        Label score = new Label(String.format("Score: %d", activity.getScore()));
        score.getStylesheets().add("style/home.css");
        score.getStyleClass().add("feedLabelScore");

        HBox scoreContainer = new HBox();
        scoreContainer.getChildren().add(score);
        scoreContainer.getStylesheets().add("style/home.css");
        score.getStyleClass().add("feedLabelScoreContainer");

        ImageView activityIcon = new ImageView();
        Image image = new Image(activityIconPath);
        activityIcon.setImage(image);
        activityIcon.setFitHeight(80);
        activityIcon.setFitWidth(80);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(activityIcon, desc, scoreContainer);
        hbox.getStylesheets().add("style/home.css");
        hbox.getStyleClass().add("feedActivity");
        feed.getChildren().add(hbox);
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


    @FXML
    public void addActivity() {
        Main.activateScreen("activity.fxml");
    }

    @FXML
    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    @FXML
    public void  goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    @FXML
    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }
}
