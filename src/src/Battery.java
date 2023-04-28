import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Battery {
    private Group batteryRoot;
    private String powerSource;
    private float voltage;
    private int xOffset = 1150;
    private int yOffset = 50;
    private float batteryPercentage = 100;
    private boolean isRunning = true;
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
                    } else {
                        //Maybe turn off screen without clearing info
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
        batteryImage.setX(xOffset+50);
        batteryImage.setY(yOffset+100);

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
            powerSource = "battery";
            voltage = 0;
            connectButton.setText("Connect Outlet");
        }else{
            powerSource = "outlet";
            voltage = 120;
            connectButton.setText("Disconnect Outlet");
        }
        updateBatteryImage();
    }

    /**
     * This function will return whether the system is running on outlet or battery power.
     */
    public String getPwrSource(){
        return powerSource;
    }

    private void updateBatteryImage(){
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
        Thread thread = new Thread(() -> {
            while(isRunning){
                try{
                    Thread.sleep(1000);
                    if(powerSource.equals("battery")){
                        if(batteryPercentage > 0){
                            batteryPercentage -= 5;
                        } else{
                            //TODO: Add functionality when battery is dead
                        }
                        updateBatteryImage();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }

    /**
     * Clears setup info and turns off screen
     */
    private void manualShutDown(){
        //TODO: SHUTDOWN
        System.out.println("SHUT DOWN");
    }

}
