import java.util.HashMap;
import java.util.List;

public class Proposition extends Item{
    /**
     * For the proposition type of votes
     */
    private String propDescription;
    private List<String> options;
    private HashMap<String, Boolean> selections = new HashMap<>();

    /**
     * Constructor, called from storage to create propositions
     * @param propDescription
     * @param propName
     * @param options
     * @param itemID
     * @param sectionName
     * @param itemType
     */
    public Proposition (String propDescription, String propName, List<String> options, int itemID, String sectionName, String itemType) {
        super(itemType,propName,sectionName,itemID);
        this.propDescription = propDescription;
        this.options = options;
        for (String itemOption:options
             ) {
            selections.put(itemOption,false);
        }
    }

    /**
     * This method returns a description for propositions. Returns "" for all other items since they have no description
     * @return the prop description
     */
    @Override
    public String getDescription() {
        return propDescription;
    }

    /**
     * This method returns the list of proposition options
     * @return the prop options
     */
    @Override
    public List<String> getPropOptions() {
        return options;
    }

    /**
     * Set the user selection
     * @param selectedOption the selected option string
     */
    @Override
    public void setSelection (String selectedOption, boolean writeIn) {
        for (String option: selections.keySet()
        ) {
            selections.put(option,false);
        }
        selections.put(selectedOption,true);
    }

    /**
     * Get the user's selection
     * @return the string of the candidate currently selected
     */
    @Override
    public String getSelection() {
        for (String option: selections.keySet()) {
            if (selections.get(option)) {
                return option;
            }
        }
        return null;
    }

    /**
     * This resets the single voting session's selections from main
     */
    @Override
    public void resetSelections() {
        for (String itemOption:options
        ) {
            selections.put(itemOption,false);
        }
    }

}
