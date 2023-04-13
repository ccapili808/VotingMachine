import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TouchScreen touchScreen = new TouchScreen();
        Scene scene = touchScreen.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Parses the setup storage and stores all the ballot information in an array of Prompt objects.
     * Prompt object will have four separate values which can be stored.
     * String questionType, String question, String[] choices, String[] selection.
     * The question type will determine whether the question is multi select or single select.
     * The array of choices, will store all the prompt choices. The array of selections will store all the user selections.
     */
    public void parseSetUpInfo() {

    }

    /**
     * Gets a specific prompt from our prompts array.
     * Useful for skipping around when a user wants to change their vote during the vote verification phase.
     * @param promptNumber - the integer value of the prompt to be retrieved.
     */
    public void getPrompt(int promptNumber) {

    }

    /**
     * Gets next prompt from prompts array. When the user finishes select the answer in the current prompt.
     */
    public void getNextPrompt() {

    }

    /**
     * Gets previous prompt from prompt array. When the user wants to go to the previous prompt to change the selection.
     */
    public void getPrevPrompt() {

    }

    /**
     * This function will clear the array that was created in parseSetUpInfo()
     */
    public void clearSetUpInfo() {

    }

    /**
     * Stalls the program until the admin types their code on the screen.
     * This function makes GUI objects and a write-in field for the passcode.
     * Once a successful passcode is entered, the function calls removeCard() and idlePage().
     */
    public void getAdminApproval() {

    }

    /**
     * Calls the Storage object and returns true if all three functions:
     * validPriStorage, validBackStorage, validSetupStorage return true.
     * If any of these functions return false, then the setup failed, and this function should return false.
     */
    public void validSetup() {

    }

    /**
     * Turns on the external led. Used when DRE system is operated with battery power.
     */
    public void turnLedOn() {

    }

    /**
     * Turns off the external led. Used when DRE system is operated with outlet power.
     */
    public void turnLedOff() {

    }



}