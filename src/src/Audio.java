public class Audio {
    private static int audioLevel;
    public Audio() {
        audioLevel = 5;
    };

    private void readInstruction(String instruction) {
    }

    public static void increaseVolume(){
        if (audioLevel < 10){audioLevel += 1;}
    }

    public static void decreaseVolume(){
        if (audioLevel > 0){audioLevel -= 1;}
    }
}
