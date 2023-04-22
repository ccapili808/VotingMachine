import java.beans.PropertyVetoException;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;

public class Audio {
    private static int audioLevel;
    private Synthesizer synthesizer;
    private static SynthesizerProperties properties;
    public Audio() {
        //setting properties as Kevin Dictionary
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        //registering speech engine
        try {
            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
        } catch (EngineException e) {
            throw new RuntimeException(e);
        }
        //create a Synthesizer that generates voice
        try {
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
        } catch (EngineException e) {
            throw new RuntimeException(e);
        }
        audioLevel = 5;
        //set the volume for tts, from 0.0-1.0
        properties = synthesizer.getSynthesizerProperties();
        try {
            properties.setVolume((float)(audioLevel*0.1));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    };

    public void readInstruction(String instruction) {
        try {
            //resume tts
            synthesizer.resume();
            //ouput instruction string to audio
            synthesizer.speakPlainText(instruction, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void increaseVolume(){
        //increase tts volume
        if (audioLevel < 10) {
            audioLevel += 1;
            try {
                properties.setVolume((float)(audioLevel*0.1));
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void decreaseVolume(){
        //decrease tts volume
        if (audioLevel > 0) {
            audioLevel -= 1;
            try {
                properties.setVolume((float)(audioLevel*0.1));
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
