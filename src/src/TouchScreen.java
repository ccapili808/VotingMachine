import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TouchScreen {
    private final double WINDOW_WIDTH = 800;
    private final double WINDOW_HEIGHT = 600;
    private Button audioBtn;
    private Button brightnessBtn;
    private Button languageBtn;
    private Button textSizeBtn;
    private Group root;
    private Scene scene;
    public TouchScreen() {
        this.root = new Group();
        setScene();
    }

    private void select() {

    }

    private void deselect() {

    }

    private void changeVolume() {

    }

    private void changeBrightness() {

    }

    private void changeTextSize() {

    }

    private void changeLanguage() {

    }

    private void nextPage() {

    }

    private void previousPage() {

    }

    private void spoilBallot() {

    }

    private void printBallot() {

    }

    private void castBallot() {

    }

    /*
    ----------------------------
    BELOW IS CODE USED FOR JAVAFX
    ----------------------------
     */

    private void setScene() {
        //fill root with layout
        setMenuLayout();
        //set scene
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public Scene getScene() {
        return scene;
    }

    private void setMenuLayout() {
        //Action buttons will be at the bottom of the screen in a horizontal layout
        HBox actionButtons = new HBox();
        actionButtons.setSpacing(10);
        actionButtons.setLayoutX(0);
        actionButtons.setLayoutY(WINDOW_HEIGHT - 100);
        //Create buttons
//        Button volumeDown = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\volumeDown.png");
//        Button volumeUp = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\162-1623769_volume-up-volume-up-icon.png");
//        Button brightnessDown = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\brightnessDown.png");
//        Button brightnessUp = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\brightnessUp.png");
//        Button textSize = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\textSize.png");
        //Add buttons to layout
//        actionButtons.getChildren().addAll(volumeDown, volumeUp, brightnessDown, brightnessUp, textSize, languageMenu());
        //Add layout to root
        root.getChildren().add(actionButtons);
    }

    private MenuButton languageMenu() {
        //Languages may need to be changed from this list
        String[] languages = {"English", "Spanish", "French", "German", "Italian", "Portuguese", "Russian", "Chinese"};
        MenuButton language = new MenuButton("Language");
        for (String lang : languages) {
            MenuItem languageItem = new MenuItem(lang);
            languageItem.setOnAction(e -> changeLanguage());
            language.getItems().add(languageItem);
        }
        language.setPrefHeight(50);
        return language;
    }

    private Button createButtonWithImage(String pathToImage) {
        Image image = new Image(pathToImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        Button buttonWithImage = new Button();
        buttonWithImage.setGraphic(imageView);
        return buttonWithImage;
    }




}
