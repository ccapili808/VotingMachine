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

    public void readSetUp() {

    }

    public void parseSetUpInfo() {

    }

}