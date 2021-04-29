package picle.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.ScoreController;
import picle.controller.SocialController;
import picle.model.Friend;
import picle.model.Score;
import picle.model.User;

import java.util.List;


public class FriendsViewController {

    @FXML
    private Label username;
    @FXML
    private JFXNodesList friendControls;
    @FXML
    private JFXNodesList search;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton socialOptions;
    @FXML
    private JFXTextField txtField;
    @FXML
    private TableView<Friend> friendsTable;
    @FXML
    private Label score;
    @FXML
    private ProgressBar levelBar;
    @FXML
    private Label levelLabel;

    /**
     * Initialize Friends View.
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

        search.addAnimatedNode(searchButton);
        search.addAnimatedNode(txtField);

        friendControls.addAnimatedNode(socialOptions);
        friendControls.addAnimatedNode(addButton);
        friendControls.addAnimatedNode(deleteButton);
        friendControls.addAnimatedNode(search);

        showFriends(getFriends());
        updateLevel();
    }

    /**
     * Performs API call to get friends.
     * @return List of friends that can be represented in the view.
     */
    public ObservableList<Friend> getFriends() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        User user = new User(name, pass);

        //Get the score list from the server
        SocialController controller = new SocialController(user);
        ObservableList<Friend> friendsList = null;

        try {
            List<Friend> list = controller.getFriends();
            //Convert the array list into an observable list so it can be added to the tableview
            friendsList = FXCollections.observableArrayList(list);

        } catch (HttpStatusCodeException e) {
            Main.showError("Could not retrieve friends");
        }
        return friendsList;
    }

    /**
     * Represent ObservableList in the table.
     * @param friendsList List of friends.
     */
    public void showFriends(ObservableList<Friend> friendsList) {
        //Create username column
        TableColumn<Friend, String> userColumn = new TableColumn<>("Username");
        userColumn.setMinWidth(200);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        /*Possible autonumbering?*/

        friendsTable.setItems(friendsList);
        friendsTable.getColumns().addAll(userColumn);
    }

    /**
     * Perform API call to add a friend.
     * Then update view to include new friend.
     */
    @FXML
    public void addFriend() {
        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        Friend friendToAdd = new Friend(txtField.getText());
        User user = new User(name, pass);
        SocialController controller = new SocialController(user);

        try {
            if (controller.addFriend(friendToAdd)) {
                friendsTable.getItems().clear();
                friendsTable.setItems(getFriends());
            } else {
                Main.showError("Could not add friend");
            }
        } catch (HttpStatusCodeException exception) {
            Main.showError("Could not add friend");
        }
    }

    /**
     * Perform API call to delete friend.
     * Then update view without that friend.
     */
    @FXML
    public void deleteFriend() {
        try {
            Friend selectedFriend = friendsTable.getSelectionModel().getSelectedItem();
            String selectedFriendUser = selectedFriend.getUsername();
            String name = Configuration.getInstance().username;
            String pass = Configuration.getInstance().password;
            User user = new User(name, pass);
            SocialController controller =  new SocialController(user);
            controller.deleteFriend(selectedFriendUser);
            friendsTable.getItems().clear();
            friendsTable.setItems(getFriends());

        } catch (HttpStatusCodeException exception) {
            Main.showError("Could not delete friend");
        } catch (NullPointerException exception) {
            Main.showError("Please select a friend to delete");
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

    @FXML
    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

    @FXML
    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    @FXML
    public void goToAchievements() {
        Main.activateScreen("achievements.fxml");
    }

}
