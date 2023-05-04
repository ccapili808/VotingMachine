import java.util.HashMap;
import java.util.Map;

public class TranslatorV2 {
    private final static Map<String, String>  mandarinDictionary = new HashMap<>();
    private final static Map<String, String> spanishDictionary = new HashMap<>();
    TranslatorV2() {
        initMandarinDictionary();
        initSpanishDictionary();
    }

    private void initMandarinDictionary() {
        mandarinDictionary.put("Next", "下一个");
        mandarinDictionary.put("Back", "后退");
        mandarinDictionary.put("Brightness", "亮度");
        mandarinDictionary.put("Volume", "音量");
        mandarinDictionary.put("Text Size", "字体大小");
        mandarinDictionary.put("Yes", "是");
        mandarinDictionary.put("No", "没有");
        mandarinDictionary.put("President", "总统");
        mandarinDictionary.put("Vice President", "副总统");
        mandarinDictionary.put("Secretary", "秘书");
        mandarinDictionary.put("Treasurer", "司库");
        mandarinDictionary.put("Senator", "参议员");
        mandarinDictionary.put("Representative", "代表");
        mandarinDictionary.put("Governor", "州长");
        mandarinDictionary.put("Print Ballot", "打印选票");
        mandarinDictionary.put("Vote", "投票");
        mandarinDictionary.put("Accessibility", "无障碍");
        mandarinDictionary.put("Language", "语言");
        mandarinDictionary.put("English", "英语");
        mandarinDictionary.put("Spanish", "西班牙语");
        mandarinDictionary.put("Mandarin", "普通话");
        mandarinDictionary.put("Write-in field...", "填写字段...");
        /*
                spanishDictionary.put("Audio", "Audio");
        spanishDictionary.put("To continue to print your ballot, touch 'Return to ballot",
                "Para continuar imprimiendo su boleta, toque 'Volver a la boleta");
        spanishDictionary.put("You cannot change your vote selections after printing your ballot.",
                "No puede cambiar sus selecciones de voto después de imprimir su boleta.");
        spanishDictionary.put("Democrat", "Demócrata");
        spanishDictionary.put("Republican", "Republicano");
        spanishDictionary.put("(Democrat)", "(Demócrata)");
        spanishDictionary.put("(Republican)", "(Republicano)");
         */
        mandarinDictionary.put("Audio", "音频");
        mandarinDictionary.put("To continue to print your ballot, touch 'Return to ballot",
                "要继续打印您的选票，请点击“返回到选票");
        mandarinDictionary.put("You cannot change your vote selections after printing your ballot.",
                "打印选票后，您将无法更改投票选择。");
        mandarinDictionary.put("Democrat", "民主党");
        mandarinDictionary.put("Republican", "共和党");
        mandarinDictionary.put("(Democrat)", "（民主党）");
        mandarinDictionary.put("(Republican)", "（共和党）");
    }

    private void initSpanishDictionary() {
        spanishDictionary.put("Next", "Siguiente");
        spanishDictionary.put("Back", "Atrás");
        spanishDictionary.put("Brightness", "Brillo");
        spanishDictionary.put("Volume", "Volumen");
        spanishDictionary.put("Text Size", "Tamaño del texto");
        spanishDictionary.put("Yes", "Sí");
        spanishDictionary.put("No", "No");
        spanishDictionary.put("President", "Presidente");
        spanishDictionary.put("Vice President", "Vicepresidente");
        spanishDictionary.put("Secretary", "Secretario");
        spanishDictionary.put("Treasurer", "Tesorero");
        spanishDictionary.put("Senator", "Senador");
        spanishDictionary.put("Representative", "Representante");
        spanishDictionary.put("Governor", "Gobernador");
        spanishDictionary.put("Print Ballot", "Imprimir boleta");
        spanishDictionary.put("Vote", "Votar");
        spanishDictionary.put("Accessibility", "Accesibilidad");
        spanishDictionary.put("Language", "Idioma");
        spanishDictionary.put("English", "Inglés");
        spanishDictionary.put("Spanish", "Español");
        spanishDictionary.put("Chinese", "Chino");
        spanishDictionary.put("Write-in Field...", "Escribir en el campo...");
        spanishDictionary.put("Audio", "Audio");
        spanishDictionary.put("To continue to print your ballot, touch 'Return to ballot",
                "Para continuar imprimiendo su boleta, toque 'Volver a la boleta");
        spanishDictionary.put("You cannot change your vote selections after printing your ballot.",
                "No puede cambiar sus selecciones de voto después de imprimir su boleta.");
        spanishDictionary.put("Democrat", "Demócrata");
        spanishDictionary.put("Republican", "Republicano");
        spanishDictionary.put("(Democrat)", "(Demócrata)");
        spanishDictionary.put("(Republican)", "(Republicano)");
    }

    public static String translateLanguage(String text, String language) {
        return switch (language) {
            case "Spanish" ->
                //search for text in spanish dictionary, if it's not there just return the text
                    spanishDictionary.getOrDefault(text, text);
            case "Mandarin" ->
                //search for text in mandarin dictionary, if it's not there just return the text
                    mandarinDictionary.getOrDefault(text, text);
            default -> text;
        };
    }




}
