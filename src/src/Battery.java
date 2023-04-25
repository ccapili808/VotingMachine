public class Battery {
    private String POWER_SOURCE;
    private float voltage;
    public Battery() {

    }

    /**
     * This function will return the amount of voltage being received by the outlet.
     */
    private float getOutletVoltage() {
        return voltage;
    }

    /**
     * This function will return the amount of power left in the batteries as a percentage.
     */
    private int getBatteryPercentage(){
        return -1;
    }

    /**
     * Changes the power source from outlet or batter depending on voltage
     * POWER_SOURCE will either be "outlet" or "battery"
     */
    private void changePwrSource() {

    }

    /**
     * This function will return whether the system is running on outlet or battery power.
     */
    private void getPwrSource(){

    }

    /**
     * Clears setup info and turns off screen
     */
    private void manualShutDown(){

    }

}
