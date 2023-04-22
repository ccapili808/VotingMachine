import java.util.HashMap;

/**
 * Only for vote result of one voting session,
 * and reset for a new one.
 */
public class VoteResult {
    // <voteContext, selectedVote>
    private HashMap<String, String> voteSelections;
    public VoteResult (HashMap<String, String> voteSelections) {
        this.voteSelections = voteSelections;
    }

    public HashMap<String, String> getVoteSelections() {
        return voteSelections;
    }

    public void setVoteSelections(HashMap<String, String> voteSelections) {
        this.voteSelections = voteSelections;
    }

    public void clearVotes() {
        voteSelections.clear();
    }
}
