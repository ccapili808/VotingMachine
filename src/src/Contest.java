import java.util.HashMap;
import java.util.List;

public class Contest extends Item{
    /**
     * Normal vote with contestName and list of candidates
     */
    private Candidates contestCandidates;
    private boolean writeIn;
    private String writeInString;
    private HashMap<String, Boolean> selections = new HashMap<>();


    /**
     * Constructor for contest. Used to create ballot items when parsing setup json file.
     * @param contestName
     * @param contestCandidates
     * @param writeIn
     * @param itemID
     * @param sectionName
     * @param itemType
     */
    public Contest (String contestName, Candidates contestCandidates, boolean writeIn, int itemID, String sectionName, String itemType) {
        super(itemType, contestName,sectionName,itemID);
        this.contestCandidates = contestCandidates;
        this.writeIn = writeIn;
        for (String itemCandidate: contestCandidates.getCandidatesList().keySet()
        ) {
            selections.put(itemCandidate,false);
        }
        if (writeIn) {
            selections.put("writeIn", false);
        }
    }

    /**
     * Check if the item has a write in field.
     * @return
     */
    @Override
    public boolean isWriteIn() {
        return writeIn;
    }

    /**
     * Set the user selection
     * @param name the selected candidate's name
     * @param writeIn true if the selection is the write in
     */
    @Override
    public void setSelection (String name, boolean writeIn) {
        for (String candidate: selections.keySet()
             ) {
            selections.put(candidate,false);
        }
        if (writeIn) {
            writeInString = name;
            selections.put("writeIn", true);
        }
        else {
            selections.put(name,true);
        }
    }

    /**
     * Get the user's selection
     * @return the string of the candidate currently selected
     */
    @Override
    public String getSelection() {
        for (String candidate: selections.keySet()) {
            if (selections.get(candidate)) {
                if (candidate.equals("writeIn")) {
                    return ("writeIn-" + writeInString);
                }
                else return candidate;
            }
        }
        return null;
    }


    /**
     * This is used for reseting the selections from main
     */
    @Override
    public void resetSelections() {
        for (String itemCandidate: contestCandidates.getCandidatesList().keySet()
        ) {
            selections.put(itemCandidate,false);
        }
        if (writeIn) {
            selections.put("writeIn", false);
        }
        System.out.println(selections.toString());
    }

    /**
     * This method returns the candidate hashmap for contest items
     * @return the candidate hashmap (Name,Party)
     */
    @Override
    public HashMap<String,String> getContestOptions() {
        return (HashMap)contestCandidates.getCandidatesList();
    }

//    public Candidates getContestCandidates() {
//        return contestCandidates;
//    }

}
