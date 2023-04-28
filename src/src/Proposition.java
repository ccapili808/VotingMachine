import java.util.HashMap;
import java.util.List;

public class Proposition extends Item{
    /**
     * For the proposition type of votes
     */
    private String propDescription;
    private List<String> options;
    private HashMap<String, Boolean> selections = new HashMap<>();
    public Proposition (String propDescription, String propName, List<String> options, int itemID, String sectionName, String itemType) {
        super(itemType,propName,sectionName,itemID);
        this.propDescription = propDescription;
        this.options = options;
        for (String itemOption:options
             ) {
            selections.put(itemOption,false);
        }
    }

    public String getPropDescription() {
        return propDescription;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setSelection (String selectedOption, boolean writeIn) {
        for (String option: selections.keySet()
        ) {
            selections.put(option,false);
        }
        selections.put(selectedOption,true);
    }

    public String getSelection() {
        for (String option: selections.keySet()) {
            if (selections.get(option)) {
                return option;
            }
        }
        return null;
    }

}
