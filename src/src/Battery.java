import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Battery {
    private final int DISCHARGERATE = 10;
    private Group batteryRoot;
    private Circle externalLED;
    private String powerSource;
    private float voltage;
    private int xOffset = 1150;
    private int yOffset = 50;
    private float batteryPercentage = 100;
    private boolean hasPower = true;
    Button connectButton;
    ImageView batteryImage;

    public Battery(Group root) {
        batteryRoot = new Group();
        powerSource = "outlet";
        batteryGUISetup();
        root.getChildren().add(batteryRoot);
        Thread batteryLevel = powerMachine();
        batteryLevel.start();

    }

    private void batteryGUISetup(){
        connectButton = new Button("Disconnect Outlet");
        if(!powerSource.equals("outlet")){
            connectButton.setText("Connect Outlet");
        }
        connectButton.setTranslateX(xOffset);
        connectButton.setTranslateY(yOffset);
        connectButton.setPrefWidth(125);
        connectButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePwrSource();
            }
        });


        Button powerButton = new Button("Power Button");
        powerButton.setTranslateX(xOffset);
        powerButton.setTranslateY(yOffset+50);
        powerButton.setPrefWidth(125);

        powerButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            long startTime;
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    startTime = System.currentTimeMillis();
                } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    if (System.currentTimeMillis() - startTime > 2 * 1000) {
                        manualShutDown();
                        batteryImage.setVisible(false);
                    } else {
                        if(hasPower){
                            Main.getTouchScreen().turnOnAndOffScreen();
                            if(Main.getTouchScreen().getIsScreenOn()){
                                batteryImage.setVisible(true);
                            }else{
                                batteryImage.setVisible(false);
                            }
                        }
                    }
                }
            }
        });

        try{
            batteryImage = new ImageView(new Image(new FileInputStream("./Resources/outlet.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        batteryImage.setFitHeight(50);
        batteryImage.setPreserveRatio(true);
        batteryImage.setX(750);
        batteryImage.setY(25);

        externalLED = new Circle(25);
        externalLED.setStroke(Color.BLACK);
        externalLED.setFill(Color.BLACK);
        externalLED.setCenterX(50);
        externalLED.setCenterY(-50);

        batteryRoot.getChildren().add(externalLED);
        batteryRoot.getChildren().add(batteryImage);
        batteryRoot.getChildren().add(connectButton);
        batteryRoot.getChildren().add(powerButton);
    }

    /**
     * This function will return the amount of voltage being received by the outlet.
     */
    public float getOutletVoltage() {
        return voltage;
    }

    /**
     * This function will return the amount of power left in the batteries as a percentage.
     */
    public float getBatteryPercentage(){
        return batteryPercentage;
    }

    /**
     * Changes the power source from outlet or batter depending on voltage
     * POWER_SOURCE will either be "outlet" or "battery"
     */
    private void changePwrSource() {
        if(powerSource.equals("outlet")){
            if(batteryPercentage <= 0){
                manualShutDown();
                hasPower = false;
            }
            powerSource = "battery";
            voltage = 0;
            connectButton.setText("Connect Outlet");
        }else{
            hasPower = true;
            powerSource = "outlet";
            voltage = 120;
            connectButton.setText("Disconnect Outlet");
        }
        updateBatteryImageAndExternalLED();
    }

    /**
     * This function will return whether the system is running on outlet or battery power.
     */
    public String getPwrSource(){
        return powerSource;
    }

    private void updateBatteryImageAndExternalLED(){
        if(powerSource.equals("outlet")){
            externalLED.setFill(Color.BLACK);
        }else{
            externalLED.setFill(Color.YELLOW);
        }

        try{
            if(powerSource.equals("outlet")){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/outlet.png")));
            } else if(batteryPercentage > 80){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/full.png")));
            } else if (batteryPercentage > 60){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/mostlyFull.png")));
            } else if (batteryPercentage > 40){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/half.png")));
            } else if (batteryPercentage > 20){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/almostEmpty.png")));
            } else if (batteryPercentage > 0){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/empty.png")));
            } else if( batteryPercentage <= 0){
                batteryImage.setImage(new Image(new FileInputStream("./Resources/dead.png")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Thread powerMachine(){
        return new Thread(() -> {
            while(hasPower){
                try{
                    Thread.sleep(1000);
                    if(powerSource.equals("battery")){
                        if(batteryPercentage > 0){
                            batteryPercentage -= DISCHARGERATE;
                        } else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    manualShutDown();
                                }
                            });
                            hasPower = false;
                        }
                        updateBatteryImageAndExternalLED();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Clears setup info and turns off screen
     */
    private void manualShutDown(){
        batteryImage.setVisible(false);
        Main.getTouchScreen().turnOnAndOffScreen();
        Main.clearSetUpInfo();
        System.out.println("SHUT DOWN");
    }

}
