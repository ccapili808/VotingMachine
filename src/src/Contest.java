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

    public boolean isWriteIn() {
        return writeIn;
    }

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

    public String getSelection() {
        for (String candidate: selections.keySet()) {
            if (selections.get(candidate)) {
                if (candidate.equals("writeIn")) {
                    return writeInString;
                }
                else return candidate;
            }
        }
        return null;
    }

    public Candidates getContestCandidates() {
        return contestCandidates;
    }

}
