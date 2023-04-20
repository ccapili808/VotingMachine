import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TouchScreen {
    //BELOW ARE VARIABLES FOR JAVAFX
    private final double WINDOW_WIDTH = 1000;
    private final double WINDOW_HEIGHT = 800;
    private Button audioBtn;
    private Button brightnessBtn;
    private Button languageBtn;
    private Button textSizeBtn;
    private TextArea textArea;
    private Group root;
    private Scene scene;
    //END OF VARIABLES FOR JAVAFX

    private int brightness = 50; //Default brightness
    private int textSize = 12; //Default text size
    private int volume = 50; //Default volume
    private String language = "English"; //Default language

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

    private void changeTextSize(boolean increase) {
        if (increase) {
            textSize += 2;
        } else {
            textSize -= 2;
        }
        textArea.setStyle("-fx-font-size: " + textSize + "px;");
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
        setMenuLayout();
        setTextArea();

        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public Scene getScene() {
        return scene;
    }

    private void setTextArea() {
        textArea = new TextArea();
        textArea.setPrefSize(300, 300);
        textArea.setLayoutX(50);
        textArea.setLayoutY(50);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        textArea.setText("Welcome to the voting machine. Please insert your card to begin.");
        root.getChildren().add(textArea);
    }


    private void setMenuLayout() {
        //Action buttons will be at the bottom of the screen in a horizontal layout
        HBox actionButtons = new HBox();
        actionButtons.setSpacing(20);
        actionButtons.setLayoutX(50);
        actionButtons.setLayoutY(WINDOW_HEIGHT - 325);
        actionButtons.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        //Create buttons
        audioBtn = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\162-1623769_volume-up-volume-up-icon.png");
        brightnessBtn = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\brightnessDown.png");
        textSizeBtn = createButtonWithImage("C:\\Users\\Chai\\Documents\\VotingMachine\\src\\Images\\textSize.png");
        //Set button actions
        setFontSizeBtn(textSizeBtn);
        //Add buttons to layout
        actionButtons.getChildren().addAll(audioBtn, brightnessBtn, textSizeBtn, languageMenu());
        //Add layout to root
        root.getChildren().add(actionButtons);
        Rectangle border = new Rectangle(WINDOW_WIDTH - 300, WINDOW_HEIGHT - 300);
        border.setStroke(Color.BLACK);
        border.setFill(null);
        border.setTranslateX(50);
        border.setTranslateY(50);
        // Add the rectangle to the group
        root.getChildren().add(border);
    }

    private MenuButton languageMenu() {
        //Languages may need to be changed from this list
        String[] languages = {"English", "Spanish", "Mandarin"};
        MenuButton language = new MenuButton("Language");
        for (String lang : languages) {
            MenuItem languageItem = new MenuItem(lang);
            languageItem.setOnAction(e -> changeLanguage());
            language.getItems().add(languageItem);
        }
        language.setPrefHeight(50);
        return language;
    }

    /**
     *
     * @param pathToImage - path to image file
     * @return - button with image, as well as on click for + and - buttons
     */
    private Button createButtonWithImage(String pathToImage) {
        Image image = new Image(pathToImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        Button buttonWithImage = new Button();
        buttonWithImage.setGraphic(imageView);
        buttonWithImage.setPrefHeight(50);
        buttonWithImage.setPrefWidth(50);
        return buttonWithImage;
    }

    private void setAudioBtn(Button audioBtn) {
        audioBtn.setOnAction(e -> changeVolume());
    }

    private void setFontSizeBtn(Button fontSizeBtn) {fontSizeBtn.setOnAction(e -> changeTextSize(true));}




}
