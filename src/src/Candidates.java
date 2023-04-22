import java.util.HashMap;
import java.util.Map;

public class Candidates {
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
