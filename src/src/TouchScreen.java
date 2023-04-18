import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TouchScreen {
    private final double WINDOW_WIDTH = 800;
    private final double WINDOW_HEIGHT = 600;
    private int audioLevel;
    private int brightnessLevel;
    private String masterLanguage;
    private int textSizeLevel;

    private Group root;
    private Scene scene;
    public TouchScreen() {
        // Default levels
        audioLevel = 5;
        brightnessLevel = 5;
        masterLanguage = "English";
        textSizeLevel = 5;

        this.root = new Group();
        setScene();
    }

    private void select() {

    }

    private void deselect() {

    }

    private void increaseBrightness() {
        if(brightnessLevel < 10){
            brightnessLevel += 1;
        }
        System.out.println("New Brightness Level " + brightnessLevel);

    }
    private void decreaseBrightness(){
        if(brightnessLevel > 0){
            brightnessLevel -= 1;
        }
        System.out.println("New Brightness Level " + brightnessLevel);
    }

    private void increaseTextSize() {
        if(textSizeLevel < 10){
            textSizeLevel += 1;
        }
        System.out.println("New Text Size " + textSizeLevel);

    }
    private void decreaseTextSize(){
        if(textSizeLevel > 0){
            textSizeLevel -= 1;
        }
        System.out.println("New Text Size " + textSizeLevel);
    }

    /**
     * TODO: This is called "displayLanguage()" in SRS
     */
    private void changeLanguage(String language) {
        masterLanguage = language;
        System.out.println("New Master Language " + masterLanguage);
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
        setAccessibilityLayout();
        setExternalLED();


        //set scene
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public Scene getScene() {
        return scene;
    }

    private void setAccessibilityLayout() {
        Menu menu = new Menu("Accessibility");

        Menu audioMenu = new Menu("Audio Level");
        MenuItem volumeInc = new MenuItem("Volume +1");
        MenuItem volumeDec = new MenuItem("Volume -1");
        volumeInc.setOnAction(e -> Audio.increaseVolume());
        volumeDec.setOnAction(e -> Audio.decreaseVolume());
        audioMenu.getItems().add(volumeInc);
        audioMenu.getItems().add(volumeDec);

        Menu textSizeMenu = new Menu("Text Size");
        MenuItem textSizeInc = new MenuItem("Text Size +1");
        MenuItem textSizeDec = new MenuItem("Text Size -1");
        textSizeInc.setOnAction(e -> increaseTextSize());
        textSizeDec.setOnAction(e -> decreaseTextSize());
        textSizeMenu.getItems().add(textSizeInc);
        textSizeMenu.getItems().add(textSizeDec);

        Menu brightnessMenu = new Menu("Brightness");
        MenuItem brightnessInc = new MenuItem("Brightness +1");
        MenuItem brightnessDec = new MenuItem("Brightness -1");
        brightnessInc.setOnAction(e -> increaseBrightness());
        brightnessDec.setOnAction(e -> decreaseBrightness());
        brightnessMenu.getItems().add(brightnessInc);
        brightnessMenu.getItems().add(brightnessDec);

        Menu languageMenu = new Menu("Language");
        MenuItem english = new MenuItem("English");
        MenuItem spanish = new MenuItem("Spanish");
        MenuItem mandarin = new MenuItem("Mandarin");
        english.setOnAction(e -> changeLanguage("English"));
        spanish.setOnAction(e -> changeLanguage("Spanish"));
        mandarin.setOnAction(e -> changeLanguage("Mandarin"));
        languageMenu.getItems().add(english);
        languageMenu.getItems().add(spanish);
        languageMenu.getItems().add(mandarin);

        menu.getItems().add(textSizeMenu);
        menu.getItems().add(brightnessMenu);
        menu.getItems().add(languageMenu);

        MenuBar menuBar = new MenuBar(menu);
        menuBar.setTranslateX(menuBar.getTranslateX() + 600);
        menuBar.setTranslateY(menuBar.getLayoutY() + 40);
        root.getChildren().add(menuBar);
    }


    private void setExternalLED(){
        Circle externalLED = new Circle(20);
        externalLED.setFill(Color.YELLOW);
        externalLED.setTranslateX(externalLED.getTranslateX() + 30);
        externalLED.setTranslateY(externalLED.getTranslateY() + 20);

        root.getChildren().add(externalLED);
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