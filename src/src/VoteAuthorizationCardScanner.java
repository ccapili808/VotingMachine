import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VoteAuthorizationCardScanner {
    private Group root;
    private final int OFFSET = 825;
    private final double PANEL_WIDTH = 300;
    private final double PANEL_HEIGHT = 600;
    private Rectangle voteAuthorizationCard;

    private boolean cardInserted;
    private boolean staffOverride = true;

    private final long LIMIT = 10000000000L;
    private long id = 0;

    Text idText;

    private final double SCANNER_WIDTH = 250;
    private final double SCANNER_HEIGHT = 50;
    public VoteAuthorizationCardScanner(Group root) {
        this.root = new Group();
        createCardScannerGUI();
        cardInserted = false;
        root.getChildren().add(this.root);
    }

    private void createCardScannerGUI(){
        Rectangle panel = new Rectangle(PANEL_WIDTH, PANEL_HEIGHT, Color.WHITE);
        panel.setStroke(Color.BLACK);
        panel.setX(OFFSET);
        root.getChildren().add(panel);

        voteAuthorizationCard = new Rectangle(PANEL_WIDTH -10, PANEL_HEIGHT -80, Color.GREY);
        if(cardInserted){
            voteAuthorizationCard.setFill(Color.WHITE);
        }
        voteAuthorizationCard.setStroke(Color.BLACK);
        voteAuthorizationCard.setX(OFFSET+5);
        voteAuthorizationCard.setY(5);
        root.getChildren().add(voteAuthorizationCard);

        Rectangle scanner = new Rectangle(SCANNER_WIDTH, SCANNER_HEIGHT, Color.WHITE);
        scanner.setStroke(Color.BLACK);
        scanner.setX(OFFSET + 25);
        scanner.setY(PANEL_HEIGHT -60);
        scanner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!cardInserted){
                    insertCard();
                }else if(cardInserted && staffOverride){
                    removeCard();
                }
            }
        });
        root.getChildren().add(scanner);

        idText = new Text();
        root.getChildren().add(idText);
        idText.setX(OFFSET + 10);
        idText.setY(PANEL_HEIGHT - SCANNER_HEIGHT - 30);
/*
        //TODO: This should all be moved to printer functionality
        VBox vBox = new VBox();
        vBox.setTranslateX(910);
        vBox.setTranslateY(10);
        Text example = new Text("This is where the votes will be printed\n");
        example.setWrappingWidth(250);
        //TODO: Figure out how to tab text even if it overlaps, maybe tabpane
        Text example2 = new Text("The text will wrap over if the prompt is too large like in this example");
        example2.setWrappingWidth(250);
        vBox.getChildren().add(example);
        vBox.getChildren().add(example2);
        root.getChildren().add(vBox);

        //TODO: ADD ON CLICK EVENT

        //ScannerText won't be visible in final design
        Text scannerText = new Text("Click here to insert card");
        scannerText.setX(950);
        scannerText.setY(SCREEN_HEIGHT -40);
        root.getChildren().add(scannerText);
        */
    }

    /**
     * Activates the vote authorization card slot to hold the voter’s card. Once this function is called,
     * it runs continuously until it detects a card in the slot.
     * Once a card is detected, it holds the card in a fixed position in the display.
     */
    private void insertCard(){
        cardInserted = true;
        voteAuthorizationCard.setFill(Color.WHITE);
        id = generateID();
        idText.setVisible(true);
        idText.setText(Long.toString(id));
    }

    /**
     * Activates the vote authorization card slot to return the voter card back to the user.
     */
    private void removeCard() {
        cardInserted = false;
        voteAuthorizationCard.setFill(Color.GREY);
        idText.setVisible(false);
    }

    /**
     * Activates the vote authorization card slot to store the finalized voter card in the DRE system.
     */
    private void storeCard(){

    }

    /**
     * Generates a new unique ID. This function is used for keeping track of digital and physical votes.
     */
    long generateID(){
        // 10 digits.
        long id = System.currentTimeMillis() % LIMIT;
        if ( id <= this.id ) {
            id = (this.id + 1) % LIMIT;
        }

        return id;
    }
}
