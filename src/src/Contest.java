import java.util.HashMap;

public class Contest {
    /**
     * Normal vote with contestName and list of candidates
     */
    private String contestName;
    private HashMap<String, String> contestCandidates;
    private boolean writeIn;
    public Contest (String contestName, HashMap<String, String> contestCandidates, boolean writeIn) {
        this.contestName = contestName;
        this.contestCandidates = contestCandidates;
        this.writeIn = writeIn;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public boolean isWriteIn() {
        return writeIn;
    }

    public void setWriteIn(boolean writeIn) {
        this.writeIn = writeIn;
    }

    public HashMap<String, String> getContestCandidates() {
        return contestCandidates;
    }

    public void setContestCandidates(HashMap<String, String> contestCandidates) {
        this.contestCandidates = contestCandidates;
    }
}
