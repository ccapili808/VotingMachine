import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VoteAuthorizationCardScanner {
    private Scene scene;
    private Group root;
    private final double PANEL_WIDTH = 300;
    private final double PANEL_HEIGHT = 600;

    private final double SCANNER_WIDTH = 250;
    private final double SCANNER_HEIGHT = 50;
    public VoteAuthorizationCardScanner(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
        createCardScannerGUI();
    }

    private void createCardScannerGUI(){
        Rectangle panel = new Rectangle(PANEL_WIDTH, PANEL_HEIGHT, Color.WHITE);
        panel.setStroke(Color.BLACK);
        panel.setX(900);
        root.getChildren().add(panel);

        Rectangle printer = new Rectangle(PANEL_WIDTH-10, PANEL_HEIGHT-80, Color.WHITE);
        printer.setStroke(Color.BLACK);
        printer.setX(900+5);
        printer.setY(5);
        root.getChildren().add(printer);

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
        Rectangle scanner = new Rectangle(SCANNER_WIDTH, SCANNER_HEIGHT, Color.BLACK);
        scanner.setX(925);
        scanner.setY(PANEL_HEIGHT-70);
        root.getChildren().add(scanner);
    }

    /**
     * Activates the vote authorization card slot to hold the voter’s card. Once this function is called,
     * it runs continuously until it detects a card in the slot.
     * Once a card is detected, it holds the card in a fixed position in the display.
     */
    private void insertCard(){

    }

    /**
     * Activates the vote authorization card slot to return the voter card back to the user.
     */
    private void removeCard() {

    }

    /**
     * Activates the vote authorization card slot to store the finalized voter card in the DRE system.
     */
    private void storeCard(){

    }

    /**
     * Generates a new unique ID. This function is used for keeping track of digital and physical votes.
     */
    int generateID(){
        return -1;
    }
}
