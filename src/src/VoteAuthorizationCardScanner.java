import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class VoteAuthorizationCardScanner {
    private Group voteCardRoot;
    private Scene scene;
    private int xOffset = 825;
    private final double PANEL_WIDTH = 300;
    private final double PANEL_HEIGHT = 600;
    private final double SCANNER_WIDTH = 250;
    private final double SCANNER_HEIGHT = 50;
    private Text scannerText;
    private Rectangle voteAuthorizationCard;
    private VBox vBox;
    private Group printerJointObjects; //Vbox to add text to, and rectangle to spoil ballot

    private static boolean cardInserted = false;
    private boolean staffOverride = true; //TODO: Remove for main method instead

    private long id = 0;
    private Text idText;

    public VoteAuthorizationCardScanner(Scene scene, Group root) {
        this.scene = scene;
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

        scannerText = new Text("Click to Insert Card");
        scannerText.setX(xOffset + 100);
        scannerText.setY(PANEL_HEIGHT - 30);
        scannerText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                insertCard();
            }
        });

        Rectangle scanner = new Rectangle(SCANNER_WIDTH, SCANNER_HEIGHT, Color.WHITE);
        scanner.setStroke(Color.BLACK);
        scanner.setX(xOffset + 25);
        scanner.setY(PANEL_HEIGHT -60);
        scanner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                insertCard();
            }
        });

        idText = new Text();
        idText.setX(xOffset + PANEL_WIDTH - 80);
        idText.setY(PANEL_HEIGHT - SCANNER_HEIGHT - 30);

        vBox = new VBox();
        vBox.setTranslateX(xOffset+10);
        vBox.setTranslateY(10);

        voteCardRoot.getChildren().add(panel);
        voteCardRoot.getChildren().add(printerJointObjects);
        voteCardRoot.getChildren().add(scanner);
        voteCardRoot.getChildren().add(scannerText);
        voteCardRoot.getChildren().add(vBox);
        voteCardRoot.getChildren().add(idText);

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
        if(!cardInserted){
            scannerText.setVisible(false);
            cardInserted = true;
            voteAuthorizationCard.setFill(Color.WHITE);
            id = generateID();
            idText.setVisible(true);
            idText.setText(Long.toString(id));
            Main.getTouchScreen().cardInserted();
        }
    }

    /**
     * Activates the vote authorization card slot to return the voter card back to the user.
     */
    public void removeCard() {
        scannerText.setVisible(true);
        scannerText.setText("Click to Insert Card");
        cardInserted = false;
        vBox.getChildren().clear();
        voteAuthorizationCard.setFill(Color.GREY);
        idText.setVisible(false);
    }

    /**
     * Activates the vote authorization card slot to store the finalized voter card in the DRE system.
     */
    public void storeCard(){
        Robot robot = new Robot();
        WritableImage image = new WritableImage((int) (PANEL_WIDTH -10), (int) (PANEL_HEIGHT -80));
        final Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
        final Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());

        image = robot.getScreenCapture(image,
                windowCoord.getX() + sceneCoord.getX() + xOffset + 5 + 50,
                windowCoord.getY() + sceneCoord.getY() + 5 + 100,
                PANEL_WIDTH - 10, PANEL_HEIGHT - 80);

        try {
            File file = new File("./Resources/VoteAuthorizationCards/" + id + ".png");
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isCardInserted(){
        return cardInserted;
    }

    /**
     * Generates a new unique ID. This function is used for keeping track of digital and physical votes.
     */
    long generateID(){
        // 10 digits.
        long LIMIT = 10000000000L;
        long id = System.currentTimeMillis() % LIMIT;
        if ( id <= this.id ) {
            id = (this.id + 1) % LIMIT;
        }

        return id;
    }
}
