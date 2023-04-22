import java.util.HashMap;
import java.util.Map;

/**
 * Store list of candidates
 */
public class Candidates {
    // <candidateName, candidateParty>
    private Map<String, String> candidatesList;
    public Candidates (HashMap<String, String> candidatesList) {
        this.candidatesList = candidatesList;
    }

    public Map<String, String> getCandidatesList() {
        return candidatesList;
    }

    public void setCandidatesList(Map<String, String> candidatesList) {
        this.candidatesList = candidatesList;
    }
}
