import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TouchScreen {
    private final double WINDOW_WIDTH = 800;
    private final double WINDOW_HEIGHT = 600;
    private static int brightnessLevel;
    private static String masterLanguage;
    private static int textSizeLevel;

    private Group root;
    private Scene scene;
    public TouchScreen() {
        // Default levels
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
        if(brightnessLevel < 10){brightnessLevel += 1;}
        System.out.println("New Brightness Level " + brightnessLevel);

    }
    private void decreaseBrightness(){
        if(brightnessLevel > 0){brightnessLevel -= 1;}
        System.out.println("New Brightness Level " + brightnessLevel);
    }

    private void increaseTextSize() {
        if(textSizeLevel < 10){textSizeLevel += 1;}
        System.out.println("New Text Size " + textSizeLevel);

    }
    private void decreaseTextSize(){
        if(textSizeLevel > 0){textSizeLevel -= 1;}
        System.out.println("New Text Size " + textSizeLevel);
    }

    /**
     * TODO: This is called "displayLanguage()" in SRS
     */
    private void changeLanguage(String language) {
        masterLanguage = language;
        System.out.println("New Master Language " + masterLanguage);
    }

    /**
     * TODO: Give this function args (promptString, choices[], etc.) from
     *   some type of "Items obj". Then just insert each arg in it's respective
     *   HBox below.
     */
    private void displayQuestion(){
        // TODO: Right here would be a perfect location to translate
        //  everything before putting them into their HBoxes :)

        int numOpts = 3;  // Creates n-many option choices, not including write-in
        int xPos = 300;  // Aligning
        int yPos = 200;  // Aligning

        // TODO: Add ID's to each thingy...how should ID's be formatted?
        // 1. Prompt
        HBox promptBox = new HBox();
        promptBox.getChildren().add(new Text("This is a prompt..."));  // Fluff
        promptBox.setTranslateX(promptBox.getTranslateX() + xPos);
        promptBox.setTranslateY(promptBox.getTranslateY() + yPos);
        promptBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        promptBox.setPadding(new Insets(10));
        yPos += 50;

        // 2. Choices
        HBox[] choices = new HBox[numOpts];
        for(int i = 0; i < numOpts; i++){
            choices[i] = new HBox();
            choices[i].getChildren().add(new Text("Answer Choice " + i));  // Fluff
            choices[i].setTranslateX(choices[i].getTranslateX() + xPos);
            choices[i].setTranslateY(choices[i].getTranslateY() + yPos);
            choices[i].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
            choices[i].setPadding(new Insets(10));

            yPos += 50;  // Slides box down +50 pixels
        }

        // 3. Write-in
        HBox writeIn = new HBox();
        writeIn.getChildren().add(new Text("Write-in Field..."));  // Fluff
        writeIn.setTranslateX(writeIn.getTranslateX() + xPos);
        writeIn.setTranslateY(writeIn.getTranslateY() + yPos);
        writeIn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        writeIn.setPadding(new Insets(10));

        // 4. Add questionnaire to GUI display.
        root.getChildren().addAll(promptBox, writeIn);
        for(Node c : choices){
            root.getChildren().add(c);
        }
    }

    private void nextPage() {
        displayQuestion();
    }

    private void previousPage() {
        displayQuestion();
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

        // TEST: Give questions
        displayQuestion();

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