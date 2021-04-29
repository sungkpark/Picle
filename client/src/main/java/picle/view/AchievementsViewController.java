package picle.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.springframework.web.client.HttpStatusCodeException;
import picle.Main;
import picle.common.Configuration;
import picle.controller.AchievementController;
import picle.controller.ScoreController;
import picle.model.Score;
import picle.model.User;

import java.util.ArrayList;


public class AchievementsViewController {

    @FXML
    private Label score;
    @FXML
    private Label username;
    @FXML
    private ImageView vegieRing;
    @FXML
    private ImageView vegieInner;
    @FXML
    private ImageView vegieLock;

    @FXML
    private ImageView localProduceRing;
    @FXML
    private ImageView localProduceInner;
    @FXML
    private ImageView localProduceLock;

    @FXML
    private ImageView publicTransportRing;
    @FXML
    private ImageView publicTransportInner;
    @FXML
    private ImageView publicTransportLock;

    @FXML
    private ImageView solarPanelRing;
    @FXML
    private ImageView solarPanelInner;
    @FXML
    private ImageView solarPanelLock;

    @FXML
    private ImageView houseTempRing;
    @FXML
    private ImageView houseTempInner;
    @FXML
    private ImageView houseTempLock;

    @FXML
    private ImageView bikeRideRing;
    @FXML
    private ImageView bikeRideInner;
    @FXML
    private ImageView bikeRideLock;

