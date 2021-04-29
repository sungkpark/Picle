package picle.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ScoreController;
import picle.controller.SocialController;
import picle.model.Friend;
import picle.model.Score;
import picle.model.User;

import java.util.ArrayList;
import java.util.List;


public class ScoreboardViewController {

    @FXML
    private Label username;
    @FXML
    private TableView<Score> scoreTable;
    @FXML
    private Label score;
    @FXML
    private Pane root;
    @FXML
    private ProgressBar levelBar;
    @FXML
    private Label levelLabel;

    /**
     * Initialize Scoreboard View.
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
        showScores(getScores());
        updateLevel();
    }

    /**
     * Perform API call to get all scores.
     * @return List of scores.
     */
    public ObservableList<Score> getScores() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        User user = new User(name, pass);

        //Get the score list from the server
        ScoreController scoreController = new ScoreController(user);
        SocialController socialController = new SocialController(user);
        //Convert the array list into an observable list so it can be added to the tableview
        ObservableList<Score> scores = null;

        try {
            List<Friend> friendList = socialController.getFriends();
            List<Score> scoreList = scoreController.getScores();
            List<Score> result = new ArrayList<>();
            for (Score score : scoreList) {
                Friend friend = new Friend(score.getUsername());
                if (friendList.contains(friend)) {
                    result.add(score);
                }
            }

            scores = FXCollections.observableArrayList(result);

        } catch (HttpStatusCodeException e) {
            Main.showError("Could not retrieve scoreboard");
        }
        return scores;
    }

    /**
     * Represent list of scores in the view.
     * @param score List of scores.
     */
    public void showScores(ObservableList<Score> score) {
        //Create username column
        TableColumn<Score, String> userColumn = new TableColumn<>("Username");
        userColumn.setMinWidth(200);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Create score column
        TableColumn<Score, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(200);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        /*Possible autonumbering?*/

        scoreTable.setItems(score);
        scoreTable.getColumns().addAll(userColumn, scoreColumn);
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

    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

    public void goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }

}
