import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VoteAuthorizationCardScanner {
    private Group voteCardRoot;
    private int xOffset = 825;
    private final double PANEL_WIDTH = 300;
    private final double PANEL_HEIGHT = 600;
    private Rectangle voteAuthorizationCard;
    private VBox vBox;
    private Group printerJointObjects;

    private boolean cardInserted;
    private boolean staffOverride = true;

    private final long LIMIT = 10000000000L;
    private long id = 0;

    Text idText;

    private final double SCANNER_WIDTH = 250;
    private final double SCANNER_HEIGHT = 50;
    public VoteAuthorizationCardScanner(Group root) {
        voteCardRoot = new Group();
        printerJointObjects = new Group();
        cardScannerGUISetup();
        printerJointObjects.getChildren().add(voteAuthorizationCard);
        printerJointObjects.getChildren().add(vBox);
        cardInserted = false;
        root.getChildren().add(voteCardRoot);
    }

    private void cardScannerGUISetup(){
        Rectangle panel = new Rectangle(PANEL_WIDTH, PANEL_HEIGHT, Color.WHITE);
        panel.setStroke(Color.BLACK);
        panel.setX(xOffset);

        voteAuthorizationCard = new Rectangle(PANEL_WIDTH -10, PANEL_HEIGHT -80, Color.GREY);
        if(cardInserted){
            voteAuthorizationCard.setFill(Color.WHITE);
        }
        voteAuthorizationCard.setStroke(Color.BLACK);
        voteAuthorizationCard.setX(xOffset +5);
        voteAuthorizationCard.setY(5);

        Rectangle scanner = new Rectangle(SCANNER_WIDTH, SCANNER_HEIGHT, Color.WHITE);
        scanner.setStroke(Color.BLACK);
        scanner.setX(xOffset + 25);
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

        idText = new Text();
        idText.setX(xOffset + PANEL_WIDTH - 80);
        idText.setY(PANEL_HEIGHT - SCANNER_HEIGHT - 30);

        vBox = new VBox();
        vBox.setTranslateX(xOffset+10);
        vBox.setTranslateY(10);


        voteCardRoot.getChildren().add(panel);
        voteCardRoot.getChildren().add(voteAuthorizationCard);
        voteCardRoot.getChildren().add(scanner);
        voteCardRoot.getChildren().add(vBox);
        voteCardRoot.getChildren().add(idText);
        voteCardRoot.getChildren().add(printerJointObjects);

    }

    public Group getPrinterJointObjects(){

        return printerJointObjects;
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
        vBox.getChildren().clear();
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
