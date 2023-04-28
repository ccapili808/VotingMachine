import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;


public class Printer {
    private Group printerRoot;
    private Group printerJointObjects;
    private Rectangle voteAuthorizationCard;
    VBox vBox;
    private int buttonXOffset = 1150;
    private int buttonYOffset = 50;

    public Printer(Group root, Group printerJointObjects) {
        printerRoot = new Group();
        this.printerJointObjects = printerJointObjects;
        root.getChildren().add(printerRoot);
        printerGUISetup();
    }

    private void printerGUISetup(){
        //TODO: Remove this spoil button, call spoil from touchscreen
        Button spoilButton = new Button("Spoil Ballot");
        spoilButton.setTranslateX(buttonXOffset);
        spoilButton.setTranslateY(buttonYOffset +175);
        spoilButton.setPrefWidth(125);
        spoilButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                spoilCard();
            }
        });

        //TODO: Remove printVoteSelectionButton: Do it somewhere else
        Button printSelectionButton = new Button("Print Selection");
        printSelectionButton.setTranslateX(buttonXOffset);
        printSelectionButton.setTranslateY(buttonYOffset +225);
        printSelectionButton.setPrefWidth(125);
        printSelectionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printVoteSelection();
            }
        });

        voteAuthorizationCard = (Rectangle) printerJointObjects.getChildren().get(0);
        vBox = (VBox) printerJointObjects.getChildren().get(1);

        printerRoot.getChildren().add(printSelectionButton);
        printerRoot.getChildren().add(spoilButton);
    }

    /**
     * This function should be called after user has already verified their vote on the touch screen.
     * This function reads user’s votes from temp storage and prints their selection onto the voter card.
     */
    private void printVoteSelection() {
        List<Section> ballot = Main.getBallot();

        //TODO: Figure out how to get answers
        for(Section s: ballot ){
            System.out.println("Section:" + s.getSectionName());
            Text section = new Text(s.getSectionName());
            section.setUnderline(true);
            vBox.getChildren().add(section);

            for(Item i: s.getSectionItems()){
                Text question = new Text(i.getItemName() +"\n");
                question.setWrappingWidth(250);
                vBox.getChildren().add(question);
                System.out.println(i.getItemName());
                System.out.println(i.getItemType());
            }
        }

    }

    /**
     *his function can only be called after a user’s vote has already printed their vote on a physical voter card.
     * The function re-prints black ink across their voter card to conceal their voter card.
     */
    private void spoilCard() {
        voteAuthorizationCard.setFill(Color.BLACK);
    }


}