    @FXML
    private Pane root;
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
        showBadges();
        updateLevel();
    }

    /**
     * Display the badges of the achievements that the user has.
     */
    public void showBadges() {
        AnimationController animate = new AnimationController();
        //Animate achievement locks
        animate.grow(1, vegieLock, 0.3, 0.15 , 0.15);
        Tooltip vegieTip = new Tooltip("Log 10 Vegetarian Meals to unlock this achievement!");
        vegieTip.setShowDelay(Duration.seconds(0.1));
        vegieTip.setHideDelay(Duration.seconds(0));
        vegieTip.setStyle("-fx-font-size: 15;");
        vegieTip.setWrapText(true);
        vegieTip.install(vegieInner, vegieTip);

        animate.grow(1, localProduceLock, 0.1, 0.15 , 0.15);
        Tooltip localProduceTip = new Tooltip(
                "Log 10 Local Produce purchases to unlock this achievement!");
        localProduceTip.setShowDelay(Duration.seconds(0.1));
        localProduceTip.setHideDelay(Duration.seconds(0));
        localProduceTip.setStyle("-fx-font-size: 15;");
        localProduceTip.setWrapText(true);
        localProduceTip.install(localProduceInner, localProduceTip);

        animate.grow(1, bikeRideLock, 0.2, 0.15 , 0.15);
        Tooltip bikeRideTip = new Tooltip("Log 10 bike rides to unlock this achievement!");
        bikeRideTip.setShowDelay(Duration.seconds(0.1));
        bikeRideTip.setHideDelay(Duration.seconds(0));
        bikeRideTip.setStyle("-fx-font-size: 15;");
        bikeRideTip.setWrapText(true);
        bikeRideTip.install(bikeRideInner, bikeRideTip);

        animate.grow(1, publicTransportLock, 0.4, 0.15 , 0.15);
        Tooltip publicTransportTip = new Tooltip(
                "Log 10 public transport rides to unlock this achievement!");
        publicTransportTip.setShowDelay(Duration.seconds(0.1));
        publicTransportTip.setHideDelay(Duration.seconds(0));
        publicTransportTip.setStyle("-fx-font-size: 15;");
        publicTransportTip.setWrapText(true);
        publicTransportTip.install(publicTransportInner, publicTransportTip);

        animate.grow(1, solarPanelLock, 0.2, 0.15 , 0.15);
        Tooltip solarPanelTip = new Tooltip(
                "Log 10 months of producing solar energy to unlock this achievement!");
        solarPanelTip.setShowDelay(Duration.seconds(0.1));
        solarPanelTip.setHideDelay(Duration.seconds(0));
        solarPanelTip.setStyle("-fx-font-size: 15;");
        solarPanelTip.setWrapText(true);
        solarPanelTip.install(solarPanelInner, solarPanelTip);

        animate.grow(1, houseTempLock, 0.3, 0.15 , 0.15);
        Tooltip houseTempTip = new Tooltip(
                "Log 10 house temperature changes to unlock this achievement!");
        houseTempTip.setShowDelay(Duration.seconds(0.1));
        houseTempTip.setHideDelay(Duration.seconds(0));
        houseTempTip.setStyle("-fx-font-size: 15;");
        houseTempTip.setWrapText(true);
        houseTempTip.install(houseTempInner, houseTempTip);

        String name = Configuration.getInstance().username;
        String pass = Configuration.getInstance().password;
        User user = new User(name, pass);
        AchievementController controller = new AchievementController(user);
        ArrayList<Integer> activityList = controller.getAchievements(user);

        for (int id : activityList) {
            switch (id) {
                default:
                    break;
                case 1:
                    vegieRing.setOpacity(1);
                    vegieInner.setOpacity(1);
                    root.getChildren().removeAll(vegieLock);
                    animate.rotate(2.4, vegieRing, 0.2);
                    animate.grow(1, vegieInner, 0.2, 0.09,0.09);
                    Tooltip vmTip = new Tooltip("Awarded for eating 10 vegetarian meals!");
                    vmTip.setShowDelay(Duration.seconds(0.1));
                    vmTip.setHideDelay(Duration.seconds(0));
                    vmTip.setWrapText(true);
                    vmTip.setStyle("-fx-font-size: 15;");
                    vmTip.install(vegieInner, vmTip);
                    break;
                case 2:
                    localProduceRing.setOpacity(1);
                    localProduceInner.setOpacity(1);
                    root.getChildren().removeAll(localProduceLock);
                    animate.rotate(2.6, localProduceRing, 0.2);
                    animate.grow(1, localProduceInner, 0.2, 0.09,0.09);
                    Tooltip lpTip = new Tooltip("Awarded for buying 10 local products!");
                    lpTip.setShowDelay(Duration.seconds(0.1));
                    lpTip.setHideDelay(Duration.seconds(0));
                    lpTip.setWrapText(true);
                    lpTip.setStyle("-fx-font-size: 15;");
                    lpTip.install(localProduceInner, lpTip);
                    break;
                case 3:
                    bikeRideRing.setOpacity(1);
                    bikeRideInner.setOpacity(1);
                    root.getChildren().removeAll(bikeRideLock);
                    animate.rotate(2.2, bikeRideRing, 0.2);
                    animate.grow(1, bikeRideInner, 0.2, 0.09,0.09);
                    Tooltip brTip = new Tooltip("Awarded for travelling by bike 10 times!");
                    bikeRideTip.setShowDelay(Duration.seconds(0.1));
                    brTip.setHideDelay(Duration.seconds(0));
                    brTip.setWrapText(true);
                    brTip.setStyle("-fx-font-size: 15;");
                    brTip.install(bikeRideInner, brTip);
                    break;
                case 4:
                    publicTransportRing.setOpacity(1);
                    publicTransportInner.setOpacity(1);
                    root.getChildren().removeAll(publicTransportLock);
                    animate.rotate(2.7, publicTransportRing, 0.2);
                    animate.grow(1, publicTransportInner, 0.2, 0.09,0.09);
                    Tooltip ptTip = new Tooltip("Awarded using public transport 10 times!");
                    ptTip.setShowDelay(Duration.seconds(0.1));
                    ptTip.setHideDelay(Duration.seconds(0));
                    ptTip.setWrapText(true);
                    ptTip.setStyle("-fx-font-size: 15;");
                    ptTip.install(publicTransportInner, ptTip);
                    break;
                case 5:
                    houseTempRing.setOpacity(1);
                    houseTempInner.setOpacity(1);
                    root.getChildren().removeAll(houseTempLock);
                    animate.rotate(2.6, houseTempRing, 0.2);
                    animate.grow(1, houseTempInner, 0.2, 0.09,0.09);
                    Tooltip htTip = new Tooltip(
                            "Awarded for lowering the house temperature 10 times!");
                    htTip.setShowDelay(Duration.seconds(0.1));
                    htTip.setHideDelay(Duration.seconds(0));
                    htTip.setWrapText(true);
                    htTip.setStyle("-fx-font-size: 15;");
                    htTip.install(houseTempInner, htTip);
                    break;
                case 6:
                    solarPanelRing.setOpacity(1);
                    solarPanelInner.setOpacity(1);
                    root.getChildren().removeAll(solarPanelLock);
                    animate.rotate(2.5, solarPanelRing, 0.2);
                    animate.grow(1, solarPanelInner, 0.2, 0.09,0.09);
                    Tooltip spTip = new Tooltip(
                            "Awarded for producing solar energy for 10 months!");
                    spTip.setShowDelay(Duration.seconds(0.1));
                    spTip.setHideDelay(Duration.seconds(0));
                    spTip.setWrapText(true);
                    spTip.setStyle("-fx-font-size: 15;");
                    spTip.install(solarPanelInner, spTip);
                    break;
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

    public void goToFriends() {
        Main.activateScreen("friends.fxml");
    }

    public void goToScoreboard() {
        Main.activateScreen("scoreboard.fxml");
    }

    public void goToHome() {
        Main.activateScreen("home.fxml");
    }

}
