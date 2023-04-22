import java.util.HashMap;
import java.util.List;

public class Contest {
    /**
     * Normal vote with contestName and list of candidates
     */
    private String contestName;
    private Candidates contestCandidates;
    private boolean writeIn;
    public Contest (String contestName, Candidates contestCandidates, boolean writeIn) {
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


    public Candidates getContestCandidates() {
        return contestCandidates;
    }

    public void setContestCandidates(Candidates contestCandidates) {
        this.contestCandidates = contestCandidates;
    }
}
