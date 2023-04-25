import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TouchScreen {
    private final double WINDOW_WIDTH = 800;
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

    private void select(MouseEvent e) {
        RadioButton rb;

        EventTarget target = e.getTarget();
        if(target instanceof HBox){
            HBox choice = (HBox) target;
            rb = (RadioButton) choice.getChildren().get(0);
        }else{
            Text text = (Text) target;
            rb = (RadioButton) text.getParent().getChildrenUnmodifiable().get(0);
        }

        rb.setSelected(true);
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

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

            }
        });

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
        HBox[] choices = new HBox[numOpts+1];
        for(int i = 0; i < numOpts; i++){
            choices[i] = new HBox();

            RadioButton rb = new RadioButton();
            rb.setToggleGroup(toggleGroup);
            choices[i].getChildren().add(rb);
            choices[i].setOnMousePressed(this::select);

            Text text = new Text("\t" + "Answer Choice " + i);  // Create Text Obj
            choices[i] = setGUIFormat(choices[i], text, xPos, yPos);

            yPos += 50;  // Slides box down +50 pixels
        }

        // 3. Write-in
        HBox writeIn = new HBox();
        RadioButton rb = new RadioButton();
        rb.setToggleGroup(toggleGroup);
        writeIn.getChildren().add(rb);
        writeIn.setOnMousePressed(this::select);
        Text writeInText = new Text("\t" + "Write-in Field...");  // Create Text Obj
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

    private HBox setGUIFormat(HBox hbox, Text textField, int xPos, int yPos) {
        textList.add(textField);
        hbox.getChildren().add(textField);
        hbox.setTranslateX(hbox.getTranslateX() + xPos);
        hbox.setTranslateY(hbox.getTranslateY() + yPos);
        hbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        hbox.setPadding(new Insets(10));

        return hbox;
    }

    private void nextPage() {
        // Clear GUI
        root.getChildren().clear();

        // Add stuff to GUI
        setScene();
    }

    private void previousPage() {
        // Clear GUI
        root.getChildren().clear();

        // Add stuff to GUI
        setScene();
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
        addNextBackBtns();
        addVirtualKeyboardToRoot();
        //set scene, if scene not initialized yet.
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

    private void addNextBackBtns(){
        Button nextBtn = new Button("Next");
        nextBtn.setTranslateX(nextBtn.getTranslateX() + 250);
        nextBtn.setOnAction(e -> nextPage());

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> previousPage());

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

    private static void choiceOnClick(HBox[] choices) {
        //turn the selected choice's background to green and the rest to white
        for (HBox choice : choices) {
            choice.setOnMouseClicked(e -> {
                for (HBox c : choices) {
                    c.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                choice.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            });
        }
    }

}