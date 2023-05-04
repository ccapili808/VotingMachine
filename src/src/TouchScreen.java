import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class TouchScreen {
    private final double SCENE_WIDTH = 1400;
    private final double SCENE_HEIGHT = 800;
    private final double WINDOW_WIDTH = 800;
    private final double WINDOW_HEIGHT = 600;
    private static int brightnessLevel;
    private static String masterLanguage = "English";
    private double textSizeLevel;
    private VirtualKeypad keyboard = new VirtualKeypad();
    ColorAdjust colorAdjust;
    Rectangle background;
    private List<Text> textList = new ArrayList<>();
    private boolean isScreenOn = true;

    private Button nextBtn;
    private Button backBtn;

    //This is where we will temporarily store prompts and options passed in
    List<String> prompts;
    Map<String, List<String>> options; //Key is prompt, value is list of options
    //This is where we will temporarily store the selected options
    Map<String, String> selectedOptions; //Key is prompt, value is list of selected options
    VBox[] pages;
    int currentPage = 0;

    //This group is so that gui objects outside of touch screen can be added to the scene
    //without being affected by the functionality of touchscreen.
    private Group mainRoot;

    private Group root;
    private Scene scene;
    private TranslatorV2 translator = new TranslatorV2();
    public TouchScreen() {
        // Default levels
        this.colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        //masterLanguage = "English";
        this.textSizeLevel = 12;
        this.root = new Group();
        this.mainRoot = new Group();
        mainRoot.setTranslateX(50);
        mainRoot.setTranslateY(100);
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
        if (textSizeLevel > 18) {
            return;
        } else {
            textSizeLevel += 2;
            for (Text text : textList) {
                text.setFont(new Font(textSizeLevel));
            }
        }
    }
    private void decreaseTextSize(){
        if (textSizeLevel < 6) {
            return;
        } else {
            textSizeLevel -= 2;
            for (Text text : textList) {
                text.setFont(new Font(textSizeLevel));
            }
        }
    }

    /**
     * TODO: This is called "displayLanguage()" in SRS
     */
    private void changeLanguage(String language) {
        masterLanguage = language;

        if (!Objects.equals(masterLanguage, "English")){
            Item currentPrompt = Main.getPrompt(currentPage+1);
            System.out.println("Changing Language on Page #" + currentPage);
            String str = "";

            for(Text text: textList){
                System.out.print("\t" + text.getText() + " --> ");

                if(!Objects.equals(currentPrompt.getItemType(), "Contest") || !Objects.equals(text.getId(), "answerChoice")){
                    str = TranslatorV2.translateLanguage(text.getText(), masterLanguage);
                }else{
                    str = text.getText();
                }

                System.out.println(str);
                text.setText(str);  // Update Text Label
            }
        }
    }

    /**
     * Simple Welcome Page. Does not allow Text Size changes.
     * @return The Welcome Page as VBox
     */
    private VBox welcomePage(){
        VBox welcomePage = new VBox();
        welcomePage.setTranslateX(WINDOW_WIDTH / 2 - 100);
        welcomePage.setTranslateY(WINDOW_HEIGHT - 550);

        Text greeting = new Text("    " + "Welcome!\n");
        Text ballotTitle = new Text(Main.getBallotTitle());
        Text date = new Text("\t\t\t" + Main.getBallotDate());
        Text instructions = new Text("\n    " + Main.getBallotInstr() + "\n");
        Text card = new Text("Insert your Vote Authorization Card");
        instructions.setWrappingWidth(200);
        card.setWrappingWidth(200);

        greeting.setFont(Font.font(36));
        card.setFont(Font.font(24));
        ballotTitle.setFont(Font.font(24));
        date.setFont(Font.font(14));
        instructions.setFont(Font.font(20));
        greeting.setTextAlignment(TextAlignment.CENTER);
        card.setTextAlignment(TextAlignment.CENTER);
        ballotTitle.setTextAlignment(TextAlignment.CENTER);
        date.setTextAlignment(TextAlignment.CENTER);
        instructions.setTextAlignment(TextAlignment.CENTER);

        welcomePage.getChildren().addAll(greeting, ballotTitle, date, instructions, card);

        return welcomePage;
    }


    private VBox printPage(){
        VBox overviewPage = new VBox();
        overviewPage.setSpacing(20);

        Text title = new Text("\t\t\t Print Ballot");
        Text prompt = new Text("""
                You cannot change your vote selections after printing your ballot.

                 To continue and print your ballot, touch 'Print'. To return to the ballot, touch 'Return to Ballot'.""");
        prompt.setWrappingWidth(300);

        title.setFont(Font.font(20));
        prompt.setFont(Font.font(16));
        title.setTextAlignment(TextAlignment.CENTER);
        prompt.setTextAlignment(TextAlignment.CENTER);
        overviewPage.getChildren().add(title);

        GridPane grid = new GridPane();
        grid.setHgap(70);
        for(int i = 1; i < pages.length-3; i++){
            System.out.print(Main.getPrompt(i).getItemName() + " --> ");
            System.out.println(Main.getPrompt(i).getSelection());

            Item item = Main.getPrompt(i);

            grid.add(new Text(item.getItemName()), 0, i-1);
            try{
                grid.add(new Text(item.getSelection()), 1, i-1);
            }catch (Exception e){
                grid.add(new Text("{EMPTY}"), 1, i-1);
            }
        }


        HBox optBtns = new HBox();
        optBtns.setSpacing(100);
        Button returnBtn = new Button("Return to Ballot");

        //add return button functionality
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPage = 0;
                nextPage();
                overviewPage.setVisible(false);
            }
        });


        Button printBtn = new Button("Print");

        //add print button functionality
        printBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pages[currentPage].setVisible(false);
                nextPage();
                printBallot();
            }
        });
        returnBtn.setPrefWidth(100);
        printBtn.setPrefWidth(100);

        optBtns.getChildren().addAll(returnBtn, printBtn);
        overviewPage.getChildren().addAll(grid, prompt, optBtns);

        overviewPage.setTranslateX(WINDOW_WIDTH / 2 - 150);
        overviewPage.setTranslateY(WINDOW_HEIGHT - 400);

        return overviewPage;
    }


    private VBox castPage(){
        VBox castPage = new VBox();

        castPage.setSpacing(20);

        Text title = new Text("\t\t\t Cast Ballot");
        Text prompt = new Text("""
                Review your ballot through the window
                 to the right.
                To cast your ballot as printed, touch 'Cast'.
                
                To quit voting and request a new ballot, touch 'Quit'. Your ballot will not be cast.""");
        prompt.setWrappingWidth(300);

        title.setFont(Font.font(20));
        prompt.setFont(Font.font(16));
        title.setTextAlignment(TextAlignment.CENTER);
        prompt.setTextAlignment(TextAlignment.CENTER);


        HBox optBtns = new HBox();
        optBtns.setSpacing(100);
        Button quitBtn = new Button("Quit");  // TODO: This should throw some sort of "Voided Ballot" page
        Button castBtn = new Button("Cast");

        //add cast button functionality
        castBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                castVotes();
                pages[currentPage].setVisible(false);
                currentPage = -1;
                Main.clearSetUpInfo();
                nextPage();
                resetRoot();
            }
        });

        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pages[currentPage].setVisible(false);
                nextPage();
            }
        });

        quitBtn.setPrefWidth(100);
        castBtn.setPrefWidth(100);

        optBtns.getChildren().addAll(quitBtn, castBtn);


        castPage.getChildren().addAll(title, prompt, optBtns);

        castPage.setTranslateX(WINDOW_WIDTH / 2 - 150);
        castPage.setTranslateY(WINDOW_HEIGHT - 400);

        return castPage;
    }

    private VBox spoilPage(){
        VBox spoilPage = new VBox();

        Main.getPrinter().spoilCard();

        spoilPage.setSpacing(20);

        Text title = new Text("\t\t Spoil Ballot");
        Text prompt = new Text("""
                Please ask for help from a poll worker.
                """);
        prompt.setWrappingWidth(300);

        title.setFont(Font.font(20));
        prompt.setFont(Font.font(16));
        title.setTextAlignment(TextAlignment.CENTER);
        prompt.setTextAlignment(TextAlignment.CENTER);

        TextField adminPrompt = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (adminPrompt.getText().equals("123456789")) {
                    System.out.println("Spoiled Ballot");
                    pages[currentPage].setVisible(false);
                    currentPage = -1;
                    Main.clearSetUpInfo();
                    nextPage();
                    resetRoot();
                }
            }
        });

        spoilPage.getChildren().addAll(title, prompt, adminPrompt,submit);

        spoilPage.setTranslateX(WINDOW_WIDTH / 2 - 150);
        spoilPage.setTranslateY(WINDOW_HEIGHT - 400);

        return spoilPage;
    }


    /**
     * TODO: Give this function args (promptString, choices[], etc.) from
     *   some type of "Items obj". Then just insert each arg in it's respective
     *   HBox below.
     */
    private VBox displayQuestion(String promptString, String[] choiceList, boolean proposition) {

        int numOpts = choiceList.length;  // Creates n-many option choices, not including write-in
        VBox page = new VBox();
        page.setSpacing(10);


        // 1. Prompt
        HBox promptBox = new HBox();
        //Add prompts here:
        promptString = TranslatorV2.translateLanguage(promptString, masterLanguage);
        Text prompt = new Text(promptString);
        prompt.setWrappingWidth(200);
        prompt.setId("prompt");
        promptBox.getChildren().add(prompt);  // Fluff
        promptBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        promptBox.setPadding(new Insets(10));
        textList.add(prompt);
        HBox[] choices;
        // 2. Choices
        // TODO: Figure out why TranslatorV2 won't translate?!  乁( ⁰͡ Ĺ̯ ⁰͡ ) ㄏ
        if (proposition) {
            choices = new HBox[numOpts];
        }
        else {
            choices = new HBox[numOpts+1];
        }
        String answerString = "";
        for(int i = 0; i < numOpts; i++){
            if(proposition){  // Only Translate Propositions (Yes & No)
                answerString = TranslatorV2.translateLanguage(choiceList[i], masterLanguage);
            }else{
                answerString = choiceList[i];
            }

            choices[i] = new HBox();
            choices[i] = setGUIFormat(choices[i], answerString);
        }

        // 3. Write-in if contest
        if (!proposition) { //TODO: This only stores the vote if the button is clicked again after a write in is stored
            HBox writeIn = new HBox();
            String writeInString = TranslatorV2.translateLanguage("Write-in Field: ", masterLanguage);
            RadioButton rb = new RadioButton();
            writeIn.getChildren().add(rb);
            Text text = new Text("\t" + writeInString);  // Create Text Obj
            text.setId("answerChoice");
            writeIn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
            writeIn.getChildren().add(text);
            textList.add(text);
            writeIn.setPadding(new Insets(10));

            writeIn.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Item currentItem = Main.getPrompt(currentPage);
                    keyboard.showKeyboard(true);
                    keyboard.setOriginalText("\t" + writeInString);
                    keyboard.setTextFieldToUpdate(text, currentItem);
                }
            });

            choices[choices.length-1] = writeIn;
        }

        // 4. Set Actions on each radio button
        choiceOnClick(choices);

        // 4. Add questionnaire to GUI display.
        page.getChildren().add(promptBox);
        for(Node c : choices){
            page.getChildren().add(c);
        }
        page.setTranslateX(WINDOW_WIDTH / 2 - 100);
        page.setTranslateY(WINDOW_HEIGHT - 400);
        return page;
    }

    private HBox setGUIFormat(HBox hbox, String str) {
        RadioButton rb = new RadioButton();
        hbox.getChildren().add(rb);
        hbox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                keyboard.setVisible(false);
            }
        });
        //hbox.setOnMousePressed(this::select);
        Text text = new Text("\t" + str);  // Create Text Obj
        text.setId("answerChoice");
        hbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,null)));
        hbox.getChildren().add(text);
        textList.add(text);

        hbox.setPadding(new Insets(10));

        return hbox;
    }

    public void cardInserted() {
        if (currentPage == 0) {
            nextPage();
        }
    }

    private void nextPage() {
        keyboard.setVisible(false);
        keyboard.clearTextField();
        if(currentPage < pages.length - 4){
            if (currentPage != -1) {
                pages[currentPage].setVisible(false);
            }
            currentPage++;
            nextBtn.setVisible(true);
            backBtn.setVisible(true);
            pages[currentPage].setVisible(true);
        }else if (currentPage == pages.length - 4){
            pages[currentPage].setVisible(false);
            currentPage++;

            pages[currentPage] = printPage();  // Add to pages
            root.getChildren().add(pages[currentPage]);
            nextBtn.setVisible(false);
            backBtn.setVisible(false);
            pages[currentPage].setVisible(true);
        }else if (currentPage == pages.length - 3){
            pages[currentPage].setVisible(false);
            currentPage++;

            pages[currentPage] = castPage();  // Add to pages
            root.getChildren().add(pages[currentPage]);

            pages[currentPage].setVisible(true);
        }else if (currentPage == pages.length - 2){
            pages[currentPage].setVisible(false);
            currentPage++;

            pages[currentPage] = spoilPage();  // Add to pages
            root.getChildren().add(pages[currentPage]);

            pages[currentPage].setVisible(true);
        }
        changeLanguage(masterLanguage);
        updateSelections();
    }

    private void previousPage() {
        //go to previous page and set all other pages in JavaFX to invisible
        //if first page go to the last page
        //you can use the VBox[] pages where each Vbox has an id of "page" + i
        //where i is the page number
        //you can use the method setVisible(boolean) to set the page to visible or invisible
        keyboard.setVisible(false);
        keyboard.clearTextField();
        if(currentPage > 1){
            pages[currentPage].setVisible(false);
            currentPage--;
            pages[currentPage].setVisible(true);
        }else{
//            pages[currentPage].setVisible(false);
//            currentPage = pages.length - 1;
//            pages[currentPage].setVisible(true);
        }
        changeLanguage(masterLanguage);
        updateSelections();
    }

    private void updateSelections() {

    }

    private void resetRoot() {
        // Clear GUI
        root.getChildren().clear();
        // Add stuff to GUI
        setScene();
    }

    private void spoilBallot() {

    }

    private void printBallot() {
        Main.getPrinter().printVoteSelection();
    }

    private void castVotes() {
        Main.getStorage().saveVote();
        Main.getVoteAuthorizationCardScanner().storeCard();
    }

    /*
    ----------------------------
    BELOW IS CODE USED FOR JAVAFX
    ----------------------------
     */

    private void setScene() {
        background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        background.setStroke(Color.BLACK);
        root.getChildren().add(background);
        //fill root with layout
        setAccessibilityLayout();
        // TEST: Give questions
        setPages();
        addNextBackBtns();
        addVirtualKeyboardToRoot();
        Button hideOrShowKeyboard = hideOrShowKeyboard(keyboard);
        hideOrShowKeyboard.setTranslateX(WINDOW_WIDTH - 100);
        hideOrShowKeyboard.setTranslateY(WINDOW_HEIGHT - 100);
        root.getChildren().add(hideOrShowKeyboard);
        if(!mainRoot.getChildren().contains(root)){
            mainRoot.getChildren().add(root);
        }
        //set scene, if scene not initialized yet.
        if(scene == null){
            scene = new Scene(mainRoot, SCENE_WIDTH, SCENE_HEIGHT);
        }
    }
    public Scene getScene() {
        return scene;
    }

    public Group getRoot(){
        return mainRoot;
    }

    private void setAccessibilityLayout() {
        Menu menu = new Menu(TranslatorV2.translateLanguage("Accessibility", masterLanguage));
        Menu audioMenu = new Menu(TranslatorV2.translateLanguage("Audio", masterLanguage));
        String volume = TranslatorV2.translateLanguage("Volume", masterLanguage);
        MenuItem volumeInc = new MenuItem(volume + " +1");
        MenuItem volumeDec = new MenuItem(volume + " -1");
        volumeInc.setOnAction(e -> Audio.increaseVolume());
        volumeDec.setOnAction(e -> Audio.decreaseVolume());
        audioMenu.getItems().add(volumeInc);
        audioMenu.getItems().add(volumeDec);

        String textSize = TranslatorV2.translateLanguage("Text Size", masterLanguage);
        Menu textSizeMenu = new Menu(textSize);
        MenuItem textSizeInc = new MenuItem(textSize + " +1");
        MenuItem textSizeDec = new MenuItem(textSize + " -1");
        textSizeInc.setOnAction(e -> increaseTextSize());
        textSizeDec.setOnAction(e -> decreaseTextSize());
        textSizeMenu.getItems().add(textSizeInc);
        textSizeMenu.getItems().add(textSizeDec);

        String brightness = TranslatorV2.translateLanguage("Brightness", masterLanguage);
        Menu brightnessMenu = new Menu(brightness);
        MenuItem brightnessInc = new MenuItem(brightness + " +1");
        MenuItem brightnessDec = new MenuItem(brightness + " -1");
        brightnessInc.setOnAction(e -> increaseBrightness());
        brightnessDec.setOnAction(e -> decreaseBrightness());
        brightnessMenu.getItems().add(brightnessInc);
        brightnessMenu.getItems().add(brightnessDec);


        Menu languageMenu = new Menu(TranslatorV2.translateLanguage("Language", masterLanguage));
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
        menu.getItems().add(audioMenu);

        MenuBar menuBar = new MenuBar(menu);
        menuBar.setTranslateX(menuBar.getTranslateX() + 600);
        menuBar.setTranslateY(menuBar.getLayoutY() + 40);
        root.getChildren().add(menuBar);
    }

    private void addNextBackBtns(){
        nextBtn = new Button(TranslatorV2.translateLanguage("Next", masterLanguage));
        nextBtn.setTranslateX(nextBtn.getTranslateX() + 580);
        nextBtn.setTranslateY(nextBtn.getTranslateY() + 300);
        nextBtn.setOnAction(e -> nextPage());

        backBtn = new Button(TranslatorV2.translateLanguage("Back", masterLanguage));
        backBtn.setTranslateX(backBtn.getTranslateX() + 150);
        backBtn.setTranslateY(backBtn.getTranslateY() + 300);
        backBtn.setOnAction(e -> previousPage());

        root.getChildren().addAll(nextBtn, backBtn);

        nextBtn.setVisible(false);
        backBtn.setVisible(false);
    }

    private void addVirtualKeyboardToRoot() {
        root.getChildren().add(keyboard);
    }

    private void choiceOnClick(HBox[] choices) {
        //turn the selected choice's background to green and the rest to white
        for (HBox choice : choices) {
            choice.setOnMouseClicked(e -> {
                RadioButton rb;
                for (HBox c : choices) {
                    c.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    rb = (RadioButton) c.getChildren().get(0);
                    rb.setSelected(false);
                }
                choice.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                rb = (RadioButton) choice.getChildren().get(0);
                rb.setSelected(true);

                //Set the voter selection for the item
                String choiceText = ((Text)choice.getChildren().get(1)).getText();
                if (choiceText.split("\\(").length>1) {
                    choiceText = choiceText.split("\\(")[0].substring(1, choiceText.split("\\(")[0].length() - 1);
                }
                else {
                    choiceText = choiceText.substring(1, choiceText.length());
                }
                Item currentItem = Main.getPrompt(currentPage);
                System.out.println("Current prompt type: " + currentItem.getItemType());
                if (choiceText.contains("Write-in")) {
                    choiceText = choiceText.substring(choiceText.indexOf(": ")+2);
                    System.out.print(choiceText);
                    if(!choiceText.equals("")){
                        currentItem.setSelection(choiceText,true);
                    }
                }
                else {
                    currentItem.setSelection(choiceText, false);
                }
            });
        }
    }

    private Button hideOrShowKeyboard(VirtualKeypad keyboard) {
        //changed default to show keyboard, as it is initially hidden
        Button hideOrShowKeyboard = new Button("Show Keyboard");
        hideOrShowKeyboard.setOnAction(e -> {
            if (keyboard.isVisible()) {
                keyboard.setVisible(false);
                hideOrShowKeyboard.setText("Show Keyboard");
            } else {
                keyboard.setVisible(true);
                hideOrShowKeyboard.setText("Hide Keyboard");
            }
        });
        return hideOrShowKeyboard;
    }

    public void turnOnAndOffScreen(){
        if(isScreenOn){
            background.toFront();
            background.setFill(Color.BLACK);
            isScreenOn = false;
        }else{
            background.toBack();
            background.setFill(Color.WHITE);
            isScreenOn = true;
        }
    }

    public boolean getIsScreenOn(){
        return isScreenOn;
    }


    //USED FOR STORING VOTES AND PAGES
    public void setPages() {
        List<VBox> pagesTemp = new ArrayList<>();  // Arbitrary # of pages.

        pagesTemp.add(welcomePage());  // Insert welcome Page
        Item prompts = Main.getNextPrompt();
        do {
            String question = prompts.getItemName();  // President, Governor, Proposition, etc.

            if(Objects.equals(prompts.getItemType(), "Contest")){
                ArrayList<String> choices = new ArrayList<>();

                for(String candidate : prompts.getContestOptions().keySet()){  // Dem, Rep, Ind, etc.
                    choices.add(candidate + " (" + prompts.getContestOptions().get(candidate) + ")");
                }

                String[] choice_array = Arrays.copyOf(choices.toArray(), choices.size(), String[].class);
                pagesTemp.add(displayQuestion(question, choice_array, false));

            }else{
                String description = prompts.getDescription();  // Description of Prop (only happens w/ Props)

                ArrayList<String> choices = (ArrayList<String>) prompts.getPropOptions();  // Yes, No, etc.

                String[] choice_array = Arrays.copyOf(choices.toArray(), choices.size(), String[].class);
                pagesTemp.add(displayQuestion(question + "\n" + description, choice_array, true));
            }

            prompts = Main.getNextPrompt();
        }while (prompts != null);


        //iterate through each page and give them an id. Sets the first visible.
        for (int i = 0; i < pagesTemp.size(); i++) {
            pagesTemp.get(i).setVisible(i == 0);
            pagesTemp.get(i).setId("page" + i);
        }

        // Move everything to Global 'Pages' Array, since everything
        // is hard coded w/ it atm :)
        pages = new VBox[pagesTemp.size() + 3];  // Adds PrintPage & CastPage
        for(int i = 0; i < pages.length-3; i++){
            pages[i] = pagesTemp.get(i);
        }

        // Send pages to root
        for(VBox page : pages){
            if(page != null){
                root.getChildren().add(page);
            }
        }
    }
}