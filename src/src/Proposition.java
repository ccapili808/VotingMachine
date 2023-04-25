import java.util.HashMap;
import java.util.List;

public class Proposition extends Item{
    /**
     * For the proposition type of votes
     */
    private String propDescription;
    private List<String> option;
    private HashMap<String, Boolean> selections = new HashMap<>();
    public Proposition (String propDescription, String propName, List<String> option, int itemID, String sectionName, String itemType) {
        super(itemType,propName,sectionName,itemID);
        this.propDescription = propDescription;
        this.option = option;
        for (String itemOption:option
             ) {
            selections.put(itemOption,false);
        }
    }

    public String getPropDescription() {
        return propDescription;
    }

    public List<String> getOption() {
        return option;
    }

}
