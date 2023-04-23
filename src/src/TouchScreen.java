import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TouchScreen {
    private final double WINDOW_WIDTH = 1000;
    private final double WINDOW_HEIGHT = 600;
    private static int brightnessLevel;
    private static String masterLanguage;
    private double textSizeLevel = 12;
    private VirtualKeypad keyboard = new VirtualKeypad();
    ColorAdjust colorAdjust;
    Rectangle background = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    private List<Text> textList = new ArrayList<>();


    private Group root;
    private Scene scene;
    public TouchScreen() {
        // Default levels
        this.colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        masterLanguage = "English";
        textSizeLevel = 12;
        Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        this.root = new Group();
        this.root.getChildren().add(background);
        setScene();
    }

    private void select() {

    }

    private void deselect() {

    }

    private void increaseBrightness() {
        colorAdjust.setBrightness(this.colorAdjust.getBrightness() + 0.1);
        System.out.println("New Brightness Level " + colorAdjust.getBrightness());
        root.setEffect(colorAdjust);
        background.setEffect(colorAdjust);
    }
    private void decreaseBrightness(){
        colorAdjust.setBrightness(this.colorAdjust.getBrightness() - 0.1);
        System.out.println("New Brightness Level " + colorAdjust.getBrightness());
        root.setEffect(colorAdjust);
        background.setEffect(colorAdjust);
    }

    private void increaseTextSize() {
        if (textSizeLevel > 22) {
            return;
        } else {
            for (Text text : textList) {
                text.setFont(new Font(textSizeLevel));
            }
            textSizeLevel += 2;
        }
    }
    private void decreaseTextSize(){
        if (textSizeLevel < 6) {
            return;
        } else {
            for (Text text : textList) {
                text.setFont(new Font(textSizeLevel));
            }
            textSizeLevel -= 2;
        }
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
        Text prompt = new Text("This is a prompt...");
        promptBox.getChildren().add(prompt);  // Fluff
        promptBox.setTranslateX(promptBox.getTranslateX() + xPos);
        promptBox.setTranslateY(promptBox.getTranslateY() + yPos);
        promptBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        promptBox.setPadding(new Insets(10));
        textList.add(prompt);
        yPos += 50;

        // 2. Choices
        RadioButton[] choices = new RadioButton[numOpts+1];
        for(int i = 0; i < numOpts; i++){
            choices[i] = new RadioButton();
            Text text = new Text("Answer Choice " + i);  // Create Text Obj
            choices[i] = setGUIFormat(choices[i], text, xPos, yPos);
            yPos += 50;  // Slides box down +50 pixels
        }

        // 3. Write-in
        RadioButton writeIn = new RadioButton();
        Text writeInText = new Text("Write-in Field...");  // Create Text Obj
        writeIn = setGUIFormat(writeIn, writeInText, xPos, yPos);


        // 4. Set Actions on each radio button
        choices[choices.length-1] = writeIn;
        choiceOnClick(choices);

        // 4. Add questionnaire to GUI display.
        root.getChildren().addAll(promptBox);
        for(Node c : choices){
            root.getChildren().add(c);
        }
    }

    /**
     * Used for Answer Choices
     * @param rb Radio Button
     * @param textField text inside radio button
     * @param xPos x-position
     * @param yPos y-position
     * @return
     */
    private RadioButton setGUIFormat(RadioButton rb, Text textField, int xPos, int yPos){
        textList.add(textField);
        rb.setText(textField.getText());  // Add Text Obj
        rb.setTranslateX(rb.getTranslateX() + xPos);
        rb.setTranslateY(rb.getTranslateY() + yPos);
        rb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        rb.setPadding(new Insets(10));

        return rb;
    }

    private void nextPage() {
        root.getChildren().clear();  // Clear GUI
        setScene();  // Add GUI items
    }

    private void previousPage() {
        root.getChildren().clear();  // Clear GUI
        setScene();  // Add GUI items
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
        setNextBackBtns();
        addVirtualKeyboardToRoot();
        //set scene
        if(scene == null){
            scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        }
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
        audioMenu.getItems().addAll(volumeInc, volumeDec);

        Menu textSizeMenu = new Menu("Text Size");
        MenuItem textSizeInc = new MenuItem("Text Size +1");
        MenuItem textSizeDec = new MenuItem("Text Size -1");
        textSizeInc.setOnAction(e -> increaseTextSize());
        textSizeDec.setOnAction(e -> decreaseTextSize());
        textSizeMenu.getItems().addAll(textSizeInc, textSizeDec);

        Menu brightnessMenu = new Menu("Brightness");
        MenuItem brightnessInc = new MenuItem("Brightness +1");
        MenuItem brightnessDec = new MenuItem("Brightness -1");
        brightnessInc.setOnAction(e -> increaseBrightness());
        brightnessDec.setOnAction(e -> decreaseBrightness());
        brightnessMenu.getItems().addAll(brightnessInc, brightnessDec);

        Menu languageMenu = new Menu("Language");
        MenuItem english = new MenuItem("English");
        MenuItem spanish = new MenuItem("Spanish");
        MenuItem mandarin = new MenuItem("Mandarin");
        english.setOnAction(e -> changeLanguage("English"));
        spanish.setOnAction(e -> changeLanguage("Spanish"));
        mandarin.setOnAction(e -> changeLanguage("Mandarin"));
        languageMenu.getItems().addAll(english, spanish, mandarin);

        menu.getItems().addAll(textSizeMenu, brightnessMenu, languageMenu);

        MenuBar menuBar = new MenuBar(menu);
        menuBar.setTranslateX(menuBar.getTranslateX() + 600);
        menuBar.setTranslateY(menuBar.getLayoutY() + 40);
        root.getChildren().add(menuBar);
    }

    private void setNextBackBtns(){
        Button nextBtn = new Button();
        Text nextText = new Text("Next");
        nextBtn.setText(nextText.getText());
        textList.add(nextText);
        nextBtn.setOnAction(e -> nextPage());
        nextBtn.setTranslateX(nextBtn.getTranslateX() + 550);
        nextBtn.setTranslateY(nextBtn.getTranslateY() + 300);

        Button backBtn = new Button();
        Text backText = new Text("Back");
        backBtn.setText(backText.getText());
        textList.add(backText);
        backBtn.setOnAction(e -> previousPage());
        backBtn.setTranslateX(backBtn.getTranslateX() + 150);
        backBtn.setTranslateY(backBtn.getTranslateY() + 300);

        root.getChildren().addAll(nextBtn, backBtn);
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

    private void addVirtualKeyboardToRoot() {
        root.getChildren().add(keyboard);
    }

    private static void choiceOnClick(RadioButton[] choices) {
        //turn the selected choice's background to green and the rest to white
        for (RadioButton choice : choices) {
            choice.setOnMouseClicked(e -> {
                for (RadioButton c : choices) {
                    c.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                choice.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            });
        }
    }

}