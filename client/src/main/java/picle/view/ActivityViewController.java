package picle.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ScoreController;
import picle.model.Score;
import picle.model.User;

public class ActivityViewController {

    @FXML
    private Label username;
    @FXML
    private Label score;
    @FXML
    private ProgressBar levelBar;
    @FXML
    private Label levelLabel;

    /**
     * Initialize Activity View.
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

    public void goToFoodActivity() {
        Main.activateScreen("addFood.fxml");
    }

    public void goToTransportActivity() {
        Main.activateScreen("addTransport.fxml");
    }

    public void goToEnergyActivity() {
        Main.activateScreen("addEnergy.fxml");
    }

    public void goBack() {
        Main.activateScreen("home.fxml");
    }

    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

    public void goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }
}


